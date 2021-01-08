package com.bmwgroup.apinext.msft_lib.model.bean

class MSFTResponse : ArrayList<MSFTResponseItem>()

data class MSFTResponseItem(
    val content: Content,
    val masterId: String,
    val msgId: String,
    val receiverId: String,
    val status: Int,
    val timestamp: Int,
    val triggerTime: Int
)

data class Content(
    val audioUrl: String?,
    val metadata: Metadata,
    val text: String
)

data class Metadata(
    val CINR_Start_MultiMedia: String?,
    val CINR_Start_Proactive: String?,
    val CINR_Start: String?,
    val EmotionType: String?
)

data class MediaProtocol(
    val scenario: String,
    val semantic: List<Semantic>,
    val service: String,
    val status: String,
    val version: String
) {

    data class Semantic(
        val action: String,
        val properties: MSFTProperties
    ) {

        data class MSFTProperties(
            val category: List<String>,
            val singer: List<String>
        )
    }
}
