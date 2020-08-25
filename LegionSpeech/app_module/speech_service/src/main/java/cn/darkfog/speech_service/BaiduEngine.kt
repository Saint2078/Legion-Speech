package cn.darkfog.speech_service

import cn.darkfog.foundation.AppContextLinker
import com.baidu.speech.EventManagerFactory
import com.baidu.speech.asr.SpeechConstant
import io.reactivex.Observable
import org.json.JSONObject

object BaiduEngine {

    val manager = EventManagerFactory.create(AppContextLinker.context, "asr").apply {
        registerListener(BaiduEventListener)
        send(SpeechConstant.ASR_KWS_LOAD_ENGINE, params, null, 0, 0)
    }

    val params = JSONObject(
        mapOf(
            SpeechConstant.SOUND_START to R.raw.bdspeech_recognition_start,
            SpeechConstant.SOUND_END to R.raw.bdspeech_recognition_error,
            SpeechConstant.SOUND_CANCEL to R.raw.bdspeech_recognition_cancel,
            "pid" to "15373",
            SpeechConstant.VAD_ENDPOINT_TIMEOUT to 800,
            SpeechConstant.VAD to SpeechConstant.VAD_TOUCH,
            SpeechConstant.DECODER to 2,
            SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH to "assets:///baidu_speech_grammar.bsg",
            SpeechConstant.NLU to "enable",
            SpeechConstant.ACCEPT_AUDIO_VOLUME to false,
            "accept-audio-data" to true,
            "outfile" to AppContextLinker.context.getExternalFilesDirs("audio")
        )
    ).toString()


    fun start(): Observable<BaiduEvent> {
        return Observable.create { emitter ->
            BaiduEventListener.setEmitter(emitter)
            manager.send(SpeechConstant.ASR_START, params, null, 0, 0)
        }
    }

    fun start(test: Int = 0) {
        manager.send(SpeechConstant.ASR_START, params, null, 0, 0)
    }

    fun cancel() {
        manager.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0)
    }

    fun stop() {
        manager.send(SpeechConstant.ASR_STOP, "{}", null, 0, 0)
    }

}