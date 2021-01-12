package cn.darkfog.baidu.engine.test

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.darkfog.speech.engine.baidu.BaiduEngine
import cn.darkfog.speech.protocol.stt.SpeechEvent
import io.reactivex.CompletableObserver
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity(), View.OnClickListener {

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
        log.text = "权限申请成功"
        BaiduEngine.init()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    log.text = "${log.text}\n开始初始化"
                }

                override fun onComplete() {
                    log.text = "${log.text}\n初始化成功"
                }

                override fun onError(e: Throwable) {
                    log.text = "${log.text}\n初始化失败"
                }

            })
    }

    private fun requestPermissionsFail() {
        log.text = "权限申请失败"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        requestPermissionIfNeeded()
        wp.setOnClickListener(this)
        asr.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.wp -> {
                BaiduEngine
                    .startWakeUp()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<SpeechEvent> {
                        override fun onSubscribe(d: Disposable) {
                            log.text = "开始唤醒"
                        }

                        override fun onNext(t: SpeechEvent) {
                            log.text = "${log.text}\n结果返回:$t"
                        }

                        override fun onError(e: Throwable) {
                            log.text = "${log.text}\n出错:$e"
                        }

                        override fun onComplete() {
                            log.text = "${log.text}\n结束"
                        }

                    })
            }
            R.id.asr -> {
                BaiduEngine
                    .startRecognize()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Observer<SpeechEvent> {
                        override fun onSubscribe(d: Disposable) {
                            log.text = "开始识别"
                        }

                        override fun onNext(t: SpeechEvent) {
                            log.text = "${log.text}\n结果返回:$t"
                        }

                        override fun onError(e: Throwable) {
                            log.text = "${log.text}\n出错:$e"
                        }

                        override fun onComplete() {
                            log.text = "${log.text}\n结束"
                        }

                    })
            }

        }
    }
}