package com.syk531.newsapp

import android.content.Intent
import android.text.Html
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
import org.apache.commons.text.StringEscapeUtils
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(val newsList: MutableList<NewsItemDto>, val companyList: MutableList<Company>) : RecyclerView.Adapter<NewsAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos: Int = absoluteAdapterPosition
                val newsItem: NewsItemDto = newsList.get(curPos)

                val intent = Intent(itemView.context, NewsDetailActivity::class.java)
                intent.putExtra("originallink", newsItem.originallink)
                ContextCompat.startActivity(itemView.context, intent, null)
            }
        }
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

        var isCompanyChecked = false

        var cnt = 0
        for(company: Company in companyList) {
            val urlTextList = company.urlTextList

            for(urlText : String in urlTextList) {
                if(newsItem.originallink.contains(urlText)) {
                    isCompanyChecked = true
                    cnt = company.cnt
                    break;
                }
            }

            if(isCompanyChecked) {
                break;
            }
        }

        holder.putDate.text = putDate
        holder.cnt.text = cnt.toString()
        holder.title.text = title
        holder.description.text = description
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val putDate = itemView.findViewById<TextView>(R.id.tv_pubDate)
        val cnt = itemView.findViewById<TextView>(R.id.tv_cnt)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val description = itemView.findViewById<TextView>(R.id.tv_description)
    }
}