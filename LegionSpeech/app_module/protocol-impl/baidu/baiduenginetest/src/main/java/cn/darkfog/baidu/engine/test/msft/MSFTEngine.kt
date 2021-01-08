package com.bmwgroup.apinext.msft_lib

import android.media.MediaRecorder
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import cn.darkfog.foundation.arch.AppContextLinker
import com.bmwgroup.apinext.msft_lib.model.bean.MSFTResponse
import com.bmwgroup.apinext.msft_lib.model.net.MSFTInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object MSFTEngine {

    enum class STATE {
        UNINIT,
        IDLE,
        LISTENING,
        WAITING,
        ERROR
    }

    private val PATH: String = AppContextLinker.context.getExternalFilesDir(null)!!.absolutePath

    private var file: String = PATH + "Cache" + MSFTConfig.OUT_FILE_TYPE
    private var mediaRecorder: MediaRecorder? = null
    private var state: MutableLiveData<STATE> = MutableLiveData<STATE>().apply {
        postValue(STATE.UNINIT)
    }

    private var currentCall: Call<MSFTResponse>? = null
    private var callback: MSFTCallback? = null

    fun changeTTS(ttsVoice: TtsVoice) {
        MSFTInterface.resetTTSConfig(ttsVoice)
        MSFTConfig.baseUrl = ttsVoice.url
        MSFTConfig.subscriptionKey = ttsVoice.key
    }

    fun init(callback: MSFTCallback) {
        createDir()
        state.postValue(STATE.IDLE)
        this.callback = callback
    }

    fun start(
        fileName: String = SimpleDateFormat(
            "yyyy_MM_dd_HH_mm_ss_SSS",
            Locale.CHINA
        ).format(System.currentTimeMillis())
    ) {
        if (state.value == STATE.IDLE) {
            file = PATH + File.separator + fileName + MSFTConfig.OUT_FILE_TYPE
            initRecoder(File(file))
            mediaRecorder?.start()
            state.postValue(STATE.LISTENING)
        }
    }

    fun stop() {
        if (state.value == STATE.LISTENING) {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            MSFTInterface.processNLUByFile(file, msftCallback)
            state.postValue(STATE.WAITING)
        }
    }

    fun cancel() {
        if (state.value == STATE.WAITING) {
            currentCall?.cancel()
            currentCall = null
        } else if (state.value == STATE.LISTENING) {
            mediaRecorder?.stop()
            mediaRecorder?.release()
        }
    }

    fun nluProcess(content: String) {
        MSFTInterface.processNLU(content, msftCallback)
        state.value = STATE.WAITING
    }

    fun onBoardProactive() {
        MSFTInterface.onBoardProactive(msftCallback)
        state.postValue(STATE.WAITING)
    }

    fun onBoardProactive(callback: Callback<MSFTResponse>) {
        MSFTInterface.onBoardProactive(callback)
    }

    fun destory() {
        callback = null
        when (state.value) {
            STATE.UNINIT -> return
            STATE.IDLE -> {

            }
            STATE.LISTENING, STATE.WAITING -> {
                cancel()
            }
            STATE.ERROR -> return
        }
    }

    fun observeState(observer: Observer<STATE>, owner: LifecycleOwner? = null) {
        owner?.let {
            state.observe(owner, observer)
            return
        }
        state.observeForever(observer)
    }

    fun removeObserver(observer: Observer<STATE>) {
        state.removeObserver(observer)
    }

    private fun initRecoder(file: File) {
        mediaRecorder = MediaRecorder().apply {
            setOutputFile(file)
            setAudioSource(MSFTConfig.AUDIO_SOURCE)
            // setAudioChannels(MSFTConfig.AUDIO_CHANNELS)
            setAudioSamplingRate(MSFTConfig.AUDIO_RECORD_FREQUENCY)
            setOutputFormat(MSFTConfig.OUTPUT_FORMAT)
            setAudioEncoder(MSFTConfig.AUDIO_ENCODER)
            setAudioEncodingBitRate(MSFTConfig.BIT_RATE)
            prepare()
        }
    }

    private fun createDir() {
        val file = File(PATH)
        if (!file.exists()) {
            file.mkdir()
        }
    }

    private val msftCallback = object : Callback<MSFTResponse> {
        override fun onFailure(call: Call<MSFTResponse>, t: Throwable) {
            state.postValue(STATE.ERROR)
            callback?.onError(t, null)
        }

        override fun onResponse(call: Call<MSFTResponse>, response: Response<MSFTResponse>) {
            state.postValue(STATE.IDLE)
            callback?.onResult(response.body()!!)
        }
    }
}