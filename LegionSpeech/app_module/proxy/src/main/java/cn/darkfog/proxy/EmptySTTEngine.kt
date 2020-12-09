package cn.darkfog.proxy

import android.os.Bundle
import cn.darkfog.speech.protocol.stt.AbstractSTTEngine
import cn.darkfog.speech.protocol.stt.SpeechEvent
import io.reactivex.Completable
import io.reactivex.Observable

object EmptySTTEngine :AbstractSTTEngine(){
    override fun init(): Completable {
        return Completable.complete()
    }

    override fun startWakeUp(keyWords: Array<String>): Observable<SpeechEvent> {
        return Observable.empty()
    }

    override fun startRecognize(extra: Bundle?): Observable<SpeechEvent> {
        return Observable.empty()
    }

    override fun stop(): Completable {
        return Completable.complete()
    }

    override fun releaseSpeech(): Completable {
        return Completable.complete()
    }
}