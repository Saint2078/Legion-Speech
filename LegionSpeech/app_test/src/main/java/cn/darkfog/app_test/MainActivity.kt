package cn.darkfog.app_test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.darkfog.foundation.CLog
import cn.darkfog.foundation.logD
import cn.darkfog.speech_service.BaiduEvent
import cn.darkfog.speech_service.BaiduSpeechManager
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity(), CLog {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BaiduSpeechManager.start().subscribe(
            object : Observer<BaiduEvent> {
                override fun onComplete() {
                    logD { "onComplete: " }
                }

                override fun onSubscribe(d: Disposable) {
                    logD { "onSubscribe: " }
                }

                override fun onNext(t: BaiduEvent) {
                    logD { "onNext: $t" }
                }

                override fun onError(e: Throwable) {
                    logD { "onError: $e" }
                }

            }
        )
    }
}
