package cn.darkfog.foundation


class CrashCatcher :Thread.UncaughtExceptionHandler,CLog{
    override fun uncaughtException(t: Thread, e: Throwable) {
        logE(e) { "$t crash"}
    }
}