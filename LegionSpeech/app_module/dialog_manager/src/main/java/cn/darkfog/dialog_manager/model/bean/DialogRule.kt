package cn.darkfog.dialog_manager.model.bean

import cn.darkfog.speech_protocol.speech.bean.ASR
import cn.darkfog.speech_protocol.speech.bean.NLU

data class DialogRule(
    val asr: ASR,
    val nlu: NLU
)