package cn.darkfog.dialog_manager

import cn.darkfog.BaiduEngine
import cn.darkfog.dialog_manager.dialog.TaskRepository
import cn.darkfog.dialog_manager.msft.MediaManager
import cn.darkfog.dialog_manager.msft.NLUResult
import cn.darkfog.dialog_manager.rule.RuleRepository
import cn.darkfog.foundation.log.CLog
import cn.darkfog.foundation.log.logD
import cn.darkfog.foundation.log.logE
import cn.darkfog.speech.protocol.stt.*
import com.bmwgroup.apinext.msft_lib.MSFTCallback
import com.bmwgroup.apinext.msft_lib.MSFTEngine
import com.bmwgroup.apinext.msft_lib.model.bean.MSFTResponse
import io.reactivex.Completable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

object DialogManager : CLog {

    enum class Provider {
        MS,
        BAIDU
    }

    val engine: AbstractSTTEngine = BaiduEngine
    val msftEngine = MSFTEngine
    var callback: Callback? = null
    var provider: Provider = Provider.BAIDU

    fun init(): Completable {
        return Completable.create {
            msftEngine.init(object : MSFTCallback {
                override fun onResult(result: MSFTResponse) {
                    if (result.isNotEmpty()) {
                        result[0].content.audioUrl?.run {
                            MediaManager.postAudio(listOf(this)) {
                                startWakeUp()
                            }
                        }
                    }

                }

                override fun onError(throwable: Throwable, rawResult: NLUResult?) {
                    TTSRepository.speak("出错了")
                        .doOnComplete {
                            startDialog(provider)
                        }.doOnError {

                        }
                        .subscribe()
                }

            })
        }.andThen(RuleRepository.init())
            .andThen(TTSRepository.init())

    }

    fun startWakeUp() {
        engine.startWakeUp()
            .subscribe(object : Observer<SpeechEvent> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: SpeechEvent) {
                    startDialog(provider)
                }

                override fun onError(e: Throwable) {
                    TTSRepository.speak("出错了")
                        .doOnComplete {
                            startWakeUp()
                        }.doOnError {
                            logE(it) { "唤醒出错" }
                        }
                        .subscribe()
                }

                override fun onComplete() {
                    logD { "唤醒结束" }
                }

            })
    }

    fun startDialog(provider: Provider) {
        when (provider) {
            Provider.BAIDU -> engine.startRecognize()
                .filter { it.type == EventType.NLU_CLOUD }
                .map<NLU> {
                    RuleRepository.getTargetRule(it.type as NLU)
                }.map {
                    if (it.parsedText.replace(" ", "").contains("小冰")) {
                        this@DialogManager.provider = Provider.MS
                    }
                    if (it.parsedText.replace(" ", "").contains("贾维斯")) {
                        this@DialogManager.provider = Provider.BAIDU
                    }
                    if (it.parsedText.replace(" ", "").contains("百度")) {
                        this@DialogManager.provider = Provider.BAIDU
                    }
                    TaskRepository.handlerNLU(it)
                }.singleOrError()
                .flatMapCompletable {
                    TTSRepository.speak(it.text)
                }
                .doOnComplete {
                    startDialog(provider)
                }
                .doOnError {
                    TTSRepository.speak("出错了")
                        .doOnComplete {
                            startWakeUp()
                        }.doOnError {
                            logE(it) { "唤醒出错" }
                        }
                        .subscribe()

                }
                .subscribe()
            Provider.MS -> msftEngine.start()
        }
    }

}



interface Callback {
    fun onText(text: String)
}






