package br.com.covidbr.data.region

data class RegionRecord(
    val deceased: Int,
    val infected: Int,
    val state: String,
    val stateName: String
)