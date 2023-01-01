package com.syk531.newsapp

import android.content.Intent
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.syk531.newsapp.api.dto.Company
import com.syk531.newsapp.api.dto.NewsItemDto
import kotlinx.android.synthetic.main.list_item.view.*
import org.apache.commons.text.StringEscapeUtils
import java.text.SimpleDateFormat
import java.util.*

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
        holder.cnt.text = cnt.toString()
        holder.putDate.text = putDate
        holder.title.text = title
        holder.description.text = description

        //가짜뉴스 건수 클릭
        holder.itemView.tv_cnt.setOnClickListener {
            val intent = Intent(it.context, FakeNewsListActivity::class.java)
            intent.putExtra("companyId", companyId)
            intent.putExtra("companyName", companyName)
            ContextCompat.startActivity(it.context, intent, null)
        }

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
        val cnt = itemView.findViewById<TextView>(R.id.tv_cnt)
        val putDate = itemView.findViewById<TextView>(R.id.tv_pubDate)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val description = itemView.findViewById<TextView>(R.id.tv_description)
    }
}