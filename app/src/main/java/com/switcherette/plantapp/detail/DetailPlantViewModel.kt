package com.switcherette.plantapp.detail

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.switcherette.plantapp.data.PlantInfo
import com.switcherette.plantapp.data.UserPlant
import com.switcherette.plantapp.data.WaterEvent
import com.switcherette.plantapp.data.repositories.UserPlantRepository
import com.switcherette.plantapp.data.repositories.WaterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*
import kotlin.time.Duration.Companion.milliseconds

class DetailPlantViewModel(
    private val userPlantRepo: UserPlantRepository,
    private val waterRepo: WaterRepository
): ViewModel(), KoinComponent {

    val context: Context by inject()

    var daysToWater: MutableLiveData<Long> = MutableLiveData()


    fun deletePlant(plant: UserPlant) {
        viewModelScope.launch(Dispatchers.IO) {
            userPlantRepo.deleteUserPlant(plant)
            withContext(Dispatchers.Main){
                Toast.makeText(context, "Plant deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun deletePlantFromFirebase(plant: UserPlant) {
        val db = Firebase.firestore
        val docRef =
            db.collection("Users").document(plant.userId).collection("Plants")
                .document(plant.id)
        docRef
            .delete()
            .addOnSuccessListener { Log.d("Success", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w("Failure", "Error deleting document", e) }
    }

    fun editPlant(plant: UserPlant) {
        viewModelScope.launch(Dispatchers.IO) {
            userPlantRepo.addNewUserPlant(plant)
        }
    }

    fun editPlantOnFirebase(editedPlant: UserPlant) {
        val db = Firebase.firestore
        val docRef =
            db.collection("Users").document(editedPlant.userId).collection("Plants")
                .document(editedPlant.id)

        val userPlant = hashMapOf(
            "id" to editedPlant.id,
            "nickname" to editedPlant.nickname,
            "scientificName" to editedPlant.scientificName,
            "family" to editedPlant.family,
            "description" to editedPlant.description,
            "cultivation" to editedPlant.cultivation,
            "light" to editedPlant.light,
            "water" to editedPlant.water,
            "image" to editedPlant.image,
            "userId" to editedPlant.userId
        )
        docRef
            .set(userPlant)
            .addOnSuccessListener { Log.d("Success", "Plant successfully updated!") }
            .addOnFailureListener { e -> Log.w("Failure", "Error updating plant", e) }
    }

    fun deleteWaterEvent(plant: UserPlant) {
        viewModelScope.launch(Dispatchers.IO) {
            val waterEvent = waterRepo.getWaterEventByPlantId(plant.id)
            waterRepo.deleteWaterEvent(waterEvent!!)
        }
    }

    fun getDaysToWater(plant: UserPlant) {
        viewModelScope.launch(Dispatchers.IO) {
            val waterEvent = waterRepo.getWaterEventByPlantId(plant.id)
            val nextEvent = waterEvent!!.repeatStart
            val today = Calendar.getInstance().let {
                it[Calendar.HOUR_OF_DAY] = 12
                it[Calendar.MINUTE] = 0
                it[Calendar.SECOND] = 0
                it[Calendar.MILLISECOND] = 0
                it.timeInMillis
            }
            val result = nextEvent - today
            val hours = result.milliseconds.inWholeHours
            val days = hours/24

            withContext(Dispatchers.Main) {
                daysToWater.value = days
            }
        }
    }
}