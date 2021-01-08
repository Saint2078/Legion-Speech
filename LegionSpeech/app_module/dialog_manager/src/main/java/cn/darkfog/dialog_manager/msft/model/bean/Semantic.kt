package com.bmwgroup.apinext.msft_lib.model.bean

import java.io.Serializable

data class Semantic(
    val action: String,
    val commandId: String,
    val deviceType: String,
    val productId: String,
    val properties: Properties,
    val target: String
) : Serializable