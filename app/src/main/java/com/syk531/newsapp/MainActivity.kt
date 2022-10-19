package com.syk531.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        readExcel()


        var items = mutableListOf<NewsItemDto>()

        val rv_newsList: RecyclerView = findViewById(R.id.rv_newsList)
        rv_newsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_newsList.setHasFixedSize(true)

        thread {
            val response = RetrofitClient.instance.getNews("주식", 10, 1, "date").execute()

            CoroutineScope(Dispatchers.Main).launch {
                if(response.isSuccessful) {
                    if(response.code() == 200) {
                        items = response.body()?.items as MutableList<NewsItemDto>
                        println(123123123)

                        rv_newsList.adapter = NewsAdapter(items)
                    }
                }
            }

        }

        println(2342342343)

    }

    fun readExcel() {
        //파일 읽기
        try {
            val inputStream: InputStream = baseContext.resources.assets.open("한국언론진흥재단_뉴스빅데이터_메타데이터_가짜뉴스_20201231.csv")

            //엑셀파일
            val workbook: Workbook = Workbook.getWorkbook(inputStream)

            //엑셀 파일이 있다면
            if (workbook != null) {
                val sheet = workbook.getSheet(0) //시트 블러오기
                if (sheet != null) {
                    val colTotal = sheet.columns //전체 컬럼
                    val rowIndexStart = 1 //row 인덱스 시작
                    val rowTotal = sheet.getColumn(colTotal - 1).size
                    var sb: StringBuilder
                    for (row in rowIndexStart until rowTotal) {
                        sb = StringBuilder()

                        //col: 컬럼순서, contents: 데이터값
                        for (col in 0 until colTotal) {
                            val contents = sheet.getCell(col, row).contents

                            print("row: " + row + "col: " + col  +"contents"  + contents);
                            if (row > 0) {
                                print("row: " + row + "col: " + col  +"contents"  + contents);
                            }
                        } //내부 For
                    } //바깥 for
                } //if(sheet체크)
            } //if(wb체크)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: BiffException) {
            e.printStackTrace()
        }
    }

}