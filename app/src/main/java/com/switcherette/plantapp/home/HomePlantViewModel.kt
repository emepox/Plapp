package com.switcherette.plantapp.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.switcherette.plantapp.data.RandomQuote
import com.switcherette.plantapp.data.repositories.RandomQuotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomePlantViewModel(
    private val randomQuotesRepo: RandomQuotesRepository
) : ViewModel() {

    var quote: MutableLiveData<RandomQuote> = MutableLiveData()

    fun getRandomQuote() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = randomQuotesRepo.getRandomQuote()
            withContext(Dispatchers.Main) {
                quote.value = result?.getOrNull(0)
            }
        }

    }

}