package com.syk531.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.syk531.newsapp.api.dto.NewsItemDto
import org.apache.commons.text.StringEscapeUtils
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(val newsList: MutableList<NewsItemDto>) : RecyclerView.Adapter<NewsAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsAdapter.CustomViewHolder, position: Int) {
        val sf = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val putDate = sf.format(newsList.get(position).pubDate)
        val title = StringEscapeUtils.unescapeHtml4(newsList.get(position).title)
        val description = StringEscapeUtils.unescapeHtml4(newsList.get(position).description)

        holder.putDate.text = putDate
        holder.title.text = title
        holder.description.text = description
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val putDate = itemView.findViewById<TextView>(R.id.tv_pubDate)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val description = itemView.findViewById<TextView>(R.id.tv_description)
    }
}