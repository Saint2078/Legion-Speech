package cn.darkfog.speech_protocol.speech.bean

import java.io.Serializable

data class ASR(
    val content: String
) : Serializable {
    override fun hashCode(): Int {
        return content.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ASR
        if (content != other.content) return false
        return true
    }
}