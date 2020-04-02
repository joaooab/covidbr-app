package br.com.covidbr.ui.gallery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.covidbr.data.contry.CountryService
import br.com.covidbr.data.contry.RecordCountryResponse
import kotlinx.coroutines.launch

class GalleryViewModel(val service: CountryService) : ViewModel() {

    private val _records: MutableLiveData<RecordCountryResponse> = MutableLiveData()
    val records: LiveData<RecordCountryResponse> = _records
    val error: MutableLiveData<String> = MutableLiveData()

    init {
        viewModelScope.launch {
            try {
                _records.value = service.getLatest()
                Log.i("", "a");
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }
}