package com.switcherette.plantapp.addPlant.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.data.WaterEvent
import com.switcherette.plantapp.data.repositories.WaterRepository
import com.switcherette.plantapp.utils.WaterAlarm
import com.switcherette.plantapp.data.repositories.UserPlantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*
import java.util.concurrent.TimeUnit

class PlantFormViewModel(
    private val waterRepository: WaterRepository,
    private val plantRepository: UserPlantRepository
) : ViewModel(), KoinComponent {

    private val waterAlarm: WaterAlarm by inject()
    fun writePlant(userPlant: UserPlant) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = plantRepository.addNewUserPlant(userPlant)
            val waterEvent = createWaterEvent(userPlant, id)
            val firstEvent = waterRepository.getFirstWaterEventByDate()
            with(waterAlarm) {
                if (firstEvent == null) {
                    waterRepository.addNewWaterEvent(waterEvent)
                    createAlarm(waterEvent.repeatStart)
                } else {
                    if (isAlarmSet()) {
                        val nextEvent = firstEvent.repeatStart
                        if (nextEvent > waterEvent.repeatStart) createAlarm(waterEvent.repeatStart)
                    }
                }
                waterRepository.addNewWaterEvent(waterEvent)
            }
            waterAlarm.isAlarmSet()
        }
    }

    private fun createWaterEvent(userPlant: UserPlant, id: Long): WaterEvent {
        val today = Calendar.getInstance().let {
            it[Calendar.HOUR_OF_DAY] = 12
            it[Calendar.MINUTE] = 0
            it[Calendar.SECOND] = 0
            it[Calendar.MILLISECOND] = 0
            it.timeInMillis
        }
        val repeatInterval = TimeUnit.DAYS.toMillis(userPlant.water.toLong())
        val repeatStart = today + repeatInterval
        return WaterEvent(
            plantId = id.toString(),
            repeatStart = repeatStart,
            repeatInterval = repeatInterval
        )
    }
}