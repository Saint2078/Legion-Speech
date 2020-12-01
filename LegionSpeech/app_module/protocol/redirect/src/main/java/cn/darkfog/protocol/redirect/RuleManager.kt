package cn.darkfog.protocol.redirect

import cn.darkfog.protocol.stt.ASR
import io.reactivex.Completable
import io.reactivex.Single

abstract class RuleManager {

    abstract fun init(): Completable

    abstract fun containsRule(asr: ASR): Boolean

    abstract fun applyRule(asr: ASR): Single<ASR>

    abstract fun addRule(asrRule: ASRRule): Completable

}