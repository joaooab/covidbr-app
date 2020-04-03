package br.com.covidbr.data.region

import retrofit2.http.GET
import retrofit2.http.Query

interface RegionService {

    @GET("LATEST")
    suspend fun getLatest(@Query("disableRedirect") disableRedirect: Boolean = true): RecordRegion

}