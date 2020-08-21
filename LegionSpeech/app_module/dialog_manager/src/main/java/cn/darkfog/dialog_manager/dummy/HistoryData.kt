package cn.darkfog.dialog_manager.dummy

data class HistoryData(
    val text: String = "",
    val time: Long = 0,
    val state: HistoryState = HistoryState.UNDO
)