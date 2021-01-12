package cn.darkfog.manager

import cn.darkfog.foundation.log.logD
import cn.darkfog.repository.RuleRepository
import cn.darkfog.repository.TTSRepository
import cn.darkfog.speech.engine.baidu.BaiduEngine
import cn.darkfog.speech.protocol.stt.AbstractSTTEngine
import cn.darkfog.speech.protocol.stt.EventType
import cn.darkfog.speech.protocol.stt.NLU
import cn.darkfog.speech.protocol.stt.Response
import io.reactivex.Completable
import io.reactivex.Observable

object DialogManager {

    private val engine: AbstractSTTEngine = BaiduEngine

    fun init(): Completable {
        return engine.init()
            .andThen(TTSRepository.init())
            .andThen(TaskManager.init())
            .andThen(RuleRepository.init())
    }

    fun start(): Observable<Response> {
        return engine.startRecognize()
            .filter {
                logD { "receive $it" }
                it.type == EventType.NLU_CLOUD
            }
            .map<NLU> {
                val result = RuleRepository.getTargetRule(it.data as NLU)
                logD { "$result" }
                result
            }.flatMap<Response> {
                TaskManager.handlerNlu(it)
            }
            .doOnNext {
                TTSRepository.speak(it.text)
            }
    }

}

