package cn.darkfog.speech_service_test

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.darkfog.foundation.CLog
import cn.darkfog.speech_service.BaiduEngine

class MainActivity : AppCompatActivity(), CLog {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissionIfNeeded()
        findViewById<TextView>(R.id.test).setOnClickListener {
            BaiduEngine.start(0)
        }
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
                val msg = """
                    No permission for ${permissions[i]}
                    """.trimIndent()
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                granted = false
            }
        }
        if (granted) {
            requestPermissionsSuc()
        } else {
            onDestroy()
        }
    }

    protected fun requestPermissionsSuc() {
//        BaiduEngine.start(0)
//        BaiduEngine.start().subscribe(
//            object : Observer<BaiduEvent> {
//                override fun onComplete() {
//                    logD { "onComplete: " }
//                }
//
//                override fun onSubscribe(d: Disposable) {
//                    logD { "onSubscribe: " }
//                }
//
//                override fun onNext(t: BaiduEvent) {
//                    logD { "onNext: $t" }
//                }
//
//                override fun onError(e: Throwable) {
//                    logD { "onError: $e" }
//                }
//
//            }
//        )
    }
}
