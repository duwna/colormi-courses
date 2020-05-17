package com.duwna.colormi.models.database

import java.util.*

data class News(
    val title: String = "",
    val text: String = "",
    val timestamp: Date = Date(),
    val imgUrl: String = ""
)