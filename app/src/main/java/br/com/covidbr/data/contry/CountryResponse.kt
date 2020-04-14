package br.com.covidbr.data.contry

import com.google.gson.JsonObject

data class CountryResponse(
        val count: Int,
        val date: String,
        val result: List<JsonObject>
)