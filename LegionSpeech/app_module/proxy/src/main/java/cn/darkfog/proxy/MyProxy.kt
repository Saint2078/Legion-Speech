package cn.darkfog.proxy

import cn.darkfog.protocol.stt.AbstractSTTEngine
import io.reactivex.Completable

object MyProxy {

    var sttEngine:AbstractSTTEngine = EmptySTTEngine

    fun init():Completable{
        return sttEngine.init()
    }

}