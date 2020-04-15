package br.com.covidbr.data.dashboard

import retrofit2.http.GET
import retrofit2.http.Query

interface DashboardService {

    @GET("items")
    suspend fun getItens(@Query("format") format: String = "json", @Query("clean") clean: String = "1"): MutableList<Dashboard>

}
