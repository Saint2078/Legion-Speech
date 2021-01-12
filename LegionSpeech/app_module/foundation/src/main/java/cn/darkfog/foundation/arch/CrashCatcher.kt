package cn.darkfog.foundation.arch

import cn.darkfog.foundation.log.logE


class CrashCatcher : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(t: Thread, e: Throwable) {
        logE(e) { "$t crash" }
    }
}