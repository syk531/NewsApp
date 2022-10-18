package com.syk531.newsapp.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val instance: RetrofitService by lazy {
        val gson = GsonBuilder()
                        .setDateFormat("E, dd MMMM yyyy HH:mm:ss X")
                        .create()
        val retrofit = Retrofit.Builder()
                            .baseUrl("https://openapi.naver.com")
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build()
        retrofit.create(RetrofitService::class.java)
    }
}