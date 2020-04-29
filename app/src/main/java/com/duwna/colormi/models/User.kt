package com.duwna.colormi.models

data class User(
    val email: String = "",
    val phone: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val avatarUrl: String = ""
)

val User.fullName: String
    get() = "$firstName $lastName"

val User.initials: String?
    get() = try {
        "${firstName[0]}${lastName[0]}"
    } catch (e: IndexOutOfBoundsException) {
        null
    }


