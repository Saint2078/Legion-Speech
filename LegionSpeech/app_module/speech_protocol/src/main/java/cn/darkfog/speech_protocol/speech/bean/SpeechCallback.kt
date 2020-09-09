package cn.darkfog.speech_protocol.speech.bean

abstract class SpeechCallback {

    abstract fun onPartialAsrResult(result: ASR)

    abstract fun onFinalAsrResult(results: ASR)

    abstract fun onFinalNluResult(results: NLU)

    abstract fun onError(e: Exception)

}