package com.example.spaceflightnewsapp.ui.newslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceflightnewsapp.data.model.Article
import com.example.spaceflightnewsapp.data.repository.NewsRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for the news list screen.
 */
class NewsListViewModel(
    private val repository: NewsRepository = NewsRepository()
) : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private var currentSearch: String? = null
    private var currentOffset = 0
    private val pageSize = 20

    init {
        loadArticles()
    }

    fun loadArticles(search: String? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = repository.getArticles(
                limit = pageSize,
                offset = 0,
                search = search
            )

            _isLoading.value = false
            result.fold(
                onSuccess = { list ->
                    _articles.value = list
                    currentSearch = search
                    currentOffset = list.size
                },
                onFailure = { e ->
                    _errorMessage.value = e.message ?: "Unknown error"
                }
            )
        }
    }

    fun refresh() {
        loadArticles(currentSearch)
    }

    /**
     * Searches articles by keyword. Pass null or blank to load all articles.
     */
    fun search(query: String?) {
        val searchQuery = query?.trim()?.takeIf { it.isNotBlank() }
        loadArticles(searchQuery)
    }
}
