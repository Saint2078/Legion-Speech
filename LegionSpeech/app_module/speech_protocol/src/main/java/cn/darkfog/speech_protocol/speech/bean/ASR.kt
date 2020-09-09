package cn.darkfog.speech_protocol.speech.bean

import java.io.Serializable

data class ASR(
    val content: String
) : Serializable {
    override fun hashCode(): Int {
        return content.hashCode()
    }
}