package com.example.spaceflightnewsapp.data.repository

import com.example.spaceflightnewsapp.data.local.AppDatabase
import com.example.spaceflightnewsapp.data.local.entity.FavoriteArticle
import com.example.spaceflightnewsapp.data.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Repository for managing favorite articles in local storage.
 */
class FavoritesRepository(
    private val database: AppDatabase
) {

    private val favoriteDao = database.favoriteDao()

    fun getAllFavorites(): Flow<List<Article>> {
        return favoriteDao.getAllFavorites().map { favorites ->
            favorites.map { it.toArticle() }
        }
    }

    suspend fun isFavorite(articleId: Int): Boolean = withContext(Dispatchers.IO) {
        favoriteDao.isFavorite(articleId)
    }

    suspend fun addFavorite(article: Article) = withContext(Dispatchers.IO) {
        favoriteDao.insert(article.toFavoriteArticle())
    }

    suspend fun removeFavorite(articleId: Int) = withContext(Dispatchers.IO) {
        favoriteDao.deleteByArticleId(articleId)
    }

    suspend fun toggleFavorite(article: Article): Boolean = withContext(Dispatchers.IO) {
        val isFav = favoriteDao.isFavorite(article.id)
        if (isFav) {
            favoriteDao.deleteByArticleId(article.id)
            false
        } else {
            favoriteDao.insert(article.toFavoriteArticle())
            true
        }
    }

    private fun FavoriteArticle.toArticle(): Article = Article(
        id = articleId,
        title = title,
        authors = listOf(com.example.spaceflightnewsapp.data.model.Author(name = authorNames)),
        url = url,
        imageUrl = imageUrl,
        newsSite = newsSite,
        summary = summary,
        publishedAt = publishedAt,
        updatedAt = publishedAt,
        featured = false,
        launches = emptyList(),
        events = emptyList()
    )

    private fun Article.toFavoriteArticle(): FavoriteArticle = FavoriteArticle(
        articleId = id,
        title = title,
        url = url,
        imageUrl = imageUrl,
        newsSite = newsSite,
        summary = summary,
        publishedAt = publishedAt,
        authorNames = authorNames
    )
}
