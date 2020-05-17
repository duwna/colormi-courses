package com.duwna.colormi.models.database

import com.duwna.colormi.models.CurrentCourseItem
import com.duwna.colormi.models.SearchCourseItem
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Course(
    @get:Exclude
    var idCourse: String? = null,
    val title: String = "",
    val type: String = "",
    val description: String = "",
    val price: Int = 0,
    val imgUrl: String = "",
    val rating: Float = 0f,
    val videoUrl: String = "",
    val documentUrl: String = ""
)

fun Course.toSearchCourseItem(isBought: Boolean = false, isBookmarked: Boolean = false): SearchCourseItem =
    SearchCourseItem(
        idCourse!!,
        title,
        type,
        description,
        price,
        isBought,
        isBookmarked,
        imgUrl,
        rating.toString().substring(0, 3)
    )

fun Course.toCurrentCourseItem(daysLeft: Int) = CurrentCourseItem (
    idCourse!!,
    title,
    type,
    description,
    daysLeft,
    videoUrl,
    documentUrl
)

