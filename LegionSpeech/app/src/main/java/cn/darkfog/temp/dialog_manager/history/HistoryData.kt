package cn.darkfog.temp.dialog_manager.history

import cn.darkfog.temp.dialog_manager.detail.Detail

data class HistoryData(
    val text: String = "",
    val time: Long = 0,
    val state: HistoryState = HistoryState.UNDO,
    val detail: Detail
)