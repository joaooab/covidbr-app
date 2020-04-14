package br.com.covidbr.data.contry

data class Contry(
        val count: Int,
        val date: String,
        val records: MutableList<ContryRecord>
)