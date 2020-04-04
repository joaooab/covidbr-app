package br.com.covidbr.data.region

import br.com.covidbr.data.Memory

interface RegionRepository {
    suspend fun getLatest(): Region
}

class RegionRepositoryImpl(val service: RegionService) : RegionRepository {

    override suspend fun getLatest(): Region {
        val latest = service.getLatest()
        val records = latest.deceasedByRegion.mapIndexed { index, deceased ->
            val infected = latest.infectedByRegion[index].count
            val nameState = Memory.getNameState(deceased.state)
            RegionRecord(deceased.count, infected, deceased.state, nameState)
        }.sortedBy { it.state }

        return Region(latest.deceased, latest.infected, records.toMutableList())
    }
}