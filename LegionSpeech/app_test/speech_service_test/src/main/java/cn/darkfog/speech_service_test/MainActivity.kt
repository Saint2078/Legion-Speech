package cn.darkfog.speech_service_test

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.darkfog.foundation.log.CLog
import cn.darkfog.foundation.util.StorageUtil
import cn.darkfog.speech_protocol.speech.bean.ASR
import cn.darkfog.speech_protocol.speech.bean.NLU
import cn.darkfog.speech_protocol.speech.bean.SpeechCallback
import cn.darkfog.speech_service.BaiduEngine
import kotlinx.android.synthetic.main.activity_main.*

class TestActivity : AppCompatActivity(), CLog {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
