package cn.darkfog.dialog_manager.model.bean

import cn.darkfog.speech.protocol.stt.ASR

data class ASRRule(
    val originAsr: ASR,
    val fixedAsr: ASR
)