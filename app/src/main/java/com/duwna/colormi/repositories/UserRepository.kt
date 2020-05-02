package com.duwna.colormi.repositories

import com.duwna.colormi.base.BaseRepository
import com.duwna.colormi.models.database.User
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class UserRepository : BaseRepository() {

    suspend fun authUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun registerUser(user: User, password: String) {
        auth.createUserWithEmailAndPassword(user.email, password).await()
        insertUser(user)
    }

    private suspend fun insertUser(user: User) {
        database.collection("users")
            .document(firebaseUserId)
            .set(user)
            .await()
    }

    suspend fun updateUser(user: User) {
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
        val result = database.collection("users")
            .document(firebaseUserId)
            .get()
            .await()

        return result.toObject<User>()!! to result.metadata.isFromCache
    }

    fun signOut() = auth.signOut()
}