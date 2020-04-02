package com.duwna.colormi.models

data class User(
    var id: String? = null,
    val email: String = "",
    val phone: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val avatarUrl: String = ""
)

val User.fullName: String
    get() = "$firstName $lastName"

val User.initials: String
    get() = "${firstName[0]}${lastName[0]}"
