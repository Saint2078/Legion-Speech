package cn.darkfog.speech_protocol.speech.bean

import android.os.Bundle

data class NLU(
    val domain: String,
    val intent: String,
    val slots: Bundle
)