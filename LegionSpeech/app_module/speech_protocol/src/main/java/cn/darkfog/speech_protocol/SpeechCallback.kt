package cn.darkfog.speech_protocol

abstract class SpeechCallback {

    abstract fun onPartialAsrResult(result: AsrResult)

    abstract fun onFinalAsrResult(results: List<AsrResult>)

//    abstract fun onBestAsrResult(result: AsrResult)

    abstract fun onLocalNluResult(results: List<NluResult>)

    abstract fun onCloudNluResult(results: List<NluResult>)

//    abstract fun onBestNluResult(result: NluResult)

    abstract fun onError(e: Exception)

}