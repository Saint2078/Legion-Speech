package cn.darkfog.dialog_manager

import androidx.lifecycle.MutableLiveData
import cn.darkfog.foundation.StorageUtil
import cn.darkfog.speech_protocol.speech.SpeechEngineManager
import cn.darkfog.speech_protocol.speech.bean.AsrResult
import cn.darkfog.speech_protocol.speech.bean.NluResult
import cn.darkfog.speech_protocol.speech.bean.SpeechCallback
import io.reactivex.Single
import java.io.File

//先做说一句话吧
//多句话也做吧  长语音的音频会被切分吗？需要研究一下

object DialogManager {
    //need solution
    val partial = MutableLiveData<AsrResult>()

    fun startOneShot(): Single<DialogResult> {
        val outfile = "${StorageUtil.AUDIO_PATH}${File.separator}${System.currentTimeMillis()}.pcm"
        val dialogResult = DialogResult(outfile)
        return Single.create { singleEmitter ->
            SpeechEngineManager.setSpeechCallback(object : SpeechCallback() {
                override fun onPartialAsrResult(result: AsrResult) {
                    partial.postValue(result)
                }

                override fun onFinalAsrResult(results: List<AsrResult>) {
                    dialogResult.asrResult = results.sortedByDescending {
                        it.score
                    }[0]
                }

                override fun onLocalNluResult(results: List<NluResult>) {
                    TODO("Not yet implemented")
//                    SpeechEngineManager.seechEngineImpl?.cancel()
                }

                override fun onCloudNluResult(results: List<NluResult>) {
                    TODO("Not yet implemented")
//                    SpeechEngineManager.seechEngineImpl?.cancel()
                }

                override fun onError(e: Exception) {
                    singleEmitter.onError(e)
                }

            })
            SpeechEngineManager.seechEngineImpl?.start(outfile)
        }
    }

    fun startDialog() {
        //长语音的音频会被切分吗？需要研究一下
    }


    private fun startRecog() {
//        SpeechEngineManager.seechEngineImpl?.start()
    }


}