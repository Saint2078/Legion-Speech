package cn.darkfog.speech_protocol.speech

import cn.darkfog.foundation.StorageUtil
import cn.darkfog.speech_protocol.speech.bean.SpeechCallback
import java.io.File

abstract class SpeechEngine {
    private var speechCallback: SpeechCallback? = null

    open fun registerSpeechCallback(callback: SpeechCallback) {
        speechCallback = callback
    }

    abstract fun start(outfile: String = "${StorageUtil.AUDIO_PATH}${File.separator}${System.currentTimeMillis()}.pcm")

    abstract fun cancel()

    abstract fun stop()
}