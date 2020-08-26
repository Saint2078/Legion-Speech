package cn.darkfog.speech_service

import cn.darkfog.foundation.AppContextLinker
import cn.darkfog.foundation.CLog
import cn.darkfog.foundation.logD
import com.baidu.speech.EventManagerFactory
import com.baidu.speech.asr.SpeechConstant
import io.reactivex.Observable
import org.json.JSONObject
import java.io.File

object BaiduEngine : CLog {
    val params = JSONObject(
        mapOf(
            SpeechConstant.SOUND_START to R.raw.bdspeech_recognition_start,
            SpeechConstant.SOUND_END to R.raw.bdspeech_recognition_error,
            SpeechConstant.SOUND_CANCEL to R.raw.bdspeech_recognition_cancel,
            SpeechConstant.PID to "15363",
            SpeechConstant.VAD to SpeechConstant.VAD_DNN,
            SpeechConstant.VAD_ENDPOINT_TIMEOUT to 800,
            SpeechConstant.DECODER to 0,
            SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH to "assets:///baidu_speech_grammar.bsg",
            SpeechConstant.NLU to "enable",
            SpeechConstant.ACCEPT_AUDIO_VOLUME to false,
            SpeechConstant.ACCEPT_AUDIO_DATA to false

        )
    ).toString()

    val manager = EventManagerFactory.create(AppContextLinker.context, "asr").apply {
        registerListener(BaiduEventListener)
        send(SpeechConstant.ASR_KWS_LOAD_ENGINE, params, null, 0, 0)
    }


    fun start(): Observable<BaiduEvent> {
        val outfile =
            "${AppContextLinker.context.getExternalFilesDirs("audio")[0].absolutePath}${File.separator}${System.currentTimeMillis()}.pcm"
        if (!File(outfile).exists())
            logD {
                "create $outfile result : ${File(outfile).createNewFile()}"
            }

        return Observable.create { emitter ->
            BaiduEventListener.setEmitter(emitter)
            manager.send(SpeechConstant.ASR_START, params, null, 0, 0)
            manager.send(
                SpeechConstant.ASR_START, params.plus(
                    "outfile" to outfile
                ), null, 0, 0
            )
        }
    }

    fun start(test: Int = 0) {
        manager.send(SpeechConstant.ASR_START, params, null, 0, 0)
//        val outfile =
//            "${AppContextLinker.context.getExternalFilesDirs("audio")[0].absolutePath}${File.separator}${System.currentTimeMillis()}.pcm"
//        if (!File(outfile).exists())
//            logD {
//                "create $outfile result : ${File(outfile).createNewFile()}"
//            }
//        manager.send(
//            SpeechConstant.ASR_START, params.plus(
//                "outfile" to outfile
//            ), null, 0, 0
//        )
    }

    fun cancel() {
        manager.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0)
    }

    fun stop() {
        manager.send(SpeechConstant.ASR_STOP, "{}", null, 0, 0)
    }

}