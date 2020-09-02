package cn.darkfog.legionspeech

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.darkfog.legionspeech.view.SpeechView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissionIfNeeded()
        SpeechView.show()
        if (!BuildConfig.DEBUG) {
            finish()
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


    }

    fun requestPermissionsFail() {

    }

}