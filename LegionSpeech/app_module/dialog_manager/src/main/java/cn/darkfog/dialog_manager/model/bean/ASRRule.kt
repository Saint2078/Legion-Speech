package cn.darkfog.dialog_manager.model.bean

import cn.darkfog.speech_protocol.speech.bean.ASR

data class ASRRule(
    val originAsr: ASR,
    val fixedAsr: ASR
)