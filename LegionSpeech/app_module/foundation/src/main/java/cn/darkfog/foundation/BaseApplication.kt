package cn.darkfog.foundation

import android.app.Application

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppContextLinker.setupLink(this)
        Thread.setDefaultUncaughtExceptionHandler(CrashCatcher())
        LoggerUtil.init("DarkFog")
    }

}