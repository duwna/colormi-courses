package com.duwna.colormi.repositories

import android.net.Uri
import android.util.Log
import com.duwna.colormi.base.BaseRepository
import com.duwna.colormi.extensions.tryOrNull
import com.duwna.colormi.models.database.User
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class UserRepository : BaseRepository() {

    private val storage = Firebase.storage.reference

    suspend fun uploadImage(uri: Uri) {
        checkUser()
    }

    suspend fun authUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun registerUser(user: User, password: String) {
        auth.createUserWithEmailAndPassword(user.email, password).await()
        insertUser(user)
    }

    suspend fun updateUser(user: User) {
        checkUser()
        database.collection("users")
            .document(firebaseUserId)
            .update(
                mapOf(
                    "email" to user.email,
                    "phone" to user.phone,
                    "firstName" to user.firstName,
                    "lastName" to user.lastName
                )
            ).await()
    }

    suspend fun getUserInfo(): Pair<User, Boolean> {
        checkUser()
        val result = database.collection("users")
            .document(firebaseUserId)
            .get()
            .await()

        var user = result.toObject<User>()!!
        if (!result.metadata.isFromCache) user = user.copy(avatarUrl = getImageUrl().also {
            Log.e(this::class.java.simpleName, it)
        })

        return user to result.metadata.isFromCache
    }

    fun signOut() = auth.signOut()

    private suspend fun getImageUrl(): String? = tryOrNull {
        checkUser()
        storage.child("avatars/$firebaseUserId")
            .downloadUrl
            .await()
            .toString()
    }

    private suspend fun insertUser(user: User) {
        checkUser()
        database.collection("users")
            .document(firebaseUserId)
            .set(user)
            .await()
    }
}