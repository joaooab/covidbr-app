package br.com.covidbr.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import br.com.covidbr.data.region.RecordRegion
import br.com.covidbr.data.region.RegionService
import br.com.covidbr.worker.RegionWorker
import kotlinx.coroutines.launch

class HomeViewModel(val service: RegionService) : ViewModel() {
    private val _records: MutableLiveData<RecordRegion> = MutableLiveData()
    val records: LiveData<RecordRegion> = _records
    val error: MutableLiveData<String> = MutableLiveData()

    init {
        viewModelScope.launch {
            try {
                _records.value = service.getLatest()
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }

}