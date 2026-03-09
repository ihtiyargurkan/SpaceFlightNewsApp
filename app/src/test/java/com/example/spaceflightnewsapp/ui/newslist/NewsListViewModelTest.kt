package com.example.spaceflightnewsapp.ui.newslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.spaceflightnewsapp.MainDispatcherRule
import com.example.spaceflightnewsapp.data.model.Article
import com.example.spaceflightnewsapp.data.repository.NewsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.Result

class NewsListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: NewsRepository
    private lateinit var viewModel: NewsListViewModel

    private val testArticles = listOf(
        Article(
            id = 1,
            title = "Test Article",
            authors = emptyList(),
            url = "https://example.com",
            imageUrl = null,
            newsSite = "TestSite",
            summary = "Summary",
            publishedAt = "2026-01-01T00:00:00Z",
            updatedAt = "2026-01-01T00:00:00Z"
        )
    )

    @Before
    fun setup() {
        repository = mock()
        whenever(repository.getArticles(any(), any(), any()))
            .thenReturn(Result.success(emptyList()))
        viewModel = NewsListViewModel(repository)
    }

    @Test
    fun search_withBlankQuery_callsLoadArticlesWithNull() = runTest {
        whenever(repository.getArticles(any(), any(), any()))
            .thenReturn(Result.success(emptyList()))

        viewModel.search("   ")
        viewModel.search("")

        assertEquals(emptyList<Article>(), viewModel.articles.value)
    }

    @Test
    fun search_withValidQuery_loadsArticles() = runTest {
        whenever(repository.getArticles(any(), any(), any()))
            .thenReturn(Result.success(testArticles))

        viewModel.search("spacex")

        assertEquals(testArticles, viewModel.articles.value)
    }

    @Test
    fun loadArticles_onSuccess_updatesArticles() = runTest {
        whenever(repository.getArticles(any(), any(), any()))
            .thenReturn(Result.success(testArticles))

        viewModel.loadArticles()

        assertEquals(testArticles, viewModel.articles.value)
        assertEquals(false, viewModel.isLoading.value)
        assertNull(viewModel.errorMessage.value)
    }

    @Test
    fun loadArticles_onFailure_updatesErrorMessage() = runTest {
        val exception = Exception("Network error")
        whenever(repository.getArticles(any(), any(), any()))
            .thenReturn(Result.failure(exception))

        viewModel.loadArticles()

        assertEquals(false, viewModel.isLoading.value)
        assertEquals("Network error", viewModel.errorMessage.value)
    }
}
