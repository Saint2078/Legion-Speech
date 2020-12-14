package cn.darkfog.baidu.engine.test

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.darkfog.foundation.log.CLog
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CLog, Callback {
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        count = 0
    }

    override fun onStart() {
        super.onStart()
        if (!Settings.canDrawOverlays(this)) {
            if (count < 1) {
                count += 1
                requestWindow()
            } else {
                promt.text = "${promt.text}没有悬浮窗权限，请重新启动或提供权限\n"
            }
        } else {
            requestPermissionIfNeeded()
        }
    }


    private fun speechInit() {
        DialogManager.init()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {
                    promt.text = "${promt.text}初始化成功\n"
                    DialogManager.callback = this@MainActivity
                    DialogManager.startDialog()
                }

                override fun onError(e: Throwable) {
                    promt.text = "${promt.text}引擎初始化失败:$e\n"
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
        promt.text = "权限申请成功\n"
        speechInit()
    }

    private fun requestPermissionsFail() {
        Toast.makeText(this, "权限申请失败", Toast.LENGTH_LONG).show()
    }

    private fun requestWindow() {
        startActivityForResult(
            Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            ), 1
        )
    }

    override fun onText(text: String) {
        promt.text = "${promt.text}$text\n"
    }

}