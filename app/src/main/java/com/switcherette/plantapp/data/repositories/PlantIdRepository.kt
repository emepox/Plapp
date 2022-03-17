package com.switcherette.plantapp.data.repositories

import android.content.Context
import android.net.Uri
import android.util.Log
import com.switcherette.plantapp.BuildConfig
import com.switcherette.plantapp.data.PlantId
import com.switcherette.plantapp.data.PlantsRequest
import com.switcherette.plantapp.utils.convertToBase64
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
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

}



