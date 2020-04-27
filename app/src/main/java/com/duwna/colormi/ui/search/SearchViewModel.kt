package com.duwna.colormi.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.duwna.colormi.models.CourseItem
import com.duwna.colormi.repositories.generateCourseItem

class SearchViewModel : ViewModel() {

    val list = MutableLiveData<List<CourseItem>>().apply {
        value = MutableList((10..50).random()) { generateCourseItem() }
    }

}

