package cn.darkfog.speech_protocol

data class AsrResult(
    val text: String,
    val score: Int
)