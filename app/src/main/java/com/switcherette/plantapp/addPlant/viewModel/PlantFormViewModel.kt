package com.switcherette.plantapp.addPlant.viewModel

import androidx.lifecycle.ViewModel
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.data.WaterEvent
import com.switcherette.plantapp.room.WaterRepository
import com.switcherette.plantapp.utils.WaterAlarm
import org.koin.core.component.KoinComponent

class PlantFormViewModel(
    private val waterRepository: WaterRepository,
    private val waterAlarm: WaterAlarm
) : ViewModel(), KoinComponent {

    fun writePlant(userPlant: UserPlant, waterEvent: WaterEvent) {
        //TODO add firebase write method
        // receive plant id returned from the firebase
        // waterEvent.plantId = //whatever returned from the firebase
        waterRepository.addNewWaterEvent(waterEvent)
        if (waterAlarm.checkIfNotSet()) {
            waterAlarm.createAlarm(waterEvent.repeatStart)
        }
    }
}