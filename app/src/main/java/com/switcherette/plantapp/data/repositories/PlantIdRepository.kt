package com.switcherette.plantapp.data.repositories

import android.R.attr.apiKey
import android.content.Context
import android.util.Log
import com.switcherette.plantapp.BuildConfig
import com.switcherette.plantapp.data.PlantId
import com.switcherette.plantapp.utils.convertToBase64
import org.json.JSONArray
import org.json.JSONObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


class PlantIdRepository : KoinComponent {

    private val BASE_URL = "https://api.plant.id/v2/"
    private val retrofit = createRetrofit()
    private val service: PlantIdService = retrofit.create(PlantIdService::class.java)

    private val context: Context by inject()


    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun getPlantId(image: String): PlantId? {
        // add api key
        val data = JSONObject()
        //data.put("api_key", BuildConfig.API_KEY)

        // add images
        val images = JSONArray()
        val fileData: String = convertToBase64(image)
        images.put(fileData)
        data.put("images", images)

        val response = service.getPlantId(data).execute()
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            Log.e("HTTP Error Tag", "${response.errorBody()}")
            null
        }
    }


    interface PlantIdService {
        // In the interface we will define the structure of our requests.
        @Headers("api_key: " + BuildConfig.API_KEY)
        @POST("identify/")
        fun getPlantId(@Body data : JSONObject): Call<PlantId?>

    }


}