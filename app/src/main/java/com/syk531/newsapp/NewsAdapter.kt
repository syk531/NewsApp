package com.syk531.newsapp

import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.syk531.newsapp.api.dto.Company
import com.syk531.newsapp.api.dto.NewsItemDto
import kotlinx.android.synthetic.main.list_item.view.*
import org.bitbucket.eunjeon.seunjeon.Analyzer
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
        holder.cnt.text = "???????????? ${cnt.toString()}???"
        holder.putDate.text = putDate
        holder.title.text = title
        holder.description.text = description

        // ????????? ??????
        // LNode(?????????/WrappedArray(N),0,3,-1135)
        //LNode(???/WrappedArray(J),3,4,-738)
        //LNode(???/WrappedArray(N),4,5,660)
        //LNode(???/WrappedArray(J),5,6,203)
        //LNode(?????????/WrappedArray(V),6,9,583)
        //LNode(??????/WrappedArray(EP, E),9,11,-1256)
        //LNode(./WrappedArray(S),11,12,325)
        for (node in Analyzer.parseJava("?????????????????????????????????.")) {
            System.out.println(node)
        }

        val companyImageId: Int = holder.itemView.context.resources.getIdentifier("company_$companyId", "drawable", "com.syk531.newsapp")
        holder.companyImage.setImageResource(companyImageId)

        //???????????? ?????? ??????
        holder.itemView.bt_cnt.setOnClickListener {
            if(cnt > 0) {
                val intent = Intent(it.context, FakeNewsListActivity::class.java)
                intent.putExtra("companyId", companyId)
                intent.putExtra("companyName", companyName)
                ContextCompat.startActivity(it.context, intent, null)
            }
        }

        //?????? ?????? ??????
        holder.itemView.cl_list2.setOnClickListener {
            val newsItem: NewsItemDto = newsList.get(position)

            val intent = Intent(it.context, NewsDetailActivity::class.java)
            intent.putExtra("originallink", newsItem.originallink)
            ContextCompat.startActivity(it.context, intent, null)
        }
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val companyName = itemView.findViewById<TextView>(R.id.tv_companyName)
        val cnt = itemView.findViewById<Button>(R.id.bt_cnt)
        val putDate = itemView.findViewById<TextView>(R.id.tv_pubDate)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val description = itemView.findViewById<TextView>(R.id.tv_description)
        val companyImage = itemView.findViewById<ImageView>(R.id.iv_companyImage)
    }

    private fun getMostUsedWords(text: String): String {
        //https://bitbucket.org/eunjeon/seunjeon/src/master/
        //https://docs.google.com/spreadsheets/d/1-9blXKjtjeKZqsf4NzHeYJCrr49-nXeRF6D80udfcwY/edit#gid=589544265
        //NNG : ????????????, NNP : ????????????, NP : ?????????
		val countPumsaList: ArrayList<String> = ArrayList(Arrays.asList("NNG", "NNP", "NP"))
        val map: MutableMap<String, Int> = HashMap()

        // ????????? ??????
        for (node in Analyzer.parseJava(text)) {
            val featureHead = node.morpheme().featureHead // NNG
            val surface = node.morpheme().surface // ?????????

            if (!countPumsaList.contains(featureHead)) {
                continue
            }

            if (map.containsKey(surface)) {
                map[surface] = map[surface]!! + 1
            } else {
                map[surface] = 1
            }
        }

        val keySet: List<String> = ArrayList(map.keys)
        //keySet.sortedBy { o1: String, o2: String -> map[o2]!!.compareTo(map[o1]!!) }

        for (key in keySet) {
            print("Key : $key")
            println(", Val : " + map[key])
        }

        return ""
    }
}