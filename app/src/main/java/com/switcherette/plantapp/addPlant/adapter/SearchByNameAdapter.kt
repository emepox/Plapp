package com.switcherette.plantapp.addPlant.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.switcherette.plantapp.data.PlantInfo
import com.switcherette.plantapp.databinding.ItemSearchPlantBynameBinding
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchByNameAdapter(
    private val dataSet: List<PlantInfo>,
    private var choosePlant: (PlantInfo) -> Unit
) : RecyclerView.Adapter<SearchByNameAdapter.PlantInfoViewHolder>() {

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
                .centerCrop()
                .into(binding.ivPlantImage);

//            if (plant.image?.contains("http")!!){
//                Glide
//                    .with(context)
//                    .load(File(plant.image!!))
//                    .centerCrop()
//                    .into(binding.ivPlantImage);
//            } else {
//                Glide
//                    .with(context)
//                    .load(plant.image)
//                    .centerCrop()
//                    .into(binding.ivPlantImage);
//            }

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

    override fun onBindViewHolder(holderInfo: PlantInfoViewHolder, position: Int) {
        holderInfo.bind(dataSet[position], choosePlant)
    }


    override fun getItemCount(): Int {
        return dataSet.size
    }
}
