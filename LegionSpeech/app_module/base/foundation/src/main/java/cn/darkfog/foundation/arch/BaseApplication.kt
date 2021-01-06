package cn.darkfog.foundation.arch

import android.app.Application
import cn.darkfog.foundation.util.LoggerUtil

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppContextLinker.setupLink(this)
        Thread.setDefaultUncaughtExceptionHandler(CrashCatcher())
        LoggerUtil.init("DarkFog")
    }

}