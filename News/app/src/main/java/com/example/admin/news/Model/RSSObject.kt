package com.example.admin.news.Model

data class RSSObject(val status:String, val feed: Feed, val items:List<Item>)