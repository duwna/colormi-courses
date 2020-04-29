package com.duwna.colormi.models

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Bookmark(
    val idUser: String = "",
    val idCourse: String = "",
    @get:Exclude
    var idBookmark: String? = null
)