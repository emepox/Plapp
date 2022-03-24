package com.switcherette.plantapp.addPlant.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.data.WaterEvent
import com.switcherette.plantapp.data.repositories.SharedPrefsRepository
import com.switcherette.plantapp.data.repositories.UserPlantRepository
import com.switcherette.plantapp.data.repositories.WaterRepository
import com.switcherette.plantapp.utils.NOTIFICATION_TOGGLE_KEY
import com.switcherette.plantapp.utils.WaterAlarm
import com.switcherette.plantapp.utils.createWaterEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import java.util.*
import java.util.concurrent.TimeUnit

class PlantForm2ViewModel(
    private val waterRepository: WaterRepository,
    private val plantRepository: UserPlantRepository,
    private val sharedPrefsRepository: SharedPrefsRepository,
    private val waterAlarm: WaterAlarm
) : ViewModel(), KoinComponent {

    fun writePlant(userPlant: UserPlant) {
        viewModelScope.launch(Dispatchers.IO) {
            plantRepository.addNewUserPlant(userPlant)
            val waterEvent = createWaterEvent(userPlant)
            val firstEvent = waterRepository.getFirstWaterEventByDate()
            val showNotifications = sharedPrefsRepository.readBoolean(NOTIFICATION_TOGGLE_KEY)
            with(waterAlarm) {
                if (firstEvent == null) {
                   /* waterRepository.addNewWaterEvent(waterEvent)*/
                    if(showNotifications) createAlarm(waterEvent.repeatStart)
                } else {
                    if (isAlarmSet()) {
                        val nextEvent = firstEvent.repeatStart
                        if (nextEvent > waterEvent.repeatStart && showNotifications) createAlarm(waterEvent.repeatStart)
                    }
                }
                waterRepository.addNewWaterEvent(waterEvent)
            }
        }
    }

    fun addPlantToFirestore(finalUserPlant: UserPlant?) {
        val db = Firebase.firestore
        val docRef =
            db.collection("Users").document("${finalUserPlant?.userId}").collection("Plants")
                .document("${finalUserPlant?.id}")
        val userPlant = hashMapOf(
            "id" to finalUserPlant?.id,
            "nickname" to finalUserPlant?.nickname,
            "scientificName" to finalUserPlant?.scientificName,
            "family" to finalUserPlant?.family,
            "description" to finalUserPlant?.description,
            "cultivation" to finalUserPlant?.cultivation,
            "light" to finalUserPlant?.light,
            "water" to finalUserPlant?.water,
            "image" to finalUserPlant?.image,
            "userId" to finalUserPlant?.userId
        )
        docRef
            .set(userPlant)
            .addOnSuccessListener { Log.d("Success", "Plant successfully added!") }
            .addOnFailureListener { e -> Log.w("Failure", "Error writing plant", e) }

    }
}