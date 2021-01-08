package cn.darkfog.dialog_manager.model.bean

import cn.darkfog.speech.protocol.stt.ASR
import cn.darkfog.speech.protocol.stt.NLU

data class DialogRule(
    val asr: ASR,
    val nlu: NLU
)