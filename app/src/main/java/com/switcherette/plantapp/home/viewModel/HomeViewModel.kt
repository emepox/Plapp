package com.switcherette.plantapp.home.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.switcherette.plantapp.data.RandomQuote
import com.switcherette.plantapp.data.WaterEvent
import com.switcherette.plantapp.data.repositories.RandomQuotesRepository
import com.switcherette.plantapp.data.repositories.WaterRepository
import com.switcherette.plantapp.utils.addAndCalculateNextWaterEvents
import com.switcherette.plantapp.utils.filterEventsByTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeViewModel(
    private val randomQuotesRepo: RandomQuotesRepository,
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
            val allEvents = waterEventRepo.getAllWaterEvents()
            val allFutureEvents = addAndCalculateNextWaterEvents(allEvents)
            val todaysEvents = filterEventsByTime(allFutureEvents, startTime, endTime)
            waterEventsToday.postValue(todaysEvents)
        }
    }
}