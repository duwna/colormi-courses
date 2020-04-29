package com.duwna.colormi.models

import java.util.*

data class Purchase(
    var idPurchase: String? = null,
    val idUser: String = "",
    val idCourse: String = "",
    val purchaseDate: Date = Date()
)