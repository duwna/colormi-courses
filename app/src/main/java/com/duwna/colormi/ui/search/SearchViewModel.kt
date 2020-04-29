package com.duwna.colormi.ui.search

import androidx.lifecycle.viewModelScope
import com.duwna.colormi.base.BaseViewModel
import com.duwna.colormi.base.IViewModelState
import com.duwna.colormi.base.Notify
import com.duwna.colormi.models.CourseItem
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
                if (result.second) notify(Notify.InternetError())
            } catch (e: Throwable) {
                updateState { it.copy(isLoading = false) }
                notify(Notify.Error())
            }
        }
    }

    fun handleBookmark(courseItem: CourseItem, index: Int) {
        updateState { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!courseItem.isBookmarked) repository.addToBookMarks(courseItem.idCourse)
                else repository.deleteFromBookmarks(courseItem.idCourse)

                val newList = currentState
                    .coursesList
                    .toMutableList()
                    .also { it[index] = courseItem.copy(isBookmarked = !courseItem.isBookmarked) }

                updateState { it.copy(coursesList = newList, isLoading = false) }
                notify(
                    Notify.TextMessage(
                        if (!courseItem.isBookmarked) "Курс добавлен в избранное"
                        else "Курс удален из избранного"
                    )
                )

            } catch (e: Throwable) {
                updateState { it.copy(isLoading = false) }
                notify(Notify.InternetError())
            }
        }
    }
}

data class SearchState(
    val coursesList: List<CourseItem> = emptyList(),
    val isLoading: Boolean = false
) : IViewModelState
