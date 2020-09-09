package cn.darkfog.dialog_manager.model.bean

import cn.darkfog.speech_protocol.speech.bean.ASR
import cn.darkfog.speech_protocol.speech.bean.NLU

data class SpeechRecord(
    var timestamp: Long = 0,
    var pcmFile: String? = null,
    var asr: ASR? = null,
    var nlu: NLU? = null
)