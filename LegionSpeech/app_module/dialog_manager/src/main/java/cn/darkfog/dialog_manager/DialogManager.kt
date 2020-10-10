package cn.darkfog.dialog_manager

import androidx.lifecycle.MutableLiveData
import cn.darkfog.dialog_manager.data.source.RecordRepository
import cn.darkfog.dialog_manager.model.bean.SpeechRecord
import cn.darkfog.foundation.util.StorageUtil
import cn.darkfog.speech_protocol.speech.bean.ASR
import cn.darkfog.speech_protocol.speech.bean.NLU
import cn.darkfog.speech_protocol.speech.bean.SpeechCallback
import cn.darkfog.speech_protocol.speech.bean.SpeechState
import cn.darkfog.speech_service.BaiduEngine
import io.reactivex.Completable

object DialogManager {

    private var record = SpeechRecord()
    val state = MutableLiveData<SpeechState>(SpeechState.ERROR)

    fun init(): Completable {
        return Completable.create {
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
                    // TODO: 2020/10/10
                }
            })
            it.onComplete()
        }.andThen(BaiduEngine.init())
            .doOnComplete {
                state.observeForever {
                    when (it) {

                    }
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

    fun bindState() {
        BaiduEngine.state.observeForever {
            when (it) {
                SpeechState.LISTENING -> state.postValue(SpeechState.LISTENING)
                SpeechState.PROCESS -> state.postValue(SpeechState.PROCESS)
                SpeechState.FINISH -> RecordRepository.addSpeechRecord(record)
            }
        }
    }
}