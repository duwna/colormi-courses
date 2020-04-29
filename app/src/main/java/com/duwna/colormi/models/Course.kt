package com.duwna.colormi.models

data class Course(
    var idCourse: String? = null,
    val title: String = "",
    val type: String = "",
    val description: String = "",
    val price: Int = 0
)

fun Course.toCourseItem(isBought: Boolean, isBookmarked: Boolean): CourseItem = CourseItem(
    idCourse!!,
    title,
    type,
    description,
    price,
    isBought,
    isBookmarked
)
