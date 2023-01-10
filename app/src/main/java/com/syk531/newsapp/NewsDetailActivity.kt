package com.syk531.newsapp

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class NewsDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_detail)

        val webView = findViewById<WebView>(R.id.webView)
        var webSettings = webView.settings
        webView.settings.domStorageEnabled = true
        webView.settings.javaScriptEnabled = true //자바 스크립트 허용
        /* 웹뷰에서 새 창이 뜨지 않도록 방지 */
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()

        val originallink = intent.getStringExtra("originallink")!!
        webView.loadUrl(originallink)
    }

    override fun onBackPressed() { //백버튼 누를 시 수행할 로직 구현
        val webView = findViewById<WebView>(R.id.webView)
        if(webView.canGoBack()) { //웹사이트에서 뒤로 갈 페이지 존재
            webView.goBack() //웹사이트 뒤로가기
        } else {
            super.onBackPressed()
        }
    }
}