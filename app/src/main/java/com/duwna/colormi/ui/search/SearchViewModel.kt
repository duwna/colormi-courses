package com.duwna.colormi.ui.search

import androidx.lifecycle.viewModelScope
import com.duwna.colormi.base.BaseViewModel
import com.duwna.colormi.base.IViewModelState
import com.duwna.colormi.base.NoAuthException
import com.duwna.colormi.base.Notify
import com.duwna.colormi.models.SearchCourseItem
import com.duwna.colormi.repositories.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel<SearchState>(SearchState()) {

    private val repository = SearchRepository()

    init {
        loadCourses()
    }

    fun loadCourses() {
        updateState { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.loadCourseItems()
                updateState { it.copy(coursesList = result.first, isLoading = false) }
                if (result.second) notify(Notify.NoInternetConnection())
            } catch (t: Throwable) {
                updateState { it.copy(isLoading = false) }
                notify(Notify.Error())
            }
        }
    }

    fun handleBookmark(courseItem: SearchCourseItem, index: Int) {
        updateState { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!courseItem.isBookmarked) repository.addToBookMarks(courseItem.idCourse)
                else repository.deleteFromBookmarks(courseItem.idCourse)

                val newList = currentState.coursesList.toMutableList()
                    .also { it[index] = courseItem.copy(isBookmarked = !courseItem.isBookmarked) }

                updateState { it.copy(coursesList = newList, isLoading = false) }
                notify(
                    Notify.TextMessage(
                        if (!courseItem.isBookmarked) "Курс добавлен в избранное"
                        else "Курс удален из избранного"
                    )
                )
            } catch (e: NoAuthException) {
                updateState { it.copy(isLoading = false) }
                notify(Notify.NoAuthentication())
            } catch (t: Throwable) {
                notify(Notify.NoInternetConnection())
                updateState { it.copy(isLoading = false) }
            }
        }
    }

    fun handleOnlyBookmarked() {
        updateState { it.copy(isOnlyBookmarked = !currentState.isOnlyBookmarked) }
    }
}

data class SearchState(
    val coursesList: List<SearchCourseItem> = emptyList(),
    val isLoading: Boolean = false,
    val isOnlyBookmarked: Boolean = false,
    val searchQuery: String = ""
) : IViewModelState {

    fun showCurses(): List<SearchCourseItem> = coursesList.filter { needToShow(it) }

    private fun needToShow(courseItem: SearchCourseItem): Boolean {
        if (isOnlyBookmarked && !courseItem.isBookmarked) return false
        if (searchQuery.isNotBlank() && !courseItem.title.contains(searchQuery)) return false
        return true
    }
}