package com.duwna.colormi.repositories

import com.duwna.colormi.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object AuthRepository {

    private val auth = FirebaseAuth.getInstance()
    val user get() = auth.currentUser

    private val database = FirebaseDatabase.getInstance().reference

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
        user: User,
        password: String,
        onComplete: (() -> Unit)? = null,
        onError: ((error: Exception?) -> Unit)? = null
    ) {
        auth.createUserWithEmailAndPassword(user.email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete?.invoke()
                    user.id = this.user?.uid
                    writeNewUserToDB(user)
                }
                else onError?.invoke(task.exception)
            }
    }

    fun signOut() {
        auth.signOut()
    }

    private fun writeNewUserToDB(user: User) {
        database.child("User").push().setValue(user)
    }
}