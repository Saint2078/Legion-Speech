package com.bmwgroup.apinext.msft_lib.model.bean

import java.io.Serializable

data class MetaInfo(
    val scenario: String,
    val semantic: List<Semantic>,
    val service: String,
    val status: String,
    val version: String
) : Serializable

