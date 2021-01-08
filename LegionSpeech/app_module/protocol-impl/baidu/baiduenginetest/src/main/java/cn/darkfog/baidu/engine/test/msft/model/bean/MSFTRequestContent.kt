package com.bmwgroup.apinext.msft_lib.model.bean

data class MSFTRequestContent(
    val text: String? = null,
    val imageUrl: String? = null,
    val imageBase64: String? = null,
    val audioUrl: String? = null,
    val audioBase64: String? = null,
    val videoUrl: String? = null,
    val metaData: HashMap<String, String>? = hashMapOf(
        "CarConfig" to "{\"E18CE8DD-55E0-B12D-F847-FBA996FE2212\": {\"position\": \"前排\", \"productId\": \"test-productId\", \"deviceType\": \"airConditioner\", \"code\": 1}}",
        "CarStatus" to "{\"E18CE8DD-55E0-B12D-F847-FBA996FE2212\": {\"action\": \"close\", \"properties\": {\"sync\": \"0\", \"windDirection\": \"7\", \"auto\": \"0\", \"tempreature\": \"24\", \"temperature_deputy\": \"24\", \"speed\": \"6\", \"cycle\": \"1\"}}}"
    )
)
