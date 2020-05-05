package com.duwna.colormi.models.database

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val email: String = "",
    val phone: String = "",
    val firstName: String = "",
    val lastName: String = "",
    @get:Exclude
    val avatarUrl: String? = null
)

val User.fullName: String
    get() = "$firstName $lastName"

val User.initials: String?
    get() = try {
        "${firstName[0]}${lastName[0]}"
    } catch (e: IndexOutOfBoundsException) {
        null
    }


