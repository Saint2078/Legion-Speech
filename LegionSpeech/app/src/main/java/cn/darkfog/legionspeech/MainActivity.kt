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
                content.text = "${content.text}没有悬浮窗权限，请重新启动或提供权限\n"
            }
        } else {
            requestPermissionIfNeeded()
        }
    }


    private fun speechInit() {
        if (DialogManager.state.value == SpeechState.ERROR) {
            DialogManager.init()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onComplete() {
                        content.text = "${content.text}引擎初始化成功\n"
                        content.text = "${content.text}此界面将于3秒后自动关闭\n"
                        AndroidSchedulers.mainThread()
                            .scheduleDirect({ finish() }, 3, TimeUnit.SECONDS)
                    }

                    override fun onError(e: Throwable) {
                        content.text = "${content.text}引擎初始化失败\n"
                    }
                })
        }
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
        content.text = "${content.text}权限申请成功\n"
        speechInit()
        startService(Intent(this, DialogService::class.java))
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

}