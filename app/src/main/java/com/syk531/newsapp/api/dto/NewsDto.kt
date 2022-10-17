package com.syk531.newsapp.api.dto

data class NewsDto (
    var lastBuildDate: String,
    var total: Int,
    var start: Int,
    var display: Int,
    var items: List<NewsItemDto>
)