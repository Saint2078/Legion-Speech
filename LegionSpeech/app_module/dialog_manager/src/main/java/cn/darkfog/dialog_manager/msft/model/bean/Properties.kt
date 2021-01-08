package com.bmwgroup.apinext.msft_lib.model.bean

import java.io.Serializable

data class Properties(
    val tag: List<String>,
    val category: List<String>,
    val singer: List<String>,
    val song: List<String>
) : Serializable