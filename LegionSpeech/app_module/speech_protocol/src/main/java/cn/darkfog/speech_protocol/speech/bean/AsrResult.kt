package cn.darkfog.speech_protocol.speech.bean

data class AsrResult(
    val text: String,
    val score: Int
)