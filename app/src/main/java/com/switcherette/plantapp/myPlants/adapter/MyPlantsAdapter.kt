package com.switcherette.plantapp.myPlants.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.switcherette.plantapp.R
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.databinding.ItemPlantMyplantsBinding
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File

class MyPlantsAdapter(
    private val dataSet: List<UserPlant>,
    private var seePlantDetails: (UserPlant) -> Unit
) : RecyclerView.Adapter<MyPlantsAdapter.MyPlantViewHolder>() {

    class MyPlantViewHolder(
        private val binding: ItemPlantMyplantsBinding
    ) : RecyclerView.ViewHolder(binding.root), KoinComponent {

        val context: Context by inject()

        fun bind(plant: UserPlant, seePlantDetails: (UserPlant) -> Unit) {
            binding.tvPlantName.text = plant.nickname

            if (plant.image?.contains("http") == false){
                val uri = Uri.parse(plant.image)
                binding.ivPlantImage.setImageURI(uri)
            } else {
                Glide
                    .with(context)
                    .load(plant.image)
                    .centerCrop()
                    .into(binding.ivPlantImage);
            }

            binding.root.setOnClickListener {
                seePlantDetails(plant)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPlantViewHolder {
        val binding = ItemPlantMyplantsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MyPlantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPlantViewHolder, position: Int) {
        holder.bind(dataSet[position], seePlantDetails)
    }


    override fun getItemCount(): Int {
        return dataSet.size
    }
}
