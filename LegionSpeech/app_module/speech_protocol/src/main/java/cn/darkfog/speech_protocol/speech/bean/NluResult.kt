package cn.darkfog.speech_protocol.speech.bean

data class NluResult(
    val domain: String,
    val intent: String,
    val slots: List<String>,
    val score: Int
)