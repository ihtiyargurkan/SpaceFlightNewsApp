package com.example.spaceflightnewsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spaceflightnewsapp.data.local.entity.FavoriteArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite_articles ORDER BY publishedAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteArticle>>

    @Query("SELECT * FROM favorite_articles WHERE articleId = :articleId LIMIT 1")
    suspend fun getFavoriteById(articleId: Int): FavoriteArticle?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteArticle)

    @Query("DELETE FROM favorite_articles WHERE articleId = :articleId")
    suspend fun deleteByArticleId(articleId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_articles WHERE articleId = :articleId)")
    suspend fun isFavorite(articleId: Int): Boolean
}
