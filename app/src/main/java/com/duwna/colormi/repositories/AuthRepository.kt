package com.duwna.colormi.repositories

import com.google.firebase.auth.FirebaseAuth

object AuthRepository {

    private val auth = FirebaseAuth.getInstance()
    val user get() = auth.currentUser

    fun authUser(
        email: String,
        password: String,
        onComplete: (() -> Unit)? = null,
        onError: ((error: Exception?) -> Unit)? = null
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) onComplete?.invoke()
                else onError?.invoke(task.exception)
            }
    }

    fun registerUser(
        name: String,
        email: String,
        password: String,
        onComplete: (() -> Unit)? = null,
        onError: ((error: Exception?) -> Unit)? = null
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) onComplete?.invoke()
                else onError?.invoke(task.exception)
            }
    }

    fun signOut() {
        auth.signOut()
    }
}