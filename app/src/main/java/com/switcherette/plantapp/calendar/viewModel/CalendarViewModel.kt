package com.switcherette.plantapp.calendar.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.switcherette.plantapp.data.WaterEvent
import com.switcherette.plantapp.data.room.WaterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CalendarViewModel(private val waterEventRepo: WaterRepository) : ViewModel() {

    var waterEvents: MutableLiveData<List<WaterEvent>> = MutableLiveData()

    fun getWaterEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = Firebase.auth.currentUser?.uid?.let {
                waterEventRepo.getAllWaterEvents()
            }
            withContext(Dispatchers.Main) {
                waterEvents.value = result!!
            }
        }
    }
}

