package com.syk531.newsapp

import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.syk531.newsapp.api.dto.Company
import com.syk531.newsapp.api.dto.FakeNews
import com.syk531.newsapp.api.dto.NewsItemDto
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.SimpleDateFormat

class FakeNewsAdapter(val fakeNewsList: MutableList<FakeNews>, val context: Context, val companyId: String) : RecyclerView.Adapter<FakeNewsAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FakeNewsAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fake_news_item, parent, false)
        return FakeNewsAdapter.CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return fakeNewsList.size
    }

    override fun onBindViewHolder(holder: FakeNewsAdapter.CustomViewHolder, position: Int) {
        val fakeNews = fakeNewsList.get(position)

        val company = fakeNews.company
        val date = fakeNews.date
        val content = fakeNews.content
        val title = fakeNews.title
        val originalUrl = fakeNews.originalUrl

        holder.fakeNewsCompany.text = company
        holder.fakeNewsContent.text = content
        holder.fakeNewsDate.text = date
        holder.fakeNewsTitle.text = title

        val companyImageId = context.resources.getIdentifier("company_${companyId}", "drawable", "com.syk531.newsapp")
        holder.fakeNewsCompanyImage.setImageResource(companyImageId)

        //본문 영역 클릭
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, NewsDetailActivity::class.java)
            intent.putExtra("originallink", originalUrl)
            ContextCompat.startActivity(it.context, intent, null)
        }
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fakeNewsCompany = itemView.findViewById<TextView>(R.id.tv_fakeNewsCompany)
        val fakeNewsContent = itemView.findViewById<TextView>(R.id.tv_fakeNewsContent)
        val fakeNewsDate = itemView.findViewById<TextView>(R.id.tv_fakeNewsDate)
        val fakeNewsTitle = itemView.findViewById<TextView>(R.id.tv_fakeNewsTitle)
        val fakeNewsCompanyImage = itemView.findViewById<ImageView>(R.id.iv_fakeNewsCompanyImage)

    }
}