package cn.darkfog.protocol.redirect

import cn.darkfog.protocol.stt.ASR
import cn.darkfog.protocol.stt.NLU
import io.reactivex.Completable
import io.reactivex.Single

abstract class RuleManager {

    abstract fun init(): Completable

    abstract fun containsRule(asr: ASR): Boolean

    abstract fun applyRule(asr: ASR): Single<NLU>

    abstract fun addRule(rule: Rule): Completable

}