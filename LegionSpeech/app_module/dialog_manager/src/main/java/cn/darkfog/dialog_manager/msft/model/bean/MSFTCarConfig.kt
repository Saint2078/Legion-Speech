package com.bmwgroup.apinext.msft_lib.model.bean

data class Device(
    val code: Int,
    val deviceType: String,
    val position: String,
    val productId: String
)

data class DevicStatus(
    val action: String,
    val properties: Properties
) {
    data class Properties(
        val inductor: String,
        val openPercentage: String,
        val speed: String
    )
}

