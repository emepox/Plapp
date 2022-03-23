package com.switcherette.plantapp.calendar.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.switcherette.plantapp.data.WaterEvent
import com.switcherette.plantapp.data.repositories.WaterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendarViewModel(private val waterEventRepo: WaterRepository) : ViewModel() {

    var allWaterEvents: MutableLiveData<List<WaterEvent>> = MutableLiveData()
    var waterEventsPerDay: MutableLiveData<List<WaterEvent>> = MutableLiveData()

    fun getWaterEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = Firebase.auth.currentUser?.uid?.let {
                waterEventRepo.getAllWaterEvents()
            }
            allWaterEvents.postValue(result!!)
        }
    }

    fun getWaterEventByTimeRange(startTime: Long, endTime: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = waterEventRepo.getWaterEventByTimeRange(startTime, endTime)
            waterEventsPerDay.postValue(result!!)
        }
    }
}

