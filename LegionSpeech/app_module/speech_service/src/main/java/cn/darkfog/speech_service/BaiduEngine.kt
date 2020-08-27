package cn.darkfog.speech_service

import cn.darkfog.foundation.AppContextLinker
import cn.darkfog.foundation.CLog
import cn.darkfog.speech_protocol.speech.SpeechEngine
import cn.darkfog.speech_protocol.speech.SpeechEngineManager
import cn.darkfog.speech_protocol.speech.bean.SpeechCallback
import com.baidu.speech.EventManagerFactory
import com.baidu.speech.asr.SpeechConstant
import org.json.JSONObject

object BaiduEngine : SpeechEngine(), CLog {
    private val recogParams = JSONObject(
        mapOf(
            SpeechConstant.SOUND_START to R.raw.bdspeech_recognition_start,
            SpeechConstant.SOUND_END to R.raw.bdspeech_recognition_error,
            SpeechConstant.SOUND_CANCEL to R.raw.bdspeech_recognition_cancel,
            SpeechConstant.PID to "15363",
            SpeechConstant.VAD to SpeechConstant.VAD_DNN,
            SpeechConstant.VAD_ENDPOINT_TIMEOUT to 800,
            SpeechConstant.NLU to "enable",
            SpeechConstant.ACCEPT_AUDIO_VOLUME to false,
            SpeechConstant.ACCEPT_AUDIO_DATA to false
        )
    ).toString()

    private val offlineParams = JSONObject(
        mapOf(
            SpeechConstant.DECODER to 2,
            SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH to "assets:///baidu_speech_grammar.bsg"
        )
    ).toString()

    private val manager = EventManagerFactory.create(AppContextLinker.context, "asr").apply {
        send(SpeechConstant.ASR_KWS_LOAD_ENGINE, offlineParams, null, 0, 0)
    }

    init {
        SpeechEngineManager.setSpeechEngine(this)
    }

    override fun registerSpeechCallback(callback: SpeechCallback) {
        super.registerSpeechCallback(callback)
        manager.registerListener(BaiduEventListener(callback))
    }

    override fun start(outfile: String) {
        manager.send(
            SpeechConstant.ASR_START, recogParams.plus(
                "outfile" to outfile
            ), null, 0, 0
        )
    }

//    fun startRecog(outfile: String): Observable<BaiduEvent> {
//        val outfile =
//            "${AppContextLinker.context.getExternalFilesDirs("audio")[0].absolutePath}${File.separator}${System.currentTimeMillis()}.pcm"
//        if (!File(outfile).exists())
//            logD {
//                "create $outfile result : ${File(outfile).createNewFile()}"
//            }
//        return Observable.create { emitter ->
//            BaiduEventListener.setEmitter(emitter)
//            manager.send(SpeechConstant.ASR_START, recogParams, null, 0, 0)
//
//        }
//    }

    override fun cancel() {
        manager.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0)
    }

    override fun stop() {
        manager.send(SpeechConstant.ASR_STOP, "{}", null, 0, 0)
    }

}