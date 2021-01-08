package com.bmwgroup.apinext.msft_lib.model.bean

data class MSFTRequestBody(
    val content: MSFTRequestContent,
    val senderId: String = "2G8f23klQ2045iH",
    val senderNickname: String = "TestUser",
    val msgId: String = "f5ff4f16fb90d07eb9475b5d9b582967",
    val timestamp: Long = System.currentTimeMillis() / 1000
)