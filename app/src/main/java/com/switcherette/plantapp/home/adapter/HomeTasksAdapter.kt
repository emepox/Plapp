package com.switcherette.plantapp.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.switcherette.plantapp.R
import com.switcherette.plantapp.data.WaterEvent
import com.switcherette.plantapp.data.repositories.UserPlantRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeTasksAdapter(private val dataSet: List<WaterEvent>) :
    RecyclerView.Adapter<HomeTasksAdapter.TasksViewHolder>(), KoinComponent {

    private val userPlantRepo: UserPlantRepository by inject()

    class TasksViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val plantNickname: TextView = view.findViewById(R.id.tvItemHomeList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_list, parent, false)
        return TasksViewHolder(view)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val waterEvent: WaterEvent = dataSet[position]
        val userPlant = userPlantRepo.getUserPlantByPlantId(waterEvent.plantId)

        holder.plantNickname.text = userPlant.nickname
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}


