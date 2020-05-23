package com.duwna.colormi.ui.profile.faq

import androidx.lifecycle.viewModelScope
import com.duwna.colormi.base.BaseViewModel
import com.duwna.colormi.base.IViewModelState
import com.duwna.colormi.base.Notify
import com.duwna.colormi.models.database.Faq
import com.duwna.colormi.repositories.InfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FaqViewModel : BaseViewModel<FaqState>(FaqState()) {

    private val repository = InfoRepository()

    init {
        loadFaq()
    }

    fun loadFaq() {
        updateState { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.loadFaq()
                updateState { it.copy(faqList = result, isLoading = false) }
            } catch (t: Throwable) {
                updateState { it.copy(isLoading = false) }
                notify(Notify.Error())
            }
        }
    }

}

data class FaqState(
    val faqList: List<Faq> = emptyList(),
    val isLoading: Boolean = false
) : IViewModelState
