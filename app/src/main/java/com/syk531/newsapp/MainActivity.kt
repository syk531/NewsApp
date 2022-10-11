package com.syk531.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.syk531.newsapp.api.RetrofitClient
import com.syk531.newsapp.api.dto.NewsDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RetrofitClient.instance.getNews("한글", 10, 1, "date")
            .enqueue(object : Callback<NewsDto> {
                override fun onResponse(call: Call<NewsDto>, response: Response<NewsDto>) {
                    if(response.isSuccessful) {
                        if(response.code() == 200) {
                            var items = response.body()?.items
                            if (items != null) {
                                for(item in items) {
                                    println(item.title)

                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<NewsDto>, t: Throwable) {
                    println("error")
                }
            })

    }


}