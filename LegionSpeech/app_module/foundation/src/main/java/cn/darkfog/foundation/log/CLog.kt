package cn.darkfog.foundation.log

import com.orhanobut.logger.Logger

//val CTAG = "DarkFog"

//interface CLog {
//    val logTag: String
//        get() = javaClass.simpleName
//}

inline fun <reified T> T.logD(block: () -> String?) {
    Logger.t(T::class.java.simpleName).d(block.invoke() ?: "")
}

inline fun <reified T> T.logD(tag: String, block: () -> String?) {
    Logger.t(tag).d(block.invoke() ?: "")
}

inline fun <reified T> T.logE(block: () -> String?) {
    Logger.t(T::class.java.simpleName).e(block.invoke() ?: "")
}

inline fun <reified T> T.logE(throwable: Throwable, block: (() -> String?)) {
    Logger.t(T::class.java.simpleName).e(throwable, block.invoke() ?: "")
}

inline fun <reified T> T.logE(tag: String, throwable: Throwable, block: () -> String?) {
    Logger.t(tag).e(throwable, block.invoke() ?: "")
}

inline fun <reified T> T.logI(block: () -> String?) {
    Logger.t(T::class.java.simpleName).i(block.invoke() ?: "")
}

inline fun <reified T> T.logI(tag: String, block: () -> String?) {
    Logger.t(tag).i(block.invoke() ?: "")
}

