package com.duwna.colormi.ui.search.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.duwna.colormi.base.BaseViewModel
import com.duwna.colormi.base.IViewModelState
import com.duwna.colormi.base.Notify
import com.duwna.colormi.models.SearchCourseItem
import com.duwna.colormi.repositories.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CourseDetailViewModel(val idCourse: String) :
    BaseViewModel<CourseDetailState>(CourseDetailState()) {

    private val repository = SearchRepository()

    init {
        loadCourse()
    }

    fun loadCourse() {
        updateState { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.loadCourse(idCourse)
                updateState { it.copy(courseItem = result.first, isLoading = false) }
                if (result.second) notify(Notify.InternetError())
            } catch (e: Throwable) {
                updateState { it.copy(isLoading = false) }
                notify(Notify.Error())
                e.printStackTrace()
            }
        }
    }

    fun handleBookmark() {
        updateState { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val isBookmarked = currentState.courseItem?.isBookmarked ?: false

                if (!isBookmarked) repository.addToBookMarks(idCourse)
                else repository.deleteFromBookmarks(idCourse)

                updateState {
                    it.copy(
                        courseItem = currentState.courseItem
                            ?.copy(isBookmarked = !isBookmarked),
                        isLoading = false
                    )
                }

                notify(
                    Notify.TextMessage(
                        if (!isBookmarked) "Курс добавлен в избранное"
                        else "Курс удален из избранного"
                    )
                )

            } catch (e: Throwable) {
                updateState { it.copy(isLoading = false) }
                notify(Notify.InternetError())
                e.printStackTrace()
            }
        }
    }

    fun handlePurchase() {
        updateState { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val isBought = currentState.courseItem?.isBought ?: false

                if (!isBought) repository.addToBought(idCourse)
                else repository.deleteFromBought(idCourse)

                updateState {
                    it.copy(
                        courseItem = currentState.courseItem
                            ?.copy(isBought = !isBought),
                        isLoading = false
                    )
                }

                notify(
                    Notify.TextMessage(
                        if (!isBought) "Курс куплен"
                        else "Курс удален из покупок"
                    )
                )

            } catch (e: Throwable) {
                updateState { it.copy(isLoading = false) }
                notify(Notify.InternetError())
                e.printStackTrace()
            }
        }
    }
}

data class CourseDetailState(
    val courseItem: SearchCourseItem? = null,
    val isLoading: Boolean = false
) : IViewModelState

class CourseDetailViewModelFactory(val idCourse: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CourseDetailViewModel(idCourse) as T
    }
}