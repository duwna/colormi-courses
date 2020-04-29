package com.duwna.colormi.models

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
interface HasId {
    @get:Exclude
    var id: String
}

data class A(
    val name: String,
    override var id: String
) : HasId