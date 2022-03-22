package com.switcherette.plantapp.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.switcherette.plantapp.data.repositories.SharedPrefsRepository
import com.switcherette.plantapp.data.repositories.WaterRepository
import com.switcherette.plantapp.utils.NOTIFICATION_TOGGLE_KEY
import com.switcherette.plantapp.utils.WaterAlarm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class MyProfileViewModel(
    private val sharedPrefsRepository: SharedPrefsRepository,
    private val waterRepository: WaterRepository,
    private val waterAlarm: WaterAlarm
): ViewModel(), KoinComponent {

    val showNotifications: Boolean = sharedPrefsRepository.readBoolean(NOTIFICATION_TOGGLE_KEY)

    fun updateShowNotifications(showNotification: Boolean){
        sharedPrefsRepository.writeBoolean(NOTIFICATION_TOGGLE_KEY, showNotification)
        if(showNotification){
            viewModelScope.launch(Dispatchers.IO) {
                val earliestEvent = waterRepository.getFirstWaterEventByDate()
                if(earliestEvent != null) waterAlarm.createAlarm(earliestEvent.repeatStart)
            }
        }else{
            waterAlarm.cancelAlarm()
        }
    }
}