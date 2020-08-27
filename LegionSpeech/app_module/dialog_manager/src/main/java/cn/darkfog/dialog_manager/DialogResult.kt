package cn.darkfog.dialog_manager

import cn.darkfog.speech_protocol.speech.bean.AsrResult
import cn.darkfog.speech_protocol.speech.bean.NluResult

data class DialogResult(
    val outFile: String,
    var asrResult: AsrResult? = null,
    val nluResult: NluResult? = null,
    val runnable: Runnable? = null
)