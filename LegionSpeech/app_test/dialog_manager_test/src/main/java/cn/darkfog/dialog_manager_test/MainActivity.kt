package cn.darkfog.dialog_manager_test

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.darkfog.dialog_manager.detail.DialogActivity

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this@TestActivity, DialogActivity::class.java))
    }
}
