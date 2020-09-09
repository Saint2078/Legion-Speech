package cn.darkfog.dialog_manager.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.darkfog.dialog_manager.R
import cn.darkfog.dialog_manager.model.bean.SpeechRecord
import cn.darkfog.speech_protocol.speech.bean.ASR
import cn.darkfog.speech_protocol.speech.bean.NLU

/**
 * A fragment representing a list of Items.
 */
class HistoryFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = HistoryRecyclerViewAdapter(
                    listOf(
                        SpeechRecord(
                            System.currentTimeMillis(), "test.pcm",
                            ASR("我要听周杰伦的歌"),
                            NLU("", "放歌", Bundle())
                        ), SpeechRecord(
                            System.currentTimeMillis(), "test.pcm",
                            ASR("我要听周杰伦的歌131313133"),
                            NLU("", "放歌", Bundle())
                        )
                    ), ::playOrDetail
                )
            }
        }
        return view
    }

    fun playOrDetail(type: String, record: SpeechRecord) {

    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}