package com.duwna.colormi.repositories

import com.duwna.colormi.base.BaseRepository
import com.duwna.colormi.models.SearchCourseItem
import com.duwna.colormi.models.database.Course
import com.duwna.colormi.models.database.toSearchCourseItem
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.util.*

class SearchRepository : BaseRepository() {

    suspend fun loadCourseItems(): Pair<List<SearchCourseItem>, Boolean> {
//        repeat(40) {
//            database.collection("courses").add(generateCourse())
//        }

        var isFromCache: Boolean

        val courses = database.collection("courses")
            .get()
            .await()
            .apply { isFromCache = metadata.isFromCache }
            .documents
            .map { it.toObject<Course>()?.apply { idCourse = it.id } }

        if (auth.currentUser == null) return courses.map { it!!.toSearchCourseItem() } to isFromCache

        val userRef = database.collection("users")
            .document(firebaseUserId)

        val bookmarkedCourses = userRef.collection("bookmarks")
            .get().await().documents.map { it.id }

        val boughtCourses = userRef.collection("purchases")
            .get().await().documents.map { it.id }

        return courses.map {
            it!!.toSearchCourseItem(
                it.idCourse in boughtCourses,
                it.idCourse in bookmarkedCourses
            )
        } to isFromCache
    }

    suspend fun loadCourse(idCourse: String): Pair<SearchCourseItem, Boolean> {
        var isFromCache = false

        val course = database.collection("courses").document(idCourse)
            .get()
            .await()
            .apply { isFromCache = metadata.isFromCache }
            .toObject<Course>()?.copy(idCourse = idCourse)

        if (auth.currentUser == null) return course!!.toSearchCourseItem() to isFromCache

        val userRef = database.collection("users")
            .document(firebaseUserId)

        val isBookmarked = userRef.collection("bookmarks")
            .document(idCourse).get().await().exists()

        val isBought = userRef.collection("purchases")
            .document(idCourse).get().await().exists()

        return course!!.toSearchCourseItem(isBought, isBookmarked) to isFromCache
    }

    suspend fun addToBookMarks(idCourse: String) {
        database.collection("users")
            .document(firebaseUserId)
            .collection("bookmarks")
            .document(idCourse)
            .set(mapOf("timestamp" to Date()))
            .await()
    }

    suspend fun deleteFromBookmarks(idCourse: String) {
        database.collection("users")
            .document(firebaseUserId)
            .collection("bookmarks")
            .document(idCourse)
            .delete()
            .await()
    }

    suspend fun addToBought(idCourse: String) {
        database.collection("users")
            .document(firebaseUserId)
            .collection("purchases")
            .document(idCourse)
            .set(mapOf("timestamp" to Date()))
            .await()
    }

    suspend fun deleteFromBought(idCourse: String) {
        database.collection("users")
            .document(firebaseUserId)
            .collection("purchases")
            .document(idCourse)
            .delete()
            .await()
    }


    private fun isOnline(): Boolean = try {
        Socket().run {
            connect(InetSocketAddress("8.8.8.8", 53), 1500)
            close()
        }
        true
    } catch (e: IOException) {
        false
    }
}