package com.example.spaceflightnewsapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data model representing a spaceflight news article from the Spaceflight News API.
 * @see <a href="https://api.spaceflightnewsapi.net/v4/docs/">Spaceflight News API Documentation</a>
 */
data class Article(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("authors") val authors: List<Author> = emptyList(),
    @SerializedName("url") val url: String,
    @SerializedName("image_url") val imageUrl: String? = null,
    @SerializedName("news_site") val newsSite: String,
    @SerializedName("summary") val summary: String,
    @SerializedName("published_at") val publishedAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("featured") val featured: Boolean = false,
    @SerializedName("launches") val launches: List<Launch> = emptyList(),
    @SerializedName("events") val events: List<Event> = emptyList()
) {
    /** Formatted author names for display. */
    val authorNames: String
        get() = authors.joinToString(", ") { it.name }.ifEmpty { "Unknown" }
}

data class Author(
    @SerializedName("name") val name: String,
    @SerializedName("socials") val socials: Map<String, String>? = null
)

data class Launch(
    @SerializedName("launch_id") val launchId: String,
    @SerializedName("provider") val provider: String
)

data class Event(
    @SerializedName("event_id") val eventId: String,
    @SerializedName("provider") val provider: String
)
