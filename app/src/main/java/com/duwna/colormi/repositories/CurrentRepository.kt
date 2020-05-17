package com.duwna.colormi.repositories

import com.duwna.colormi.base.BaseRepository
import com.duwna.colormi.extensions.getDayDifference
import com.duwna.colormi.models.CurrentCourseItem
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import java.util.*

class CurrentRepository : BaseRepository() {

    suspend fun loadCourses(): List<CurrentCourseItem> {
        val boughtCourses = database.collection("users")
            .document(firebaseUserId)
            .collection("purchases")
            .get()
            .await()
            .documents
            .map { it.id to (it.data!!["timestamp"] as Timestamp).toDate() }

        return database.collection("courses")
            .whereIn(FieldPath.documentId(), boughtCourses.map { it.first })
            .get()
            .await()
            .documents
            .map {
                it.toObject<CurrentCourseItem>()!!.apply {
                    idCourse = it.id
                    val purchaseDate =
                        boughtCourses.find { course -> course.first == idCourse }!!.second
                    daysLeft = COURSE_ACCESS_DAYS - getDayDifference(purchaseDate, Date())
                }
            }
    }

    companion object {
        const val COURSE_ACCESS_DAYS = 60
    }
}
