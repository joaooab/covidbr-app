package br.com.covidbr.data.region

object regionMemory {

    val regions = mutableListOf<Region>()

    fun getNameState(state: String): String = regions.find { it.sigla == state }?.nome ?: ""
}