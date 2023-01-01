package com.syk531.newsapp.api.dto

data class Company (
    var id: String,
    var name: String,
    var cnt: Int,
    var urlTextList: MutableList<String>
)