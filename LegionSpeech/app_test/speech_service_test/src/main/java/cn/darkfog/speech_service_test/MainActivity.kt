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

class MainActivity : AppCompatActivity(), CLog {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissionIfNeeded()

    }

    private fun requestPermissionIfNeeded() {
        val permissions = packageManager.getPackageInfo(
            packageName,
            PackageManager.GET_PERMISSIONS
        ).requestedPermissions
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        var granted = true
        for (i in permissions.indices) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                granted = false
            }
        }
        if (granted) {
            requestPermissionsSuc()
        } else {
            requestPermissionsFail()
            onDestroy()
        }
    }

    fun requestPermissionsSuc() {
        BaiduEngine.register(object : SpeechCallback {
            override fun onPartialAsrResult(result: ASR) {
                content.text = "${content.text} onPartialAsrResult : ${result.content}\n"
            }

            override fun onFinalAsrResult(result: ASR) {
                content.text = "${content.text} onFinalAsrResult : ${result.content}\n"
            }

            override fun onFinalNluResult(result: NLU) {
                content.text = "${content.text} onFinalNluResult : ${result}\n"
            }

            override fun onError(e: Exception) {
                content.text = "${content.text} onError : ${e}\n"
            }

        })


    }

    fun requestPermissionsFail() {
        onDestroy()
    }


    fun onClick(view: View) {
        BaiduEngine.start(StorageUtil.AUDIO_PATH + "/" + System.currentTimeMillis() + ".pcm")
    }

}
