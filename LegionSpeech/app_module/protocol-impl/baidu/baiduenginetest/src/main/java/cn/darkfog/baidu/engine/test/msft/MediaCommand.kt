package com.bmwgroup.apinext.china_speech_poc.common.media

import java.io.Serializable

/**
 *
{
"scenario": "Media",
"semantic": [
{
"properties": {
"singer": [
"周杰伦"
],
"category": [
"音乐"
]
},
"action": "PlaySong"
}
],
"service": "MediaControl",
"version": "1.0",
"status": "0"
}
 */
data class MediaCommand(
    val scenario: String,
    val semantic: List<Semantic>,
    val service: String,
    val status: String,
    val version: String
) : Serializable

data class Semantic(
    val action: String,
    val properties: Properties?
) : Serializable

data class Properties(
    val tag: List<String?>?,
    val category: List<String?>?,
    val singer: List<String?>?,
    val song: List<String?>?
) : Serializable