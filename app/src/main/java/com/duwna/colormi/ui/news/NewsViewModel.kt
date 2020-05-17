package com.duwna.colormi.ui.news

import androidx.lifecycle.viewModelScope
import com.duwna.colormi.base.BaseViewModel
import com.duwna.colormi.base.IViewModelState
import com.duwna.colormi.base.Notify
import com.duwna.colormi.models.database.News
import com.duwna.colormi.repositories.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel : BaseViewModel<NewsState>(NewsState()) {

    private val repository = NewsRepository()

    init {
        loadNews()
    }

    fun loadNews() {
        updateState { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.loadNews()
                updateState { it.copy(newsList = result.first, isLoading = false) }
                if (result.second) notify(Notify.NoInternetConnection())
            } catch (t: Throwable) {
                updateState { it.copy(isLoading = false) }
                notify(Notify.Error())
            }
        }
    }

}

data class NewsState(
    val newsList: List<News> = emptyList(),
    val isLoading: Boolean = false
) : IViewModelState
