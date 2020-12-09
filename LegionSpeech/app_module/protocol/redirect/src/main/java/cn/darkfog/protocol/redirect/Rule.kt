package cn.darkfog.protocol.redirect

import cn.darkfog.speech.protocol.stt.ASR
import cn.darkfog.speech.protocol.stt.NLU

data class Rule(
    val origin: ASR,
    val destination: NLU
)

