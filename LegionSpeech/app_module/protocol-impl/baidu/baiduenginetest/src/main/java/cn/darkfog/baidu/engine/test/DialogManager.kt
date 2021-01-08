package cn.darkfog.dialog_manager

import cn.darkfog.BaiduEngine
import cn.darkfog.baidu.engine.test.TTSRepository
import cn.darkfog.baidu.engine.test.TaskRepository
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
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

object DialogManager : CLog {
    override val logTag: String
        get() = "DarkFog.DialogManager"

    enum class Provider {
        MS,
        BAIDU
    }

    val engine: AbstractSTTEngine = BaiduEngine
    private val msftEngine = MSFTEngine
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
                            startDialog()
                        }.doOnError {

                        }
                        .subscribe()
                }

            })
            logD { "微软引擎初始化成功" }
            it.onComplete()
        }.andThen(TTSRepository.init()).andThen(RuleRepository.init()).andThen(engine.init()).andThen(TaskRepository.init())
    }

    fun startWakeUp() {
        engine.startWakeUp()
            .subscribe(object : Observer<SpeechEvent> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: SpeechEvent) {
                    startDialog()
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

    fun startDialog() {
        when (provider) {
            Provider.BAIDU -> engine.startRecognize()
                .filter {
                    logD { "receive $it" }
                    it.type == EventType.NLU_CLOUD
                }
                .map<NLU> {
                    val result = RuleRepository.getTargetRule(it.data as NLU)
                    logD { "$result" }
                    result
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
                    val response = TaskRepository.handlerNLU(it)
                    logD { "$response" }
                    response
                }.singleOrError()
                .flatMapCompletable {
                    TTSRepository.speak(it.text)
                }
                .doOnComplete {
                    startDialog()
                }
                .doOnError {
                    logE(it) { "出错了" }
                    TTSRepository.speak("未检测到有效指令")
                        .doOnComplete {
                            startDialog()
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






