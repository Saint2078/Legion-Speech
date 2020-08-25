package cn.darkfog.speech_service

data class BaiduEvent(
    val name: String?,
    val params: String?,
    val data: ByteArray?,
    val offset: Int,
    val length: Int
)