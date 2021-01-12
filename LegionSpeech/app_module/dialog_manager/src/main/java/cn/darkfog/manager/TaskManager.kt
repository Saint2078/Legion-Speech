package cn.darkfog.manager

import android.os.Bundle
import cn.darkfog.speech.protocol.stt.NLU
import cn.darkfog.speech.protocol.stt.Response
import io.reactivex.Completable
import io.reactivex.Observable

object TaskManager {
    const val SENTENCE_KEY = "sentence"
    const val DISPLAY_KEY = "display"

    private val tasks = mutableListOf<Task>()

    fun init(): Completable {
        return Completable.complete()
    }

    fun handlerNlu(nlu: NLU): Observable<Response> {
        val sentence = nlu.parsedText.replace(" ", "")
        var result: Observable<Response>? = null
        tasks.forEach {
            if (it.trigger(sentence)) {
                result = it.handler(nlu)
            }
        }
        return result ?: Observable.just(Response("暂不支持，等待开发中", Bundle().apply {
            putString(SENTENCE_KEY, sentence)
        }))
    }

}

data class Task(
    val name: String,
    val trigger: (text: String) -> Boolean,
    val handler: (nlu: NLU) -> Observable<Response>
)