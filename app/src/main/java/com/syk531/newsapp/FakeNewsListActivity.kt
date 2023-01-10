package com.syk531.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syk531.newsapp.api.RetrofitClient
import com.syk531.newsapp.api.dto.Company
import com.syk531.newsapp.api.dto.FakeNews
import com.syk531.newsapp.api.dto.NewsItemDto
import com.syk531.newsapp.const.Const
import com.syk531.newsapp.const.NEWS_DISPLAY_COUNT
import kotlinx.android.synthetic.main.activity_fake_news_list.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FakeNewsListActivity : AppCompatActivity() {
    private var fakeNewsList = mutableListOf<FakeNews>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fake_news_list)

        rv_fakeNewsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_fakeNewsList.setHasFixedSize(true)

        val companyId = intent.getStringExtra("companyId")!!
        val companyName = intent.getStringExtra("companyName")!!

        searchFakeNews(companyName, NEWS_DISPLAY_COUNT, 0)

        rv_fakeNewsList.adapter = FakeNewsAdapter(fakeNewsList, companyId)
        rv_fakeNewsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // 마지막 스크롤된 항목 위치
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                // 항목 전체 개수
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1
                if (lastVisibleItemPosition == itemTotalCount) {
                    searchFakeNews(companyName, NEWS_DISPLAY_COUNT, itemTotalCount)
                }
            }
        })
    }

    private fun searchFakeNews(companyName: String, display: Int, start: Int) {
        RetrofitClient.raspberryInstance.searchFakeNews(companyName, display, start).enqueue(object : Callback<List<FakeNews>> {
            override fun onResponse(
                call: Call<List<FakeNews>>,
                response: Response<List<FakeNews>>
            ) {
                if(response.isSuccessful) { // <--> response.code == 200
                    val fakeNewsList = response.body() as MutableList<FakeNews>
                    addFakeNewsList(fakeNewsList)
                } else {
                    // 실패 처리
                    Log.d("debug log", "searchFakeNewsSummaryList not isSuccessful")
                }
            }

            override fun onFailure(call: Call<List<FakeNews>>, t: Throwable) {
                Log.d("debug log", "searchFakeNewsSummaryList onFailure")
                Log.d("debug log", t.toString())
            }
        })
    }

    private fun addFakeNewsList(fakeList: MutableList<FakeNews>) {
        val size = fakeNewsList.size
        fakeNewsList.addAll(fakeList)
        rv_fakeNewsList.adapter?.notifyItemRangeInserted(size, size + NEWS_DISPLAY_COUNT);
    }
}