package com.switcherette.plantapp.profile.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.data.repositories.SharedPrefsRepository
import com.switcherette.plantapp.data.repositories.UserPlantRepository
import com.switcherette.plantapp.data.repositories.WaterRepository
import com.switcherette.plantapp.utils.NOTIFICATION_TOGGLE_KEY
import com.switcherette.plantapp.utils.WaterAlarm
import com.switcherette.plantapp.utils.createWaterEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MyProfileViewModel(
    private val sharedPrefsRepository: SharedPrefsRepository,
    private val waterRepository: WaterRepository,
    private val waterAlarm: WaterAlarm,
    private val plantRepository: UserPlantRepository
) : ViewModel(), KoinComponent {

    val context: Context by inject()
    val showNotifications: Boolean = sharedPrefsRepository.readBoolean(NOTIFICATION_TOGGLE_KEY)

    fun updateShowNotifications(showNotification: Boolean) {
        sharedPrefsRepository.writeBoolean(NOTIFICATION_TOGGLE_KEY, showNotification)
        if (showNotification) {
            viewModelScope.launch(Dispatchers.IO) {
                val earliestEvent = waterRepository.getFirstWaterEventByDate()
                if (earliestEvent != null) waterAlarm.createAlarm(earliestEvent.repeatStart)
            }
        } else {
            waterAlarm.cancelAlarm()
        }
    }

    fun restoreBackup() {
        val user = (Firebase.auth).currentUser!!.uid
        val docRef = Firebase.firestore.collection("Users")
            .document(user)
            .collection("Plants")
        docRef.get()
            .addOnSuccessListener {
                val responseList = it.documents
                responseList.forEach { documentSnapshot ->
                    val plant = documentSnapshot.toObject(UserPlant::class.java)
                    plantRepository.addNewUserPlant(plant!!)
                    val existingEvent = waterRepository.getWaterEventByPlantId(plant.id)
                    if (existingEvent == null) {
                        val waterEvent = createWaterEvent(plant)
                        waterRepository.addNewWaterEvent(waterEvent)
                    }
                }
                val showNotifications = sharedPrefsRepository.readBoolean(NOTIFICATION_TOGGLE_KEY)
                waterAlarm.cancelAlarm()
                if (showNotifications) {
                    val earliestEvent = waterRepository.getFirstWaterEventByDate()
                    if (earliestEvent != null) waterAlarm.createAlarm(earliestEvent.repeatStart)
                }
                Toast.makeText(context, "Your plant family has been restored", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Log.e("backup", "lol loser, your exception is $it")
            }
    }
}