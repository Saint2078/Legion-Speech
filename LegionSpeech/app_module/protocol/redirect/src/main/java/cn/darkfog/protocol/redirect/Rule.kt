package cn.darkfog.protocol.redirect

import cn.darkfog.protocol.stt.ASR
import cn.darkfog.protocol.stt.NLU

data class Rule(
    val origin: ASR,
    val destination: NLU
)

