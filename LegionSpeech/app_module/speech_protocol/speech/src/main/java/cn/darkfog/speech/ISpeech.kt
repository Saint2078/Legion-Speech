package cn.darkfog.speech

import android.os.Bundle
import io.reactivex.Observer

interface ISpeech {

    fun init(extra : Bundle? =null)

    fun startWakeUp() : Observer<>


}