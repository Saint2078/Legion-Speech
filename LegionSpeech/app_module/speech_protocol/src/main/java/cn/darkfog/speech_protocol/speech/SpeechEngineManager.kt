package cn.darkfog.speech_protocol.speech

import cn.darkfog.speech_protocol.speech.bean.SpeechCallback

object SpeechEngineManager {

    var seechEngineImpl: SpeechEngine? = null

    fun setSpeechEngine(speechEngine: SpeechEngine) {
        seechEngineImpl = speechEngine
    }

    fun setSpeechCallback(callback: SpeechCallback) {
        if (seechEngineImpl == null) {
            callback.onError(Exception("seechEngineImpl null"))
            return
        }
        seechEngineImpl?.registerSpeechCallback(callback)
    }

}