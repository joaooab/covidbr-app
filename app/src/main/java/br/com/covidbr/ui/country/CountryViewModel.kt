package br.com.covidbr.ui.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.covidbr.data.contry.Contry
import br.com.covidbr.data.contry.ContryRecord
import br.com.covidbr.data.contry.CountryRepository
import br.com.covidbr.extension.unaccent
import br.com.covidbr.ui.filter.Filter
import kotlinx.coroutines.launch
import java.util.*

class CountryViewModel(val repository: CountryRepository) : ViewModel() {

    private val _records: MutableLiveData<Contry> = MutableLiveData()
    val records: LiveData<Contry> = _records
    val onError = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
    private var filter: Filter? = null

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

    fun filter(newText: String): MutableList<ContryRecord> {
        val records = getRecords()
        return if (newText.isEmpty()) {
            records
        } else {
            val query = newText.toUpperCase(Locale.getDefault()).unaccent()
            records.filter {
                it.contryName.toUpperCase(Locale.getDefault()).unaccent().contains(query)
            }.toMutableList()
        }
    }

    private fun order(records: MutableList<ContryRecord>): MutableList<ContryRecord> {
        if (this.filter == null) return records
        if (filter?.type == Filter.TYPE_ASC) {
            when (filter?.order) {
                Filter.ORDER_NAME -> records.sortBy { it.contryName.unaccent() }
                Filter.ORDER_DECEASE -> records.sortBy { it.deaths }
                Filter.ORDER_INFECTED -> records.sortBy { it.confirmed }
                else -> throw IllegalArgumentException("Filter invalid")
            }
        } else {
            when (filter?.order) {
                Filter.ORDER_NAME -> records.sortByDescending { it.contryName.unaccent() }
                Filter.ORDER_DECEASE -> records.sortByDescending { it.deaths }
                Filter.ORDER_INFECTED -> records.sortByDescending { it.confirmed }
                else -> throw IllegalArgumentException("Filter invalid")
            }
        }
        return records
    }

    fun order(filter: Filter): MutableList<ContryRecord> {
        this.filter = filter
        val records = getRecords()
        return order(records)
    }

    private fun getRecords() = _records.value?.records ?: mutableListOf()

}