package br.com.covidbr.data.contry

import retrofit2.http.GET

interface CountryService {

    @GET("latest")
    suspend fun getLatest(): CountryResponse
}