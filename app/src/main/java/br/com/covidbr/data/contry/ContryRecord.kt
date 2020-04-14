package br.com.covidbr.data.contry

data class ContryRecord(
        val contry: String,
        val contryName: String,
        val confirmed: Int,
        val deaths: Int,
        val recovered: Int
)