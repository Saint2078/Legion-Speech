package cn.darkfog.speech_service.model.bean

import org.json.JSONObject

data class BaiduNluResult(
    val appid: Int,
    val encoding: String,
    val err_no: Int,
    val parsed_text: String,
    val raw_text: String,
    val results: List<NLUResult>
)

data class NLUResult(
    val domain: String,
    val intent: String,
    val score: Double,
    val slots: JSONObject
)