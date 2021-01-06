package cn.darkfog.entity.stt

import android.os.Bundle
import cn.darkfog.foundation.protocol.stt.AbstractSTTEngine
import cn.darkfog.foundation.protocol.stt.SpeechEvent
import io.reactivex.Completable
import io.reactivex.Observable

object STTRepository : AbstractSTTEngine() {

    var engine: AbstractSTTEngine = BaiduEngine


    override fun init(extra: Bundle?): Completable {
        return engine.init(extra)
    }

    override fun startWakeUp(extra: Bundle?): Observable<SpeechEvent> {
        return engine.startWakeUp(extra)
    }

    override fun startRecognize(extra: Bundle?): Observable<SpeechEvent> {
        return engine.startRecognize(extra)
    }

    override fun stop(extra: Bundle?): Completable {
        return engine.stop(extra)
    }

    override fun release(): Completable {
        return engine.release()
    }

}