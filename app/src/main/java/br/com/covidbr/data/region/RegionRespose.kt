package br.com.covidbr.data.region

data class RegionRespose(
    val deceased: Int,
    val deceasedByRegion: List<DeceasedByRegion>,
    val infected: Int,
    val infectedByRegion: List<InfectedByRegion>,
    val lastUpdatedAtApify: String,
    val lastUpdatedAtSource: String,
    val readMe: String,
    val sourceUrl: String,
    val version: Int
)