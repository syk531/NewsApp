package com.syk531.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syk531.newsapp.api.RetrofitClient
import com.syk531.newsapp.api.dto.NewsDto
import com.syk531.newsapp.api.dto.NewsItemDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var items = mutableListOf<NewsItemDto>()

        val rv_newsList: RecyclerView = findViewById(R.id.rv_newsList)
        rv_newsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_newsList.setHasFixedSize(true)

        thread {
            val response = RetrofitClient.instance.getNews("한글", 10, 1, "date").execute()

            CoroutineScope(Dispatchers.Main).launch {
                if(response.isSuccessful) {
                    if(response.code() == 200) {
                        items = response.body()?.items as MutableList<NewsItemDto>
                        println(123123123)

                        rv_newsList.adapter = NewsAdapter(items)
                    }
                }
            }

        }

        println(2342342343)

    }

}