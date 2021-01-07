package cn.darkfog.dialog_manager.rule

import android.os.Bundle
import cn.darkfog.speech.protocol.stt.NLU
import cn.darkfog.speech_protocol.speech.Nlu
import io.reactivex.Completable
import io.reactivex.Single

object RuleRepository {

    fun init(params:Bundle?=null):Completable{
        return Completable.complete()
    }

    fun addRule(rule: Rule):Completable{
        return Completable.complete()
    }

    fun getTargetRule(nlu: NLU):NLU{
        return nlu
    }
}

data class Rule(
    val origin:NLU,
    val target:NLU
)