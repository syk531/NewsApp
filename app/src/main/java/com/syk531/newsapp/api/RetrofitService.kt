package com.syk531.newsapp.api

import com.syk531.newsapp.api.dto.FakeNewsSummaryDto
import com.syk531.newsapp.api.dto.NewsDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.*

interface RetrofitService {
    @Headers(
        "X-Naver-Client-Id: WL0ci6ayeGBxtn2xr_K1",
        "X-Naver-Client-Secret: Sr67C6NgSy"
    )
    @GET("v1/search/news.json")
    fun getNews(
        @Query("query") query: String,
        @Query("display") display: Int,
        @Query("start") start: Int,
        @Query("sort") sort: String
    ): Call<NewsDto>

    @GET("searchFakeNewsSummary")
    fun searchFakeNewsSummary(): Call<FakeNewsSummaryDto>
}