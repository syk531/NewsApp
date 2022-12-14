package com.syk531.newsapp.api.dto

import java.sql.Timestamp
import java.util.*

data class NewsItemDto (
    var title: String,
    var originallink: String,
    var link: String,
    var description: String,
    var pubDate: Date
)
