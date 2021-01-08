package cn.darkfog.dialog_manager.model.bean

import cn.darkfog.speech.protocol.stt.ASR
import cn.darkfog.speech.protocol.stt.NLU

data class NLURule(
    val asr: ASR,
    val nlu: NLU,
    val type: TYPE = TYPE.FIX
)

enum class TYPE {
    FIX,
    CUSTOME
}