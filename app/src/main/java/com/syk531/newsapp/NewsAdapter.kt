package com.syk531.newsapp

import android.content.Intent
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.syk531.newsapp.api.RetrofitClient
import com.syk531.newsapp.api.dto.Company
import com.syk531.newsapp.api.dto.NewsItemDto
import kotlinx.android.synthetic.main.list_item.view.*
import org.bitbucket.eunjeon.seunjeon.Analyzer
import org.bitbucket.eunjeon.seunjeon.LNode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class NewsAdapter(val newsList: MutableList<NewsItemDto>, val companyList: MutableList<Company>) : RecyclerView.Adapter<NewsAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsAdapter.CustomViewHolder, position: Int) {
        val newsItem = newsList.get(position)

        val sf = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val putDate = sf.format(newsItem.pubDate)
        val title = Html.fromHtml(newsItem.title, Html.FROM_HTML_MODE_LEGACY)
        val description = Html.fromHtml(newsItem.description, Html.FROM_HTML_MODE_LEGACY)

        var cnt = 0
        var companyId = ""
        var companyName = ""
        var isCompanyChecked = false
        for(company: Company in companyList) {
            val urlTextList = company.urlTextList

            for(urlText : String in urlTextList) {
                if(newsItem.originallink.contains(urlText)) {
                    isCompanyChecked = true
                    cnt = company.cnt
                    companyName = company.name
                    companyId = company.id
                    break;
                }
            }

            if(isCompanyChecked) {
                break;
            }
        }

        holder.companyName.text = companyName
        holder.putDate.text = putDate
        holder.title.text = title.toString()
        holder.description.text = description.toString()

        RetrofitClient.raspberryInstance.getMostUsedWords(description.toString()).enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                if(response.isSuccessful) { // <--> response.code == 200
                    holder.mostWord.text = response.body().toString()
                } else {
                    // 실패 처리
                    Log.d("debug log", "getMostUsedWords not isSuccessful")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("debug log", "getMostUsedWords onFailure")
                Log.d("debug log", t.toString())
            }
        })

        val companyImageId: Int = holder.itemView.context.resources.getIdentifier("company_$companyId", "drawable", "com.syk531.newsapp")
        holder.companyImage.setImageResource(companyImageId)

        //본문 영역 클릭
        holder.itemView.cl_list2.setOnClickListener {
            val newsItem: NewsItemDto = newsList.get(position)

            val intent = Intent(it.context, NewsDetailActivity::class.java)
            intent.putExtra("originallink", newsItem.originallink)
            ContextCompat.startActivity(it.context, intent, null)
        }
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val companyName = itemView.findViewById<TextView>(R.id.tv_companyName)
        val mostWord = itemView.findViewById<Button>(R.id.tv_mostWord)
        val putDate = itemView.findViewById<TextView>(R.id.tv_pubDate)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val description = itemView.findViewById<TextView>(R.id.tv_description)
        val companyImage = itemView.findViewById<ImageView>(R.id.iv_companyImage)
    }
}