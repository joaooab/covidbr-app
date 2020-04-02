package br.com.covidbr.data.contry

data class RecordCountry(
        val count: Int,
        val date: String,
        val result: List<Result>
)