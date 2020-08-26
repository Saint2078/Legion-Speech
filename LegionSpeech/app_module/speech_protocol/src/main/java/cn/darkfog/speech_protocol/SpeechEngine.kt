package cn.darkfog.speech_protocol

abstract class SpeechEngine {
    private var speechCallback: SpeechCallback? = null

    open fun registerSpeechCallback(callback: SpeechCallback) {
        speechCallback = callback
    }

    abstract fun start(outfile: String)

    abstract fun cancel()

    abstract fun stop()
}