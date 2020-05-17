package com.duwna.colormi.base

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

open class BaseRepository {

    protected val auth = FirebaseAuth.getInstance()

    protected val firebaseUserId
        get() = auth.currentUser!!.uid

    protected val database = Firebase.firestore.apply {
        firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
    }

    fun checkUser() {
        if (auth.currentUser == null) throw NoAuthException()
    }
}

class NoAuthException : Throwable()
