package com.duwna.colormi.repositories

import com.duwna.colormi.base.BaseRepository
import com.duwna.colormi.models.CourseItem
import com.duwna.colormi.models.database.Course
import com.duwna.colormi.models.database.toCourseItem
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.util.*

class SearchRepository : BaseRepository() {

    suspend fun loadCourseItems(): Pair<List<CourseItem>, Boolean> {
        var isFromCache: Boolean

        val courses = database.collection("courses")
            .get()
            .await()
            .apply { isFromCache = metadata.isFromCache }
            .documents
            .map { it.toObject<Course>()?.apply { idCourse = it.id } }

        val userRef = database.collection("users")
            .document(firebaseUserId)

        val bookmarkedCourses = userRef.collection("bookmarks")
            .get().await().documents.map { it.id }

        val boughtCourses = userRef.collection("purchases")
            .get().await().documents.map { it.id }

        return courses.map {
            it!!.toCourseItem(
                it.idCourse in boughtCourses,
                it.idCourse in bookmarkedCourses
            )
        } to isFromCache
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