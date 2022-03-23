package com.switcherette.plantapp.home.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.switcherette.plantapp.data.RandomQuote
import com.switcherette.plantapp.data.WaterEvent
import com.switcherette.plantapp.data.repositories.RandomQuotesRepository
import com.switcherette.plantapp.data.repositories.WaterRepository
import com.switcherette.plantapp.data.room.AppDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeViewModel(
    private val randomQuotesRepo: RandomQuotesRepository,
    private val appDB: AppDB,
    private val waterEventRepo: WaterRepository
) : ViewModel() {

    var quote: MutableLiveData<RandomQuote> = MutableLiveData()

    var waterEventsToday: MutableLiveData<List<WaterEvent>> = MutableLiveData()

    fun getRandomQuote() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = randomQuotesRepo.getRandomQuote()
            withContext(Dispatchers.Main) {
                quote.value = result?.getOrNull(0)
            }
        }
    }

    fun getTodaysWaterEventsByTimeRange(startTime: Long, endTime: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = waterEventRepo.getWaterEventByTimeRange(startTime, endTime)
            waterEventsToday.postValue(result!!)
        }
    }


}