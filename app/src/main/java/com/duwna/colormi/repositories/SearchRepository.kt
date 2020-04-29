package com.duwna.colormi.repositories

import com.duwna.colormi.base.BaseRepository
import com.duwna.colormi.models.Bookmark
import com.duwna.colormi.models.Course
import com.duwna.colormi.models.CourseItem
import com.duwna.colormi.models.toCourseItem
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class SearchRepository : BaseRepository() {

    suspend fun loadCourseItems(): Pair<List<CourseItem>, Boolean> {

        var isFromCache: Boolean

        val courses = database.collection("courses")
            .get()
            .await()
            .apply { isFromCache = metadata.isFromCache }
            .documents
            .map { it.toObject<Course>()?.apply { idCourse = it.id } }

        val boughtCourses = database.collection("purchases")
            .whereEqualTo("idUser", firebaseUser!!.uid)
            .get()
            .await()
            .documents
            .map { it.id }

        val bookmarkCourses = database.collection("bookmarks")
            .whereEqualTo("idUser", firebaseUser!!.uid)
            .get()
            .await()
            .documents
            .map { it.toObject<Bookmark>()?.idCourse }

        return courses.map {
            it!!.toCourseItem(
                it.idCourse in boughtCourses,
                it.idCourse in bookmarkCourses
            )
        } to isFromCache
    }

    suspend fun addToBookMarks(idCourse: String) {
        database.collection("bookmarks")
            .add(Bookmark(firebaseUser!!.uid, idCourse))
            .await()
    }

    suspend fun deleteFromBookmarks(idCourse: String) {

        val id = database.collection("bookmarks")
            .whereEqualTo("idUser", firebaseUser!!.uid)
            .whereEqualTo("idCourse", idCourse)
            .get(Source.SERVER)
            .await()
            .documents[0]
            .id

        database.collection("bookmarks")
            .document(id)
            .delete()
            .await()
    }
}