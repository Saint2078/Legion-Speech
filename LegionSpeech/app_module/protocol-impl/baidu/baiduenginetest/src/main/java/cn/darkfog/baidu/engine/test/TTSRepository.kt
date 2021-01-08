package cn.darkfog.baidu.engine.test

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import cn.darkfog.foundation.arch.AppContextLinker
import cn.darkfog.foundation.log.CLog
import cn.darkfog.foundation.log.logD
import com.jdai.tts.*
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.ObservableEmitter
import java.util.*
import kotlin.collections.HashMap

object TTSRepository:CLog {
    val ttsEngine = TextToSpeech(AppContextLinker.context){Unit}

    fun init(extra: Bundle? = null): Completable {
        return Completable.create {
            ttsEngine.setOnUtteranceProgressListener(Listener)
            logD { "TTS引擎初始化成功" }
            it.onComplete()
        }
    }

    fun speak(text: String): Completable {
        return Completable.create {
            val uuid = UUID.randomUUID().toString()
            ttsEngine.speak(text, TextToSpeech.QUEUE_FLUSH,Bundle(),uuid)
            Listener.emitters[uuid] = it
        }
    }

    object Listener : UtteranceProgressListener() {

        var emitters: HashMap<String?, CompletableEmitter?> = HashMap()


        override fun onStart(utteranceId: String?) {
        }

        override fun onDone(utteranceId: String?) {
            val emitter = emitters[utteranceId]
            if (emitter.isAvailable()) {
                emitter!!.onComplete()
                emitters.remove(utteranceId)
            }
        }

        override fun onError(utteranceId: String?) {
            val emitter = emitters[utteranceId]
            if (emitter.isAvailable()) {
                emitter!!.onError(Exception(utteranceId.toString()))
                emitters.remove(utteranceId)
            }
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

