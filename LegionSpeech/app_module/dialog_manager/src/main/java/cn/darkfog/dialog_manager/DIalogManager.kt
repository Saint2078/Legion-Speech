package cn.darkfog.dialog_manager

import cn.darkfog.dialog_manager.data.source.RecordRepository
import cn.darkfog.dialog_manager.model.bean.SpeechRecord
import cn.darkfog.foundation.util.StorageUtil
import cn.darkfog.speech_protocol.speech.bean.ASR
import cn.darkfog.speech_protocol.speech.bean.NLU
import cn.darkfog.speech_protocol.speech.bean.SpeechCallback
import cn.darkfog.speech_protocol.speech.bean.SpeechState
import cn.darkfog.speech_service.BaiduEngine

object DialogManager {

    private var record = SpeechRecord()
    val state = BaiduEngine.state

    //Dialog
//    fun init():Completable{ return }
//
//    fun start(){ return}
//
//    fun stop(){ return }
//
//    fun cancel(){ return }

    private object Private {


    }

    fun init() {
        BaiduEngine.register(object : SpeechCallback {
            override fun onPartialAsrResult(result: ASR) {
                //particalText.
            }

            override fun onFinalAsrResult(result: ASR) {
                record.asr = result
            }

            override fun onFinalNluResult(result: NLU) {
                record.nlu = result
            }

            override fun onError(e: Exception) {
                TODO("Not yet implemented")
            }
        })
        BaiduEngine.init()
        state.observeForever {
            when (it) {
                SpeechState.FINISH -> RecordRepository.addSpeechRecord(record)
            }
        }
    }

    fun start() {
        record = SpeechRecord()
        val time = System.currentTimeMillis()
        record.timestamp = time
        val file = "${StorageUtil.AUDIO_PATH}/$time.pcm"
        record.pcmFile = file
        BaiduEngine.start()
    }

    fun stop() {
        BaiduEngine.stop()
    }

    fun cancel() {
        BaiduEngine.cancel()
    }
}