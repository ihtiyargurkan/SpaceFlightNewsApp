package com.example.spaceflightnewsapp.ui.articledetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.spaceflightnewsapp.SpaceFlightNewsApplication
import com.example.spaceflightnewsapp.data.model.Article
import com.example.spaceflightnewsapp.data.repository.NewsRepository
import com.example.spaceflightnewsapp.util.ErrorHandler
import kotlinx.coroutines.launch

/**
 * ViewModel for the article detail screen.
 */
class ArticleDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val newsRepository = NewsRepository()
    private val favoritesRepository = (application as SpaceFlightNewsApplication).favoritesRepository

    private val _article = MutableLiveData<Article?>()
    val article: LiveData<Article?> = _article

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun loadArticle(articleId: Int) {
        if (articleId <= 0) {
            _errorMessage.value = "Invalid article ID"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = newsRepository.getArticleById(articleId)

            _isLoading.value = false
            result.fold(
                onSuccess = {
                    _article.value = it
                    _isFavorite.value = favoritesRepository.isFavorite(it.id)
                },
                onFailure = { e ->
                    _errorMessage.value = ErrorHandler.getErrorMessage(e)
                }
            )
        }
    }

    fun toggleFavorite() {
        val currentArticle = _article.value ?: return
        viewModelScope.launch {
            _isFavorite.value = favoritesRepository.toggleFavorite(currentArticle)
        }
    }
}
