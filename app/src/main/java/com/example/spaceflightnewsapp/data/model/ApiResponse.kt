package com.example.spaceflightnewsapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * API response wrapper for paginated article lists.
 * Spaceflight News API returns: count, next, previous, results
 */
data class ArticlesApiResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String? = null,
    @SerializedName("previous") val previous: String? = null,
    @SerializedName("results") val results: List<Article>
)
