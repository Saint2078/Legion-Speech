package cn.darkfog.speech_protocol.speech.bean

data class AsrResult(
    val text: String,
    val score: Int = 0,
    val energy: Double = 0.0
)