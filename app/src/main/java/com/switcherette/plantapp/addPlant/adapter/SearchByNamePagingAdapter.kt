package com.switcherette.plantapp.addPlant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.switcherette.plantapp.data.PlantInfo
import com.switcherette.plantapp.databinding.ItemSearchPlantBynameBinding

class SearchByNamePagingAdapter(
    private var choosePlant: (PlantInfo) -> Unit
):PagingDataAdapter<PlantInfo, PagingViewHolder>(PlantInfoDiffUtil) {

    object PlantInfoDiffUtil : DiffUtil.ItemCallback<PlantInfo>() {
        override fun areItemsTheSame(oldItem: PlantInfo, newItem: PlantInfo): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PlantInfo, newItem: PlantInfo): Boolean = oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        val binding = ItemSearchPlantBynameBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PagingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        holder.bind(getItem(position), choosePlant)
    }
}

class PagingViewHolder(private val binding: ItemSearchPlantBynameBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(plantInfo: PlantInfo?, choosePlant: (PlantInfo) -> Unit){
        with(binding){
            tvPlantName.text = plantInfo?.scientificName?: ""
            tvCommonName.text = plantInfo?.commonName
            btnChoose.setOnClickListener {
                choosePlant(plantInfo!!)
            }
        }
        Glide
            .with(itemView.context)
            .load(plantInfo?.img)
            .centerCrop()
            .into(binding.ivPlantImage);
    }
}
