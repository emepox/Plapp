package com.switcherette.plantapp.data.repositories

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.switcherette.plantapp.BuildConfig
import com.switcherette.plantapp.data.PlantId
import com.switcherette.plantapp.utils.convertToBase64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class PlantIdRepo {

    private val BASE_URL = "https://api.plant.id/v2/"
    private val retrofit = createRetrofit()
    private val service: PlantIdService = retrofit.create(PlantIdService::class.java)

    private var plant: PlantId? = null

    // create retrofit
    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // implement interface functions (interface is also defined inside class)
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getPlantId(image: String): PlantId? {
        val encodedImage = convertToBase64(image)
        val response = service.getPlantId(encodedImage).execute()
        return if (response.isSuccessful) {
            withContext(Dispatchers.Main){
                this@PlantIdRepo.plant = response.body()
            }
            response.body()!!
        } else {
            Log.e("HTTP Error Tag", "${response.errorBody()}")
            null
        }
    }


    interface PlantIdService {
        // In the interface we will define the structure of our requests.
        @Headers("api-key: " + BuildConfig.API_KEY)
        @POST("identify/")
        fun getPlantId(@Body image: String): Call<PlantId>

    }


}