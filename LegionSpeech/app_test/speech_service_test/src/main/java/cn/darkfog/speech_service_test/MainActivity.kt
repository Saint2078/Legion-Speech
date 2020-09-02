package cn.darkfog.speech_service_test

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.darkfog.foundation.CLog
import cn.darkfog.speech_protocol.speech.bean.AsrResult
import cn.darkfog.speech_protocol.speech.bean.NluResult
import cn.darkfog.speech_protocol.speech.bean.SpeechCallback
import cn.darkfog.speech_service.BaiduEngine

class MainActivity : AppCompatActivity(), CLog {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissionIfNeeded()
        findViewById<TextView>(R.id.test).setOnClickListener {
            BaiduEngine.start()
            BaiduEngine.registerSpeechCallback(object : SpeechCallback() {
                override fun onPartialAsrResult(result: AsrResult) {
                    TODO("Not yet implemented")
                }

                override fun onFinalAsrResult(results: List<AsrResult>) {
                    TODO("Not yet implemented")
                }

                override fun onLocalNluResult(results: List<NluResult>) {
                    TODO("Not yet implemented")
                }

                override fun onCloudNluResult(results: List<NluResult>) {
                    TODO("Not yet implemented")
                }

                override fun onError(e: Exception) {
                    TODO("Not yet implemented")
                }

            })
        }
    }


}
