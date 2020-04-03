package br.com.covidbr.ui.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.covidbr.data.contry.CountryService
import br.com.covidbr.data.contry.RecordCountry
import br.com.covidbr.data.contry.Result
import kotlinx.coroutines.launch

class CountryViewModel(val service: CountryService) : ViewModel() {

    private val _records: MutableLiveData<RecordCountry> = MutableLiveData()
    val records: LiveData<RecordCountry> = _records
    val error: MutableLiveData<String> = MutableLiveData()

    init {
        viewModelScope.launch {
            try {
                val response = service.getLatest()
                val results = response.result.map { json ->
                    val key = json.keySet().first()
                    val jsonContent = json[key].asJsonObject
                    val confirmed = jsonContent["confirmed"].asString
                    val deaths = jsonContent["deaths"].asString
                    val recovered = jsonContent["recovered"].asString

                    Result(key, confirmed, deaths, recovered)
                }
                _records.value = RecordCountry(response.count, response.date, results)
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }
}