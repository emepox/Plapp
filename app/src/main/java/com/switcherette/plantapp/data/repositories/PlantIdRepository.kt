package com.switcherette.plantapp.data.repositories

import android.content.Context
import android.net.Uri
import android.util.Log
import com.switcherette.plantapp.BuildConfig
import com.switcherette.plantapp.data.PlantId
import com.switcherette.plantapp.data.PlantsRequest
import com.switcherette.plantapp.utils.convertToBase64
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


class PlantIdRepository : KoinComponent {

    private val BASE_URL = "https://api.plant.id/v2/"
    private val retrofit = createRetrofit()
    private val service: PlantIdService = retrofit.create(PlantIdService::class.java)

    private val context: Context by inject()


    private fun createRetrofit(): Retrofit {

        val interceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) //GsonBuilder().serializeNulls().create()
            .client(client)
            .build()
    }


    fun getPlantId(image: Uri): PlantId? {
        val fileData: String = convertToBase64(context, image)
        //change 'fileData' to 'testData' to test API
        val response = service.getPlantId(PlantsRequest(listOf(fileData))).execute()
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            Log.e("HTTP Error Tag", "${response.errorBody()}")
            null
        }
    }


    interface PlantIdService {

        @Headers("Api-Key: " + BuildConfig.API_KEY)
        @POST("identify")
        fun getPlantId(@Body data: PlantsRequest): Call<PlantId?>

    }

    //Base64 version of a plant, for testing purposes
    val testData: String = "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"

}



