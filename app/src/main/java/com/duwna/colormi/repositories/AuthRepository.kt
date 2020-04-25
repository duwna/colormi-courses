package com.duwna.colormi.repositories

import android.util.Log
import com.duwna.colormi.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object AuthRepository {

//    init {
//        Firebase.database.setPersistenceEnabled(true)
//    }

    private val auth = FirebaseAuth.getInstance()
    private val firebaseUser
            get() = auth.currentUser

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
//                    writeNewUserToDbRealtime(user)
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
                    val user = it.toObject<User>()
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


    private val realtimeDatabase = Firebase.database.reference

    fun getUserInfoRealtime(
        onComplete: ((user: User?) -> Unit)? = null,
        onError: ((error: Exception?) -> Unit)? = null
    ) {
        if (firebaseUser != null) {
            realtimeDatabase.child("users")
                .child(firebaseUser?.uid!!)
                .addListenerForSingleValueEvent(
                    object : ValueEventListener {
                        override fun onCancelled(databaseError: DatabaseError) {
                            onError?.invoke(databaseError.toException())
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val user = dataSnapshot.getValue<User>()
                            onComplete?.invoke(user)
                        }
                    }
                )
        }
    }

    private fun writeNewUserToDbRealtime(user: User) {
        realtimeDatabase.child("users").child(user.id!!).setValue(user)
    }

    suspend fun getSuspendUserInfo(fromCash: Boolean): User? {
        if (firebaseUser != null) {
            val result = database.collection("users")
                .document(firebaseUser?.uid!!)
                .get(if (fromCash) Source.CACHE else Source.SERVER)
                .await()

            Log.e("TAG", result.metadata.isFromCache.toString())

            return result.toObject<User>()
        }
        return null
    }

}