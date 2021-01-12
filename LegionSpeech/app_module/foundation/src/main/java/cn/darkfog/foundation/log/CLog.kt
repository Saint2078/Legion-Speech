package cn.darkfog.foundation.log

import com.orhanobut.logger.Logger

//val CTAG = "DarkFog"

//interface CLog {
//    val logTag: String
//        get() = javaClass.simpleName
//}

inline fun <reified T> T.pLogD(block: () -> String?) {
    Logger.t(T::class.java.simpleName).d(block.invoke() ?: "")
}

inline fun <reified T> T.pLogD(tag: String, block: () -> String?) {
    Logger.t(tag).d(block.invoke() ?: "")
}

inline fun <reified T> T.pLogE(block: () -> String?) {
    Logger.t(T::class.java.simpleName).e(block.invoke() ?: "")
}

inline fun <reified T> T.pLogE(throwable: Throwable, block: (() -> String?)) {
    Logger.t(T::class.java.simpleName).e(throwable, block.invoke() ?: "")
}

inline fun <reified T> T.pLogE(tag: String, throwable: Throwable, block: () -> String?) {
    Logger.t(tag).e(throwable, block.invoke() ?: "")
}

inline fun <reified T> T.pLogI(block: () -> String?) {
    Logger.t(T::class.java.simpleName).i(block.invoke() ?: "")
}

inline fun <reified T> T.pLogI(tag: String, block: () -> String?) {
    Logger.t(tag).i(block.invoke() ?: "")
}

