package com.syk531.newsapp.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val instance: RetrofitService by lazy {
        val retrofit = Retrofit.Builder()
                            .baseUrl("https://openapi.naver.com")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
        retrofit.create(RetrofitService::class.java)
    }
}