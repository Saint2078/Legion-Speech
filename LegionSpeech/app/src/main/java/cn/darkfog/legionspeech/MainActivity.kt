package cn.darkfog.legionspeech

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.darkfog.dialog_manager.DialogManager
import cn.darkfog.foundation.log.CLog
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), CLog {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissionIfNeeded()
        speechInit()
    }

    override fun onResume() {
        super.onResume()
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "申请悬浮窗权限失败", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun speechInit() {
        DialogManager.init()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {
                    content.text = "${content.text}引擎初始化成功\n"
                    AndroidSchedulers.mainThread().scheduleDirect({ finish() }, 3, TimeUnit.SECONDS)
                }

                override fun onError(e: Throwable) {
                    content.text = "${content.text}引擎初始化失败\n"
                }
            })
    }

    private fun requestPermissionIfNeeded() {
        val permissions = packageManager.getPackageInfo(
            packageName,
            PackageManager.GET_PERMISSIONS
        ).requestedPermissions.filter {
            it != "android.permission.SYSTEM_ALERT_WINDOW"
        }.toTypedArray()
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            requestPermissionsSuc()
        } else {
            requestPermissionsFail()
        }
    }

    private fun requestPermissionsSuc() {
        startService(Intent(this, DialogService::class.java))
        content.text = "${content.text}权限申请成功\n"
        if (!Settings.canDrawOverlays(this)) {
            requestWindow()
        }
    }

    private fun requestPermissionsFail() {
        Toast.makeText(this, "权限申请失败", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun requestWindow() {
        startActivityForResult(
            Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            ), 1
        );
    }


}