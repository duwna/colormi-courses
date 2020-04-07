package com.duwna.colormi.repositories

import com.duwna.colormi.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object AuthRepository {

    private val auth = FirebaseAuth.getInstance()
    val firebaseUser get() = auth.currentUser

    private val database = Firebase.firestore.apply {
        firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
    }

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
                    user.id = this.firebaseUser?.uid
                    writeNewUserToDB(user)
                } else onError?.invoke(task.exception)
            }
    }

    fun getUserInfo(
        onComplete: ((user: User?) -> Unit)? = null,
        onError: ((error: Exception?) -> Unit)? = null
    ) {
        if (firebaseUser != null) {
            database.collection("users")
                .document(firebaseUser?.uid!!)
                .get(Source.DEFAULT)
                .addOnSuccessListener {
                    val user = it.toObject(User::class.java)
                    onComplete?.invoke(user)
                }.addOnFailureListener {
                    onError?.invoke(it)
                }
        } else onError?.invoke(null)
    }

    fun signOut() = auth.signOut()

    private fun writeNewUserToDB(user: User) {
        database.collection("users").document(user.id!!).set(user)
    }

}