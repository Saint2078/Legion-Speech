package cn.darkfog.dialog_manager.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.darkfog.dialog_manager.R
import cn.darkfog.dialog_manager.model.bean.SpeechRecord

class HistoryRecyclerViewAdapter(
    private val values: List<SpeechRecord>,
    private val callback: (String, SpeechRecord) -> Unit
) : RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_history_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = values[position]
        holder.asr.text = record.asr?.content
        holder.action.text = record.nlu?.intent

        holder.play.setOnClickListener {
            callback("play", record)
        }

        holder.confirm.setOnClickListener {
            callback("confirm", record)
        }

        holder.detail.setOnClickListener {
            callback("detail", record)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val play = view.findViewById<ImageView>(R.id.play)
        val confirm = view.findViewById<ImageView>(R.id.confirm)
        val detail = view.findViewById<Button>(R.id.detail)


        val asr = view.findViewById<TextView>(R.id.asrcontent)
        val action = view.findViewById<TextView>(R.id.actionContent)
    }

}