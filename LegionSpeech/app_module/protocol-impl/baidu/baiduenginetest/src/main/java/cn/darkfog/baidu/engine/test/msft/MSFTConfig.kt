package com.bmwgroup.apinext.msft_lib

import android.media.AudioFormat
import android.media.MediaRecorder

object MSFTConfig {

    const val AUDIO_RECORD_FREQUENCY = 8000

    const val AUDIO_SOURCE = MediaRecorder.AudioSource.MIC

    const val AUDIO_CHANNELS = AudioFormat.CHANNEL_OUT_MONO

    const val OUTPUT_FORMAT = MediaRecorder.OutputFormat.AMR_NB

    const val OUT_FILE_TYPE = ".amr"

    const val AUDIO_ENCODER = MediaRecorder.AudioEncoder.AMR_NB

    const val BIT_RATE = 32

    const val SIGNATURE_KEY = "c812077a295d4af590bb5d645a9ad697"

    const val PATH = "api/platform/Reply"

    var baseUrl = "http://ppe-xiaoice-bmw.trafficmanager.cn/"
    var subscriptionKey = "9f561191515541e6aa54ea58f306fbca"

}