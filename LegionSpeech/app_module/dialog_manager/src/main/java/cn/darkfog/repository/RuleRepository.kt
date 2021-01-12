package cn.darkfog.repository

import android.os.Bundle
import cn.darkfog.foundation.log.logD
import cn.darkfog.foundation.util.StorageUtil
import cn.darkfog.speech.protocol.stt.NLU
import io.reactivex.Completable
import java.io.Serializable

object RuleRepository {
    private val ruleMap = HashMap<String, NLU>()

    fun init(params: Bundle? = null): Completable {
        logD { "Rule引擎初始化成功" }
        return Completable.complete()
//        StorageUtil.getCache(Array<Rule>::class.java, "rules.dat")
//            .doOnSuccess { arrayOfRules ->
//                arrayOfRules.forEach {
//                    ruleMap[it.origin] = it.target
//                }
//            }
//            .ignoreElement()
    }

    fun addRule(rule: Rule): Completable {
        ruleMap[rule.origin] = rule.target
        return StorageUtil.saveCache(ruleMap.values.toTypedArray(), "rules.dat")
    }

    fun getTargetRule(nlu: NLU): NLU {
        val result = ruleMap[nlu.parsedText]
        return result ?: nlu
    }
}

data class Rule(
    val origin: String,
    val target: NLU
) : Serializable