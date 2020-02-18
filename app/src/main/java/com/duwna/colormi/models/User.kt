package com.duwna.colormi.models

data class User(
    var id: String? = null,
    val email: String = "",
    val phone: String = "",
    val firstName: String = "",
    val lastName: String = ""
)