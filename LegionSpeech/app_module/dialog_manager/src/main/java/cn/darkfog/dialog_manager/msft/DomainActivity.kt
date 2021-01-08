package com.bmwgroup.apinext.msft_lib

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.darkfog.dialog_manager.R

class DomainActivity : AppCompatActivity() {

//    val broadcastReceiver : BroadcastReceiver = object: BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            val action = intent?.action
//            val file = intent?.getStringExtra("file")
//            val text = intent?.getStringExtra("text")?.let {
//                MSFTEngine.nluProcess(it)
//            }
//
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

//        MSFTEngine.init(object : MSFTCallback{
//            override fun onResult(result: MSFTResponse) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onError(throwable: Throwable, rawResult: NLUResult?) {
//                TODO("Not yet implemented")
//            }
//
//        })

    }

//    override fun onPause() {
//        super.onPause()
//        registerReceiver(broadcastReceiver, IntentFilter("audio.result"))
//    }
//
//    override fun onResume() {
//        super.onResume()
//        unregisterReceiver(broadcastReceiver)
//    }
}