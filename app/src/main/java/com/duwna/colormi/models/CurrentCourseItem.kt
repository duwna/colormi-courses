package com.duwna.colormi.models

data class CurrentCourseItem(
    var idCourse: String = "",
    val title: String = "",
    val type: String = "",
    val description: String = "",
    var daysLeft: Int = 0,
    val videoUrl: String = "",
    val documentUrl: String = ""
)