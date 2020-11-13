package cn.darkfog.speech

import java.io.Serializable

data class SpeechEvent(
    val type: EventType,
    val data: Serializable?
)

enum class EventType {
    VAD_START,
    VAD_END,
    ASR_WUW,
    ASR_LOCAL,
    ASR_PARTICAL,
    ASR_CLOUD,
    NLU_LOCAL,
    NLU_CLOUD,
    RESPONSE
}