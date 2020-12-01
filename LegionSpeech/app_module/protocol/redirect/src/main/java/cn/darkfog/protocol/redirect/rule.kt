package cn.darkfog.protocol.redirect

import cn.darkfog.protocol.stt.ASR

data class ASRRule(
    val originAsr: ASR,
    val fixedAsr: ASR
)