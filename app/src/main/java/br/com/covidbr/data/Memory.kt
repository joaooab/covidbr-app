package br.com.covidbr.data

import br.com.covidbr.data.contry.ContryMemory
import br.com.covidbr.data.region.RegionMemory

object Memory {
    val REGION_MEMORIES = mutableListOf<RegionMemory>()
    val CONTRY_MEMORIES: MutableList<ContryMemory> = mutableListOf()

    fun getNameState(sigla: String): String = REGION_MEMORIES.find { it.sigla == sigla }?.nome ?: ""
    fun getNameContry(sigla: String): String = CONTRY_MEMORIES.find { it.sigla3 == sigla }?.nome ?: ""
}