package com.example.spaceflightnewsapp.data.remote

import com.example.spaceflightnewsapp.data.model.Article
import com.example.spaceflightnewsapp.data.model.ArticlesApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API interface for the Spaceflight News API.
 * Base URL: https://api.spaceflightnewsapi.net/v4/
 */
interface SpaceFlightNewsApi {

    /**
     * Fetches a paginated list of articles.
     * @param limit Number of articles per page (default: 10)
     * @param offset Pagination offset
     * @param search Optional search query for filtering by keyword
     */
    @GET("articles/")
    suspend fun getArticles(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("search") search: String? = null
    ): Response<ArticlesApiResponse>

    /**
     * Fetches a single article by ID.
     */
    @GET("articles/{id}/")
    suspend fun getArticleById(
        @Path("id") id: Int
    ): Response<Article>
}
