package com.switcherette.plantapp.addPlant.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.data.WaterEvent
import com.switcherette.plantapp.data.room.WaterRepository
import com.switcherette.plantapp.utils.WaterAlarm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PlantFormViewModel(
    private val waterRepository: WaterRepository,
) : ViewModel(), KoinComponent {

    private val waterAlarm: WaterAlarm by inject()
    fun writePlant(userPlant: UserPlant, waterEvent: WaterEvent) {
        //TODO add firebase write method
        // receive plant id returned from the firebase
        // waterEvent.plantId = //whatever returned from the firebase
        waterRepository.addNewWaterEvent(waterEvent)
        with(waterAlarm) {
            if (isAlarmSet()) {
                viewModelScope.launch(Dispatchers.IO) {
                    val nextEvent = waterRepository.getFirstWaterEventByDate().repeatStart
                    if (nextEvent > waterEvent.repeatStart) createAlarm(waterEvent.repeatStart)
                }
            } else {
                createAlarm(waterEvent.repeatStart)
            }
        }

    }
}