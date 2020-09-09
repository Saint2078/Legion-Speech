package cn.darkfog.speech_service

import androidx.lifecycle.MutableLiveData
import cn.darkfog.foundation.arch.AppContextLinker
import cn.darkfog.foundation.log.CLog
import cn.darkfog.foundation.util.GsonHelper
import cn.darkfog.speech_protocol.speech.bean.ASR
import cn.darkfog.speech_protocol.speech.bean.NLU
import cn.darkfog.speech_protocol.speech.bean.SpeechCallback
import cn.darkfog.speech_protocol.speech.bean.SpeechState
import cn.darkfog.speech_service.model.bean.BaiduNluResult
import cn.darkfog.speech_service.model.bean.BaiduPartialParams
import cn.darkfog.speech_service.model.bean.BaiduResponse
import com.baidu.speech.EventManagerFactory
import com.baidu.speech.asr.SpeechConstant
import org.json.JSONObject

object BaiduEngine : CLog {
    private val state = MutableLiveData(SpeechState.ERROR)
    private var callback: SpeechCallback? = null
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
        //设置离线引擎
        send(SpeechConstant.ASR_KWS_LOAD_ENGINE, offlineParams, null, 0, 0)
    }


    init {
        manager.registerListener { name, params, data, offset, length ->
            val response = BaiduResponse(
                name,
                params,
                data?.let { String(it) },
                offset,
                length
            )
            processBaiduResponse(response)
        }
    }

    fun start(outfile: String) {
        manager.send(
            SpeechConstant.ASR_START, recogParams.plus(
                "outfile" to outfile
            ), null, 0, 0
        )
    }

    fun cancel() {
        manager.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0)
    }

    fun stop() {
        manager.send(SpeechConstant.ASR_STOP, "{}", null, 0, 0)
    }

    fun register(speechCallback: SpeechCallback) {
        callback = speechCallback
    }

    private fun processBaiduResponse(response: BaiduResponse) {
        when (response.name) {
            "asr.partial" -> {
                val params =
                    GsonHelper.gson.fromJson(response.params, BaiduPartialParams::class.java)
                when (params.result_type) {
                    "partial_result" -> callback?.onPartialAsrResult(ASR(params.best_result))
                    "final_result" -> callback?.onFinalAsrResult(ASR(params.best_result))
                    "nlu_result" -> {
                        response.data?.let {
                            val nlu = GsonHelper.gson.fromJson(
                                response.data,
                                BaiduNluResult::class.java
                            ).results.sortedByDescending {
                                it.score
                            }[0]
                            callback?.onFinalNluResult(
                                NLU(
                                    nlu.domain,
                                    nlu.intent,
                                    nlu.slots.toNluSlots()
                                )
                            )
                        }
                    }

                }
            }
            "asr.begin" -> state.postValue(SpeechState.PROCESS)
            "asr.exit" -> state.postValue(SpeechState.IDLE)
            else -> Unit
        }
    }


}