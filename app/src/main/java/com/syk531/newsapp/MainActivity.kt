package com.syk531.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        val bt_search = findViewById<Button>(R.id.bt_search)
        bt_search.setOnClickListener {
            val et_search = findViewById<EditText>(R.id.et_search)
            search(et_search.text.toString())
        }

    }

    fun search(searchText: String) {
        var items = mutableListOf<NewsItemDto>()

        val rv_newsList: RecyclerView = findViewById(R.id.rv_newsList)
        rv_newsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_newsList.setHasFixedSize(true)

        thread {
            val response = RetrofitClient.instance.getNews(searchText, 10, 1, "date").execute()

            CoroutineScope(Dispatchers.Main).launch {
                if(response.isSuccessful) {
                    if(response.code() == 200) {
                        items = response.body()?.items as MutableList<NewsItemDto>
                        rv_newsList.adapter = NewsAdapter(items)
                    }
                }
            }

        }
    }

}
