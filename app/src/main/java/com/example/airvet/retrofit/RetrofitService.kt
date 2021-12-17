package com.example.airvet.retrofit

import com.example.airvet.model.UserResult
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface RetrofitService {
    @GET("/api/")
    suspend fun getAllUsers(@Query("results") results: Int) : Response<UserResult>

    companion object {
        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService? {
            if (retrofitService == null) {
                val okHttpClient = OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES) // write timeout
                    .readTimeout(1, TimeUnit.MINUTES)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://randomuser.me")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService
        }
    }
}
