package cn.darkfog.dialog_manager

import androidx.lifecycle.MutableLiveData
import cn.darkfog.dialog_manager.data.source.RecordRepository
import cn.darkfog.dialog_manager.model.bean.SpeechRecord
import cn.darkfog.foundation.log.CLog
import cn.darkfog.foundation.log.logD
import cn.darkfog.foundation.util.StorageUtil
import cn.darkfog.speech_service.trash.BaiduEngine
import io.reactivex.Completable

object DialogManager : CLog {

    private var record = SpeechRecord()
    val state = MutableLiveData<SpeechState>(SpeechState.ERROR)
    val callback = object : SpeechCallback {
        override fun onPartialAsrResult(result: ASR) {
            logD {
                result.toString()
            }
        }

        override fun onFinalAsrResult(result: ASR) {
            record.asr = result
            logD {
                result.toString()
            }
        }

        override fun onFinalNluResult(result: NLU) {
            record.nlu = result
            logD {
                result.toString()
            }
        }

        override fun onError(e: Exception) {
            logD {
                e.toString()
            }
        }
    }

    fun init(): Completable {
        return BaiduEngine.init(callback)
            .doOnComplete {
                bindState()
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
                SpeechState.IDLE -> state.postValue(SpeechState.IDLE)
                SpeechState.LISTENING -> state.postValue(SpeechState.LISTENING)
                SpeechState.PROCESS -> state.postValue(SpeechState.PROCESS)
                SpeechState.FINISH -> RecordRepository.addSpeechRecord(record)
            }
        }
    }
}