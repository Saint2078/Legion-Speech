package com.bmwgroup.apinext.msft_lib.model.net

import android.util.Base64
import cn.darkfog.dialog_manager.msft.model.net.MSFTApi
import com.bmwgroup.apinext.msft_lib.TtsVoice
import com.bmwgroup.apinext.msft_lib.model.bean.MSFTRequestBody
import com.bmwgroup.apinext.msft_lib.model.bean.MSFTRequestContent
import com.bmwgroup.apinext.msft_lib.model.bean.MSFTResponse
import com.bmwgroup.apinext.msft_lib.model.net.interceptors.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileInputStream

object MSFTInterface {

    private var currentTtsVoice = TtsVoice.MATURE
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var api: MSFTApi

    init {
        initRetrofit()
    }

    private fun initRetrofit() {
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor(currentTtsVoice.key))
            .addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        api = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(currentTtsVoice.url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MSFTApi::class.java)
    }

    fun resetTTSConfig(ttsVoice: TtsVoice = TtsVoice.LOLI) {
        currentTtsVoice.takeIf { it != ttsVoice }?.let {
            currentTtsVoice = ttsVoice
            initRetrofit()
        }
    }

    fun processNLU(content: String, callback: Callback<MSFTResponse>) {
        api.requestReply(
            MSFTRequestBody(
                content = MSFTRequestContent(
                    text = content
                )
            )
        ).enqueue(callback)
    }

    fun processNLUByFile(filePath: String, callback: Callback<MSFTResponse>) {
        api.requestReply(
            MSFTRequestBody(
                content = MSFTRequestContent(
                    audioBase64 = createBase64String(filePath)
                )
            )
        ).enqueue(callback)
    }

    fun onBoardProactive(callback: Callback<MSFTResponse>) {
        api.requestReply(
            MSFTRequestBody(
                content = MSFTRequestContent(text = "#EMPTY#")
                    .apply {
                        metaData?.put("CustomizedProactive", "OnBoard")
                    }
            )
        ).enqueue(callback)
    }

    private fun createBase64String(path: String): String {
        val file = File(path)
        val inputFile: FileInputStream? = FileInputStream(file)
        val buffer = ByteArray(file.length().toInt())
        inputFile?.let {
            inputFile.read(buffer)
            inputFile.close()
            return Base64.encodeToString(buffer, Base64.DEFAULT).replace("\n", "")
        }
        return ""
    }

}