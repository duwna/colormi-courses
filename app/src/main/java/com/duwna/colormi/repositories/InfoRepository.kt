package com.duwna.colormi.repositories

import com.duwna.colormi.base.BaseRepository
import com.duwna.colormi.models.database.Faq
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class InfoRepository : BaseRepository() {

    suspend fun loadAboutText(): String {

//        database.collection("about")
//            .document("CGhf18bg4qt8oGeOBttv")
//            .update("text", a)
//            .await()

        return database.collection("about")
            .get()
            .await()
            .documents[0]
            .getString("text")!!
    }

    suspend fun loadFaq(): List<Faq> {
        return database.collection("faq")
//            .get(if (fromServer) Source.SERVER else Source.CACHE)
            .get()
            .await()
            .documents
            .map { it.toObject<Faq>()!! }
    }
}