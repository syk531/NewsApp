package com.syk531.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syk531.newsapp.api.RetrofitClient
import com.syk531.newsapp.api.dto.NewsDto
import com.syk531.newsapp.api.dto.NewsItemDto
import jxl.Workbook
import jxl.read.biff.BiffException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.io.InputStream
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bt_search: Button = findViewById(R.id.bt_search)
        bt_search.setOnClickListener {
            val et_search: EditText = findViewById(R.id.et_search)
            search(et_search.text.toString())
        }
    }

    fun search(str: String) {
        var items = mutableListOf<NewsItemDto>()

        val rv_newsList: RecyclerView = findViewById(R.id.rv_newsList)
        rv_newsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_newsList.setHasFixedSize(true)

        thread {
            val response = RetrofitClient.instance.getNews(str, 10, 1, "sim").execute()

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