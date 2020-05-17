package com.duwna.colormi.ui.current

import androidx.lifecycle.viewModelScope
import com.duwna.colormi.base.BaseViewModel
import com.duwna.colormi.base.IViewModelState
import com.duwna.colormi.base.NoAuthException
import com.duwna.colormi.base.Notify
import com.duwna.colormi.models.CurrentCourseItem
import com.duwna.colormi.repositories.CurrentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrentViewModel : BaseViewModel<CurrentCoursesState>(CurrentCoursesState()) {

    private val repository = CurrentRepository()

    init {
        loadCourses()
    }

    fun loadCourses() {
        updateState { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val courses = repository.loadCourses()
                updateState { it.copy(coursesList = courses, isLoading = false) }
            } catch (e: NoAuthException) {
                updateState { it.copy(isLoading = false) }
                notify(Notify.NoAuthentication())
            } catch (t: Throwable) {
                notify(Notify.TextMessage("Нет купленных курсов"))
                updateState { it.copy(isLoading = false) }
            }
        }
    }
}

data class CurrentCoursesState(
    val coursesList: List<CurrentCourseItem> = emptyList(),
    val isLoading: Boolean = false
) : IViewModelState