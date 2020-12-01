package com.solo.runtracker.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.solo.runtracker.R
import com.solo.runtracker.database.Run
import com.solo.runtracker.getDate

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    var data =  listOf<Run>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val startTime : TextView = itemView.findViewById(R.id.li_tv_start_time_value)
        val endTime : TextView = itemView.findViewById(R.id.li_tv_end_time_value)
        val runningTime : TextView = itemView.findViewById(R.id.li_tv_running_time_value)
        val runFeedback : TextView = itemView.findViewById(R.id.li_tv_feedback_value)

        fun bind(item: Run) {
            startTime.text = getDate(item.startTimeMilli)
            endTime.text = getDate(item.endTimeMilli)
            runningTime.text = item.elapsedTime
            if(item.runFeedback==1)
                runFeedback.text = " It was great!"
            else if (item.runFeedback==0)
                runFeedback.text = " Didn't like it"

        }

        companion object {
            fun from(parent: ViewGroup): RunViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.run_list_item, parent, false)
                return RunViewHolder(view)
            }
        }
    }
}
