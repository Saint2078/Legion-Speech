package cn.darkfog.eneities.dialog

import cn.darkfog.foundation.protocol.stt.NLU
import cn.darkfog.foundation.protocol.stt.Response
import io.reactivex.Maybe

object DialogEngine {

    fun handlerNlu(nlu: NLU): Maybe<Response> {
        return Maybe.empty()
    }

}