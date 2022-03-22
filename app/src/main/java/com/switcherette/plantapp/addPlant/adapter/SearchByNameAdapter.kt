package com.switcherette.plantapp.addPlant.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.switcherette.plantapp.R
import com.switcherette.plantapp.data.PlantInfo
import com.switcherette.plantapp.databinding.ItemSearchPlantBynameBinding
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.sql.Date
import java.text.SimpleDateFormat

class SearchByNameAdapter(
    private var choosePlant: (PlantInfo) -> Unit
) : ListAdapter<PlantInfo, SearchByNameAdapter.PlantInfoViewHolder>(SightingsDiffUtil()) {

    class PlantInfoViewHolder(
        private val binding: ItemSearchPlantBynameBinding
    ) : RecyclerView.ViewHolder(binding.root), KoinComponent {

        val context: Context by inject()

        fun bind(plant: PlantInfo, choosePlant: (PlantInfo) -> Unit) {
            with(binding){
                tvPlantName.text = plant.scientificName
                tvCommonName.text = plant.commonName
            }

            Glide
                .with(context)
                .load(plant.img)
                .placeholder(R.drawable.plant_img)
                .centerCrop()
                .into(binding.ivPlantImage);

            binding.btnChoose.setOnClickListener {
                choosePlant(plant)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlantInfoViewHolder {
        val binding = ItemSearchPlantBynameBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return PlantInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlantInfoViewHolder, position: Int) {
        holder.bind(getItem(position), choosePlant)
    }

    class SightingsDiffUtil : DiffUtil.ItemCallback<PlantInfo>() {
        override fun areItemsTheSame(oldItem: PlantInfo, newItem: PlantInfo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PlantInfo, newItem: PlantInfo): Boolean {
            return oldItem.id == newItem.id
        }
    }
}

