package cn.darkfog.dialog_manager

import cn.darkfog.speech_protocol.AsrResult
import cn.darkfog.speech_protocol.NluResult

abstract class DialogCallback {

    abstract fun onBestAsrResult(result: AsrResult)

    abstract fun onBestNluResult(result: NluResult)

    abstract fun onError(e: Exception)
}