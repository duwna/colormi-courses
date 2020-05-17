package com.duwna.colormi.repositories

import com.duwna.colormi.base.BaseRepository
import com.duwna.colormi.models.database.News
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class NewsRepository : BaseRepository() {

    suspend fun loadNews(): Pair<List<News>, Boolean> {
        var isFromCache: Boolean

        val newsList = database.collection("news")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .get()
            .await()
            .apply { isFromCache = metadata.isFromCache }
            .documents
            .map { it.toObject<News>()!! }

        return newsList to isFromCache
    }
}