package cn.darkfog.legionspeech.view

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import cn.darkfog.foundation.AppContextLinker
import cn.darkfog.legionspeech.R
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.ScreenUtils
import com.yhao.floatwindow.FloatWindow
import com.yhao.floatwindow.MoveType
import com.yhao.floatwindow.Screen

/**
 * 出发方式：点击
 * 状态：出错，识别中，处理中，空闲
 */
object SpeechView {

    enum class ViewState(val id: Int) {
        IDLE(R.drawable.ic_microphone),
        ERROR(R.drawable.ic_error),
        SPEAKING(R.drawable.ic_speak),
        PROCESSING(R.drawable.ic_voiceprint),
    }

    private const val TAG = "SpeechView"

    private val state = MutableLiveData<ViewState>().apply {
        postValue(ViewState.IDLE)
    }

    private val icon: ImageView
    private val view: View

    init {
        FloatWindow.with(AppContextLinker.context)
            .setView(R.layout.speech_view_layout)
            .setWidth(AdaptScreenUtils.pt2Px(30f))
            .setHeight(AdaptScreenUtils.pt2Px(30f))
            .setX(ScreenUtils.getAppScreenWidth() - AdaptScreenUtils.pt2Px(30f) - 10)
            .setY(Screen.height, 0.5f)
            .setDesktopShow(true)
            .setMoveType(MoveType.slide)
            .setTag(TAG)
            .build()
        view = FloatWindow.get(TAG).view
        icon = view.findViewById(R.id.content)
        view.setOnClickListener {
            when (state.value) {
                ViewState.IDLE -> ""
                else -> ""
            }
        }
    }

    fun setViewState(state: ViewState) {
        icon.setImageResource(state.id)
    }

    fun show() {
        FloatWindow.get(TAG).show()
    }

    fun hide() {
        FloatWindow.get(TAG).hide()
    }
}

