package com.switcherette.plantapp.data.repositories


import android.util.Log
import com.switcherette.plantapp.data.RandomQuote
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET



class RandomQuotesRepository {

    private val BASE_URL = "https://zenquotes.io/api/"
    private val retrofit = createRetrofit()
    private val service: RandomQuotesService = retrofit.create(RandomQuotesService ::class.java)


    private fun createRetrofit(): Retrofit {
         return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun getRandomQuote(): List<RandomQuote>? {
        val response = service.getRandomQuote().execute()
        return if (response.isSuccessful) {
            response.body()!!
        } else {
            Log.e("HTTP Error Tag", "${response.errorBody()}")
            null
        }
    }


    interface RandomQuotesService  {

        @GET("random")
        fun getRandomQuote(): Call<List<RandomQuote>?>

    }

}



