package cn.darkfog.foundation


class CrashCather :Thread.UncaughtExceptionHandler,CLog{
    override fun uncaughtException(t: Thread, e: Throwable) {
        logE(e) { "$t crash"}
    }
}