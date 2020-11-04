package cn.darkfog.speech_service.baidu

import android.os.Bundle
import cn.darkfog.foundation.log.CLog
import cn.darkfog.foundation.log.logD
import cn.darkfog.foundation.util.GsonHelper
import cn.darkfog.speech_protocol.speech.ASR
import cn.darkfog.speech_protocol.speech.SpeechEngine
import cn.darkfog.speech_protocol.speech.SpeechEvent
import cn.darkfog.speech_protocol.speech.SpeechEventType
import cn.darkfog.speech_service.trash.BaiduEngine
import cn.darkfog.speech_service.trash.bean.BaiduPartialParams
import cn.darkfog.speech_service.trash.bean.BaiduResponse
import com.baidu.speech.EventListener
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

object BaiduEngine : SpeechEngine(), CLog {

    override fun init(): Completable {
        return Completable.create {

        }.subscribeOn(Schedulers.io())
    }

    override fun startWakeUp(extra: Bundle?): Observable<SpeechEvent> {
        TODO("Not yet implemented")
    }

    override fun startRecog(extra: Bundle?): Observable<SpeechEvent> {
        TODO("Not yet implemented")
    }

    override fun stop(): Completable {
        TODO("Not yet implemented")
    }

    override fun destroy(): Completable {
        TODO("Not yet implemented")
    }

}

object BaiduListener : EventListener {

    var eventEmitter: ObservableEmitter<SpeechEvent>? = null

    override fun onEvent(
        name: String?,
        params: String?,
        data: ByteArray?,
        offset: Int,
        length: Int
    ) {
        if (name == "asr.audio") return
        val response = BaiduResponse(
            name,
            params,
            data?.let { String(it) },
            offset,
            length
        )
        when (name) {
            "asr.partial" -> {
                val params = GsonHelper.gson.fromJson(response.params, BaiduPartialParams::class.java)
                when (params.result_type) {
                    "partial_result" -> eventEmitter?.onNext(SpeechEvent(SpeechEventType.ASR_PARTIAL,ASR(params.best_result)))
                    "final_result" -> eventEmitter?.onNext(SpeechEvent(SpeechEventType.ASR_CLOUD,ASR(params.best_result)))

            }
        }
    }
}

data class BaiduPartialParams(
    val best_result: String,
    val result_type: String
)