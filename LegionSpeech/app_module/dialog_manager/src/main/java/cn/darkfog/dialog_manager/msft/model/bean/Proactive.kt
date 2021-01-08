package com.bmwgroup.apinext.msft_lib.model.bean

/**
{
"scenario": "Car",
"semantic": [
{
"action": "set",
"properties": {
"tag": "激昂",
"category": "play"
},
"commandId": "491851F1DF3FE7D1B9972965E9381485",
"target": "",
"deviceType": "voiceBox",
"productId": ""
}
],
"service": "CarControl",
"version": "1.0",
"status": "0"
}
 */

data class Proactive(
    val scenario: String,
    val semantic: List<Semantic>?,
    val service: String,
    val status: String,
    val version: String
) {
    data class Semantic(
        val action: String?,
        val commandId: String?,
        val deviceType: String?,
        val productId: String?,
        val properties: Properties?,
        val target: String?
    )

    data class Properties(
        val category: String?,
        val tag: String?
    )
}