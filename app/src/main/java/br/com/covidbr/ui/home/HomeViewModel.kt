package br.com.covidbr.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.covidbr.data.region.Region
import br.com.covidbr.data.region.RegionRecord
import br.com.covidbr.data.region.RegionRepository
import br.com.covidbr.extension.unaccent
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel(val repository: RegionRepository) : ViewModel() {

    private val _records: MutableLiveData<Region> = MutableLiveData()
    val records: LiveData<Region> = _records
    val onError = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            isLoading.value = true
            try {
                _records.value = repository.getLatest()
            } catch (e: Exception) {
                onError.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }

    fun filter(newText: String): MutableList<RegionRecord> {
        val records = _records.value?.records ?: mutableListOf()
        return if (newText.isEmpty()) {
            records
        } else {
            val query = newText.toUpperCase(Locale.getDefault()).unaccent()
            records.filter {
                it.stateName.toUpperCase(Locale.getDefault()).contains(query)
            }.toMutableList()
        }
    }

}

