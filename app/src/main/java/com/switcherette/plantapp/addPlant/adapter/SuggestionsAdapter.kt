package com.switcherette.plantapp.addPlant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.switcherette.plantapp.data.Suggestion
import com.switcherette.plantapp.databinding.ItemSuggestionBinding
import kotlin.math.roundToInt

class SuggestionsAdapter(
    private val dataSet: List<Suggestion>,
    private var chooseSuggestion: (Suggestion) -> Unit
) : RecyclerView.Adapter<SuggestionsAdapter.SuggestionViewHolder>() {

    class SuggestionViewHolder(
        private val binding: ItemSuggestionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(suggestion: Suggestion, chooseSuggestion: (Suggestion) -> Unit) {
            binding.tvSciName.text = suggestion.plant_details.scientific_name
            if (suggestion.plant_details.common_names.toString() != "null"){
                binding.tvCommonName.text = suggestion.plant_details.common_names.toString()
            } else {
                binding.tvCommonName.text = ""
            }
            binding.tvProbability.text = "${(suggestion.probability * 10000.0).roundToInt() / 100.0} %"
            binding.btnChoose.setOnClickListener {
                chooseSuggestion(suggestion)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SuggestionViewHolder {
        val binding = ItemSuggestionBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return SuggestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        holder.bind(dataSet[position], chooseSuggestion)
    }


    override fun getItemCount(): Int {
        return dataSet.size
    }
}
