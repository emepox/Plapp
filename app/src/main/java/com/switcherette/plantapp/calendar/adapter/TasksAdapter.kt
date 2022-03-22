package com.switcherette.plantapp.addPlant.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.switcherette.plantapp.R
import com.switcherette.plantapp.data.WaterEvent
import java.text.SimpleDateFormat
import java.util.*

class TasksAdapter(private val dataSet: List<WaterEvent>) :

    RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    class TasksViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val plantNickname: TextView = view.findViewById(R.id.tvTaskItemNickname)
        val wateringFrequency: TextView = view.findViewById(R.id.tvTaskItemWatering)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_task, parent, false)
        return TasksViewHolder(view)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val currentData: WaterEvent = dataSet[position]
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        holder.plantNickname.text = currentData.plantId
        holder.wateringFrequency.text = currentData.repeatInterval.toString()
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}


