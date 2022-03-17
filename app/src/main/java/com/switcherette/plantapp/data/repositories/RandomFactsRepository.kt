package com.switcherette.plantapp.data.repositories


import android.util.Log
import com.switcherette.plantapp.data.RandomFact
import org.koin.core.component.KoinComponent
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET



class RandomFactsRepository : KoinComponent {

    private val BASE_URL = "https://zenquotes.io/api/random"
    private val retrofit = createRetrofit()
    private val service: RandomFactsService = retrofit.create(RandomFactsService ::class.java)


    private fun createRetrofit(): Retrofit {
         return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun getRandomFact(): RandomFact? {
        val response = service.getRandomFact().execute()
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            Log.e("HTTP Error Tag", "${response.errorBody()}")
            null
        }
    }


    interface RandomFactsService  {

        @GET()
        fun getRandomFact(): Call<RandomFact?>

    }

}



