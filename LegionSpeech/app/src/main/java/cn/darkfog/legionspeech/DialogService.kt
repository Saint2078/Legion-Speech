package cn.darkfog.legionspeech

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.Observer
import cn.darkfog.dialog_manager.DialogManager
import cn.darkfog.legionspeech.view.SpeechView
import cn.darkfog.speech_protocol.speech.bean.SpeechState

class DialogService : Service() {

    override fun onCreate() {
        super.onCreate()
        DialogManager.state.observeForever(Observer {
            when (it) {
                SpeechState.IDLE -> SpeechView.setViewState(SpeechView.ViewState.IDLE)
                SpeechState.ERROR -> SpeechView.setViewState(SpeechView.ViewState.ERROR)
                SpeechState.PROCESS -> SpeechView.setViewState(SpeechView.ViewState.PROCESSING)
                SpeechState.LISTENING -> SpeechView.setViewState(SpeechView.ViewState.SPEAKING)
                else -> Unit
            }
        })
        SpeechView.show()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        DialogManager.state.removeObserver { }
    }


}
