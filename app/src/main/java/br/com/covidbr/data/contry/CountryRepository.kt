package br.com.covidbr.data.contry

import br.com.covidbr.data.Memory

interface CountryRepository {
    suspend fun getLatest(): Contry
}

class CountryRepositoryImpl(val service: CountryService) : CountryRepository {
    override suspend fun getLatest(): Contry {
        val response = service.getLatest()
        val results = response.result.map { json ->
            val key = json.keySet().first()
            val contry = Memory.getNameContry(key)
            val jsonContent = json[key].asJsonObject
            val confirmed = jsonContent["confirmed"].asInt
            val deaths = jsonContent["deaths"].asInt
            val recovered = jsonContent["recovered"].asInt

            ContryRecord(key, contry, confirmed, deaths, recovered)
        }.sortedBy { it.contryName }

        return Contry(response.count, response.date, results.toMutableList())
    }
}