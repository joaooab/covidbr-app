package br.com.covidbr.data.region

import retrofit2.http.GET
import retrofit2.http.Query

interface RegionService {

//    https://api.apify.com/v2/key-value-stores/TyToNta7jGKkpszMZ/records/LATEST?disableRedirect=true

    @GET("LATEST")
    suspend fun getLatest(@Query("disableRedirect") disableRedirect: Boolean = true): RecordRegion
}