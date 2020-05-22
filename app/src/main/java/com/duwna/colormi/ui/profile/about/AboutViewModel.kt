package com.duwna.colormi.ui.profile.about

import androidx.lifecycle.viewModelScope
import com.duwna.colormi.base.BaseViewModel
import com.duwna.colormi.base.IViewModelState
import com.duwna.colormi.base.Notify
import com.duwna.colormi.repositories.InfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AboutViewModel : BaseViewModel<AboutState>(AboutState()) {

    init {
        loadAboutText()
    }

    private val repository = InfoRepository()

    fun loadAboutText() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(1000)
                val text = repository.loadAboutText()
                updateState { it.copy(text = text, isLoading = false) }
            } catch (e: Throwable) {
                e.printStackTrace()
                updateState { it.copy(isLoading = false) }
                notify(Notify.Error())
            }
        }
    }
}

data class AboutState(
    val isLoading: Boolean = true,
    val text: String? = null
) : IViewModelState