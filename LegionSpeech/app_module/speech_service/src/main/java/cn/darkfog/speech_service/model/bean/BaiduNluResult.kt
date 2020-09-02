package cn.darkfog.speech_service.model.bean

import cn.darkfog.speech_protocol.speech.bean.NluResult

data class BaiduNluResponse(
    val appid: Int,
    val encoding: String,
    val err_no: Int,
    val parsed_text: String,
    val raw_text: String,
    val results: List<BaiduNluResult>
) {
    fun toNluResult(): NluResult {
        return NluResult("", "", listOf(), 0)
    }
}

data class BaiduNluResult(
    val domain: String,
    val intent: String,
    val score: Double,
    val slots: Slots
)

data class Slots(
    val user_singer_name: List<UserSingerName>
)

data class UserSingerName(
    val norm: String,
    val word: String
)