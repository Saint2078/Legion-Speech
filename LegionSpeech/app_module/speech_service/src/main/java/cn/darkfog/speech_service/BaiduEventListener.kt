package cn.darkfog.speech_service

import cn.darkfog.foundation.CLog
import cn.darkfog.speech_protocol.speech.bean.SpeechCallback
import com.baidu.speech.EventListener


class BaiduEventListener(val callback: SpeechCallback) : EventListener, CLog {

    override fun onEvent(
        name: String?,
        params: String?,
        data: ByteArray?,
        offset: Int,
        length: Int
    ) {
        val event = BaiduEvent(name, params, data?.let { String(it) }, offset, length)


    }

}