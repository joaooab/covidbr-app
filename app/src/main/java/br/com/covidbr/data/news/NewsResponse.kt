package br.com.covidbr.data.news

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)