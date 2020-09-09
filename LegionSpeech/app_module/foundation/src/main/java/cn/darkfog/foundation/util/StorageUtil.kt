package cn.darkfog.foundation.util

import cn.darkfog.foundation.arch.AppContextLinker
import io.reactivex.Completable
import io.reactivex.Single
import java.io.*

object StorageUtil {

    init {
        AppContextLinker.context.getExternalFilesDir("audio")
        AppContextLinker.context.getExternalFilesDir("cache")
    }

    val CACHE_PATH = AppContextLinker.context.getExternalFilesDir("cache")?.absolutePath
    val AUDIO_PATH = AppContextLinker.context.getExternalFilesDir("audio")?.absolutePath


    fun <T : Serializable> getCache(modelClass: Class<T>, fileName: String): Single<T> {
        return Single.create {
            it.onSuccess(ObjectInputStream(FileInputStream("$CACHE_PATH/$fileName")).readObject() as T)
        }
    }

    fun saveCache(instance: Serializable, fileName: String): Completable {
        return Completable.create {
            val fos = FileOutputStream("$CACHE_PATH/$fileName")
            val oos = ObjectOutputStream(fos)
            oos.writeObject(instance)
            oos.flush()
            oos.close()
            fos.close()
            it.onComplete()
        }
    }
}