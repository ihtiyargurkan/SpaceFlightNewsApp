package com.example.spaceflightnewsapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for storing favorited articles locally.
 */
@Entity(tableName = "favorite_articles")
data class FavoriteArticle(
    @PrimaryKey val articleId: Int,
    val title: String,
    val url: String,
    val imageUrl: String?,
    val newsSite: String,
    val summary: String,
    val publishedAt: String,
    val authorNames: String
)
