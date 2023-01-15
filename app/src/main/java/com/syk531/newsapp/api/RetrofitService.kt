package com.syk531.newsapp.api

import com.syk531.newsapp.api.dto.Company
import com.syk531.newsapp.api.dto.FakeNews
import com.syk531.newsapp.api.dto.NewsDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitService {
    @Headers(
        "X-Naver-Client-Id: WL0ci6ayeGBxtn2xr_K1",
        "X-Naver-Client-Secret: Sr67C6NgSy"
    )
    @GET("v1/search/news.json")
    fun searchNews(
        @Query("query") query: String,
        @Query("display") display: Int,
        @Query("start") start: Int,
        @Query("sort") sort: String
    ): Call<NewsDto>

    @GET("searchCompany")
    fun searchCompany(): Call<List<Company>>

    @GET("searchFakeNews")
    fun searchFakeNews(
        @Query("company") company: String,
        @Query("display") display: Int,
        @Query("start") start: Int
    ): Call<List<FakeNews>>

    @GET("getMostUsedWords")
    fun getMostUsedWords(
        @Query("text") text: String
    ): Call<String>

}