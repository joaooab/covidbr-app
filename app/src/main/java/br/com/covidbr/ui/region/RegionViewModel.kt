package br.com.covidbr.ui.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.covidbr.data.region.Region
import br.com.covidbr.data.region.RegionRecord
import br.com.covidbr.data.region.RegionRepository
import br.com.covidbr.extension.unaccent
import br.com.covidbr.ui.filter.Filter
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.util.*

class RegionViewModel(val repository: RegionRepository) : ViewModel() {

    private val _records: MutableLiveData<Region> = MutableLiveData()
    private var filter: Filter? = null
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
        val records = getRecords()
        return if (newText.isEmpty()) {
            records
        } else {
            val query = newText.toUpperCase(Locale.getDefault()).unaccent()
            records.filter {
                val stateName = it.stateName.toUpperCase(Locale.getDefault()).unaccent()
                stateName.contains(query)
            }.toMutableList()
        }
    }

    private fun order(records: MutableList<RegionRecord>): MutableList<RegionRecord> {
        if (this.filter == null) return records
        if (filter?.type == Filter.TYPE_ASC) {
            when(filter?.order) {
                Filter.ORDER_NAME -> records.sortBy { it.stateName.unaccent() }
                Filter.ORDER_DECEASE -> records.sortBy { it.deceased }
                Filter.ORDER_INFECTED -> records.sortBy { it.infected }
                else -> throw IllegalArgumentException("Filter invalid")
            }
        } else {
            when(filter?.order) {
                Filter.ORDER_NAME -> records.sortByDescending { it.stateName.unaccent() }
                Filter.ORDER_DECEASE -> records.sortByDescending { it.deceased }
                Filter.ORDER_INFECTED -> records.sortByDescending { it.infected }
                else -> throw IllegalArgumentException("Filter invalid")
            }
        }

        return records
    }

    fun order(filter: Filter): MutableList<RegionRecord> {
        this.filter = filter
        val records = getRecords()
        return order(records)
    }

    private fun getRecords() = _records.value?.records ?: mutableListOf()

}

