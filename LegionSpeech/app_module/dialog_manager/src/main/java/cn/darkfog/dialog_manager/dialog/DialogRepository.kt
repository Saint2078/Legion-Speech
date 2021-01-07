package cn.darkfog.dialog_manager.dialog

import android.os.Bundle
import cn.darkfog.speech.protocol.stt.NLU
import cn.darkfog.speech.protocol.stt.Response
import io.reactivex.Completable

object TaskRepository {

    fun init(extra:Bundle?=null):Completable{
        return Completable.complete()
    }

    fun handlerNLU(nlu: NLU):Response{
        return Response(text = "暂不支持")
    }

}