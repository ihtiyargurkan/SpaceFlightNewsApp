package com.example.spaceflightnewsapp.data.repository

import com.example.spaceflightnewsapp.data.model.Article
import com.example.spaceflightnewsapp.data.remote.ApiClient
import com.example.spaceflightnewsapp.data.remote.SpaceFlightNewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Repository for spaceflight news data.
 * Handles API calls and error mapping.
 */
class NewsRepository {

    private val api: SpaceFlightNewsApi = ApiClient.spaceFlightNewsApi

    /**
     * Fetches articles from the API.
     * @return Result containing list of articles or error
     */
    suspend fun getArticles(
        limit: Int = 20,
        offset: Int = 0,
        search: String? = null
    ): Result<List<Article>> = withContext(Dispatchers.IO) {
        try {
            val response = api.getArticles(limit = limit, offset = offset, search = search)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.results)
                } else {
                    Result.failure(IOException("Empty response body"))
                }
            } else {
                Result.failure(
                    IOException("API error: ${response.code()} ${response.message()}")
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Fetches a single article by ID.
     */
    suspend fun getArticleById(id: Int): Result<Article> = withContext(Dispatchers.IO) {
        try {
            val response = api.getArticleById(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(IOException("Empty response body"))
                }
            } else {
                Result.failure(
                    IOException("API error: ${response.code()} ${response.message()}")
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
