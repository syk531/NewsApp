package com.syk531.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syk531.newsapp.api.RetrofitClient
import com.syk531.newsapp.api.dto.Company
import com.syk531.newsapp.api.dto.NewsDto
import com.syk531.newsapp.api.dto.NewsItemDto
import com.syk531.newsapp.const.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var companyList = mutableListOf<Company>()
    private var newsItemsList = mutableListOf<NewsItemDto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_newsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_newsList.setHasFixedSize(true)

        searchCompany()

        bt_search.setOnClickListener {
            newsItemsList.clear()

            val str = et_search.text.toString()
            searchNewsList(str, 1)

            rv_newsList.adapter = NewsAdapter(newsItemsList, companyList)
            rv_newsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    // 마지막 스크롤된 항목 위치
                    val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                    // 항목 전체 개수
                    val itemTotalCount = recyclerView.adapter!!.itemCount - 1
                    if (lastVisibleItemPosition == itemTotalCount) {
                        searchNewsList(str, itemTotalCount)
                    }
                }
            })
        }
    }

    private fun searchCompany() {
        RetrofitClient.raspberryInstance.searchCompany().enqueue(object : Callback<List<Company>> {
            override fun onResponse(
                call: Call<List<Company>>,
                response: Response<List<Company>>
            ) {
                if(response.isSuccessful) { // <--> response.code == 200
                    companyList = response.body() as MutableList<Company>
                } else {
                    // 실패 처리
                    Log.d("debug log", "searchFakeNewsSummaryList not isSuccessful")
                }
            }

            override fun onFailure(call: Call<List<Company>>, t: Throwable) {
                Log.d("debug log", "searchFakeNewsSummaryList onFailure")
                Log.d("debug log", t.toString())
            }
        })
    }

    private fun searchNewsList(searchStr: String, startIndex: Int) {
        RetrofitClient.naverInstance.searchNews(searchStr, NEWS_DISPLAY_COUNT, startIndex, NEWS_DEFAULT_SORT).enqueue(object : Callback<NewsDto> {
            override fun onResponse(call: Call<NewsDto>, response: Response<NewsDto>) {
                if(response.isSuccessful) {
                    val items = response.body()?.items as MutableList<NewsItemDto>
                    addNewsList(items)
                } else {
                    // 실패 처리
                    Log.d("debug log", "searchNewsList not isSuccessful")
                }
            }

            override fun onFailure(call: Call<NewsDto>, t: Throwable) {
                Log.d("debug log", "searchNewsList onFailure")
                Log.d("debug log", t.toString())
            }
        })
    }

    private fun addNewsList(newsList: MutableList<NewsItemDto>) {
        val size = newsItemsList.size
        newsItemsList.addAll(newsList)
        rv_newsList.adapter?.notifyDataSetChanged()
    }

}