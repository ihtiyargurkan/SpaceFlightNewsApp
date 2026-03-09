package com.example.spaceflightnewsapp.ui.articledetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceflightnewsapp.data.model.Article
import com.example.spaceflightnewsapp.data.repository.NewsRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for the article detail screen.
 */
class ArticleDetailViewModel(
    private val repository: NewsRepository = NewsRepository()
) : ViewModel() {

    private val _article = MutableLiveData<Article?>()
    val article: LiveData<Article?> = _article

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

            val result = repository.getArticleById(articleId)

            _isLoading.value = false
            result.fold(
                onSuccess = { _article.value = it },
                onFailure = { e ->
                    _errorMessage.value = e.message ?: "Unknown error"
                }
            )
        }
    }
}
