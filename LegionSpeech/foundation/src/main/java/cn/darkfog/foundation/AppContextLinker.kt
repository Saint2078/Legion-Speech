package cn.darkfog.foundation

import android.app.Application
import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.CsvFormatStrategy
import com.orhanobut.logger.DiskLogAdapter
import com.orhanobut.logger.Logger

object AppContextLinker{
    lateinit var context: Context
        private set

    fun setupLink(application: Application){
        context = application
    }

}