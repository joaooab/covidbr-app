package br.com.covidbr.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.covidbr.data.dashboard.Dashboard
import br.com.covidbr.data.dashboard.DashboardRepository
import br.com.covidbr.data.region.Region
import br.com.covidbr.data.region.RegionRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    val regionRepository: RegionRepository,
    val dashboardRepository: DashboardRepository
) : ViewModel() {

    private val _records: MutableLiveData<Region> = MutableLiveData()
    val records: LiveData<Region> = _records
    private val _dashboard: MutableLiveData<MutableList<Dashboard>> = MutableLiveData()
    val dashboard: LiveData<MutableList<Dashboard>> = _dashboard
    val onError = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            isLoading.value = true
            try {
                _records.value = regionRepository.getLatest()
                _dashboard.value = dashboardRepository.getItens()
            } catch (e: Exception) {
                onError.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }

}

