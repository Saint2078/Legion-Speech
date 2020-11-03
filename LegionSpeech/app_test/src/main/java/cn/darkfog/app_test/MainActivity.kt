package cn.darkfog.app_test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.darkfog.foundation.log.CLog

class TestActivity : AppCompatActivity(), CLog {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
