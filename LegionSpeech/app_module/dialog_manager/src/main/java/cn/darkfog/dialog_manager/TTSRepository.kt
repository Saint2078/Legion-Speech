package cn.darkfog.dialog_manager

import android.os.Bundle
import cn.darkfog.foundation.arch.AppContextLinker
import com.jdai.tts.*
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.ObservableEmitter
import java.util.*
import kotlin.collections.HashMap

object TTSRepository {
    val ttsEngine = TTSEngine(AppContextLinker.context, TTSMode.ONLINE)

    fun init(extra: Bundle? = null): Completable {
        return Completable.create {
            JDLogProxy.setEnable(true, JDLogProxy.VERBOSE)
            val ttsParam = TTSParam()
            ttsParam.setOpts("aue", "2"); // 0：wav, 1：pcm, 2：opus,  3：mp3
            ttsParam.setOpts("sr", "16000"); //  采样率 wav和pcm支持4k到24k的采样率 opus支持8k 12k 16k 和24k的采样率
            ttsParam.setOpts(
                "serverURL",
                "https://aiapi.jd.com/jdai/tts_vip"
            )// 接口地址（注意每个API接口地址不同，详见购买的API接口文档）
            ttsParam.setOpts("appKey", "b399d02131515930e3d173425ae32720");
            ttsParam.setOpts("appSecret", "edfad37deee18ab30a1a69105be0af56");
            ttsParam.setOpts("CustomerType", "0"); // 固定值，Neuhub平台
            ttsParam.setOpts("tte", "1"); // 1:UTF-8 (目前仅支持UTF-8格式)
            ttsParam.setOpts("tim", "1"); // 0：女声 1：男声
            ttsParam.setOpts("vol", "4"); // 音量[0.1, 10.0]
            ttsParam.setOpts("sp", "1.0"); // 语速 [0.5, 2.0] wav和pcm支持4k到24k的采样率
            ttsParam.setOpts("streamMode", "1"); // 1 流式模式， 0 非流式模式
            ttsParam.setOpts("tt", "0"); // 文本格式,  0：文本 1：SSML
            ttsParam.setOpts("ttsModel", "taotao.dat");  //引擎模型， 在离线模式下有效
            ttsParam.setOpts("connectTimeout", "3000");  //3s
            ttsParam.setOpts("readTimeout", "5000");  //5s
            ttsParam.setOpts("playCacheNum", "0");  //播放器缓冲设置， 内部默认为2
            ttsParam.setOpts("httpProtocols", "http1");  //http1版本协议
            ttsEngine.setParam(ttsParam)
            ttsEngine.setTTSEngineListener(Listener)
        }
    }

    fun speak(text: String): Completable {
        return Completable.create {
            val uuid = UUID.randomUUID().toString()
            ttsEngine.speak(text, uuid)
            Listener.emitters[uuid] = it
        }
    }

    object Listener : TTSEngineListener {

        var emitters: HashMap<String?, CompletableEmitter?> = HashMap()

        override fun onSynthesizeStart(p0: String?) {
            Unit
        }

        override fun onSynthesizeFirstPackage(p0: String?) {

        }

        override fun onSynthesizeDataArrived(
            p0: String?,
            p1: ByteArray?,
            p2: Int,
            p3: Double,
            p4: String?
        ) {

        }

        override fun onSynthesizeFinish(p0: String?) {

        }

        override fun onPlayStart(p0: String?) {

        }

        override fun onPlayProgressChanged(p0: String?, p1: Double) {

        }

        override fun onPlayPause(p0: String?) {

        }

        override fun onPlayResume(p0: String?) {

        }

        override fun onPlayFinish(p0: String?) {
            val emitter = emitters[p0]
            if (emitter.isAvailable()) {
                emitter!!.onComplete()
                emitters.remove(p0)
            }
        }

        override fun onError(p0: String?, p1: TTSErrorCode?) {
            val emitter = emitters[p0]
            if (emitter.isAvailable()) {
                emitter!!.onError(Exception(p1.toString()))
                emitters.remove(p0)
            }
        }

        override fun onBufValid(): Int {
            return 0
        }

        override fun onTry(p0: String?, p1: TTSErrorCode?) {

        }

    }

}

fun <T> ObservableEmitter<T>?.isAvaliable(): Boolean {
    // null -> false
    // true -> false
    // false -> true
    return this?.isDisposed == false
}

fun CompletableEmitter?.isAvailable(): Boolean {
    // null -> false
    // true -> false
    // false -> true
    return this?.isDisposed == false
}

