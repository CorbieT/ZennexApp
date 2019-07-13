package ru.zennex.zennexapp.model

data class Quote (val id: Int, val description: String, val time: String, val rating: Int)

data class Quotes(val total: Int, val last: String, val quotes: List<Quote>)