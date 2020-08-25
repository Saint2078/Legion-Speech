package cn.darkfog.speech_service

import cn.darkfog.foundation.CLog
import cn.darkfog.foundation.logD
import com.baidu.speech.EventListener
import io.reactivex.ObservableEmitter


object BaiduEventListener : EventListener, CLog {
    private var emitter: ObservableEmitter<BaiduEvent>? = null

    override fun onEvent(
        name: String?,
        params: String?,
        data: ByteArray?,
        offset: Int,
        length: Int
    ) {
        val event = BaiduEvent(name, params, data, offset, length)
        logD {
            event.toString()
        }
        emitter?.onNext(event)
    }

    fun setEmitter(e: ObservableEmitter<BaiduEvent>?) {
        emitter = e
    }

}