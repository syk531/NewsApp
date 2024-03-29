package com.syk531.newsapp.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val naverInstance: RetrofitService by lazy {
        val gson = GsonBuilder()
                        .setDateFormat("E, dd MMMM yyyy HH:mm:ss X")
                        .create()
        val retrofit = Retrofit.Builder()
                            .baseUrl("https://openapi.naver.com/")
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build()
        retrofit.create(RetrofitService::class.java)
    }

    val raspberryInstance: RetrofitService by lazy {
        val gson : Gson = GsonBuilder()
                                .setLenient()
                                .create()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://220.120.52.234:8081/")
            //.baseUrl("http://14.34.107.170:8081/") //로컬
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        retrofit.create(RetrofitService::class.java)
    }

}