package cn.darkfog.speech_protocol.speech.bean

import java.io.Serializable

data class SpeechEvent(
    val type: String,
    val data: Serializable
)