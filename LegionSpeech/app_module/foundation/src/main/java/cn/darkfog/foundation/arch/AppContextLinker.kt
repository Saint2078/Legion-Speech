package cn.darkfog.foundation.arch

import android.app.Application
import android.content.Context

object AppContextLinker{
    lateinit var context: Context
        private set

    fun setupLink(application: Application){
        context = application
    }

}