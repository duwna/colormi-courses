package com.duwna.colormi.models

data class CourseItem(
    val id: String = "",
    val title: String = "",
    val type: String = "",
    val description: String = "",
    val price: Int = 0,
    val isBought: Boolean = false,
    val isBookmarked: Boolean = false
)