package cn.darkfog.speech_protocol.speech.bean

interface SpeechCallback {

    abstract fun onPartialAsrResult(result: ASR)

    abstract fun onFinalAsrResult(result: ASR)

    abstract fun onFinalNluResult(result: NLU)

    abstract fun onError(e: Exception)

}