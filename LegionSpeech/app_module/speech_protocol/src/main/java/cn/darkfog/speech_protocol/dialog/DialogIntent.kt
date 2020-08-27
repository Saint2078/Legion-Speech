package cn.darkfog.speech_protocol.dialog

import cn.darkfog.speech_protocol.speech.bean.NluResult

abstract class DialogIntent {
    open val INTENT = ""

    abstract fun getRunnable(result: NluResult): Runnable

    //不需要检查的情况
    open fun checkSlots(result: NluResult): Boolean = true

}