package cn.darkfog.speech_service.model.bean

data class BaiduResponse(
    val name: String?,
    val params: String?,
    val data: String?,
    val offset: Int,
    val length: Int
)