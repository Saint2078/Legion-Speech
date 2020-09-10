package cn.darkfog.legionspeech

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

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
        when (requestCode) {
            1 -> {
                if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    requestPermissionsSuc()
                } else {
                    val index =
                        grantResults.indices.filter { grantResults[it] != PackageManager.PERMISSION_GRANTED }
                    if (index.size == 1 && permissions[index[0]] == "android.permission.SYSTEM_ALERT_WINDOW") {
                        requestWindow()
                        requestPermissionsSuc()
                    } else {
                        requestPermissionsFail()
                    }
                }
            }
        }

    }

    fun requestPermissionsSuc() {
        startService(Intent(this, DialogService::class.java))
    }

    fun requestPermissionsFail() {
        Toast.makeText(this, "权限申请失败", Toast.LENGTH_LONG).show()
    }

    fun requestWindow() {
        startActivityForResult(
            Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            ), 2
        );
    }


}