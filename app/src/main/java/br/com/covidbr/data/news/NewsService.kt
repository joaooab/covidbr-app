package br.com.covidbr.data.news

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("sources") sources: String = "google-news-br",
        @Query("apiKey") apiKey: String = "7f441739559540f68649036e2647ca07"
    ): NewsResponse

    @GET("everything")
    suspend fun getEverything(
        @Query("q") q: String,
        @Query("language") language: String = "pt",
        @Query("apiKey") apiKey: String = "7f441739559540f68649036e2647ca07"
    ): NewsResponse
}