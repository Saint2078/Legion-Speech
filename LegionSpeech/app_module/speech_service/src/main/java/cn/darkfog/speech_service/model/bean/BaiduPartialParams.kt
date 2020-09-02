package cn.darkfog.speech_service.model.bean

data class BaiduPartialParams(
    val best_result: String,
    val error: Int,
    val origin_result: OriginResult,
    val result_type: String,
    val results_recognition: List<String>
)

data class OriginResult(
    val corpus_no: Long,
    val err_no: Int,
    val result: Result,
    val sn: String
)

data class Result(
    val word: List<String>
)