package br.com.covidbr.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.covidbr.data.news.NewsResponse
import br.com.covidbr.data.news.NewsService
import kotlinx.coroutines.launch

class NewsViewModel(private val service: NewsService) : ViewModel() {

    private val _newsResponse = MutableLiveData<NewsResponse>()
    val newsResponse: LiveData<NewsResponse> = _newsResponse

    init {
        getTopHeadlines()
    }

    private fun getTopHeadlines() {
        viewModelScope.launch {
            _newsResponse.value = service.getEverything("Convid-19")
        }
    }
}