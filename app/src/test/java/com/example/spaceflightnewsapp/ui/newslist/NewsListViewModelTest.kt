package com.example.spaceflightnewsapp.ui.newslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.spaceflightnewsapp.MainDispatcherRule
import com.example.spaceflightnewsapp.data.model.Article
import com.example.spaceflightnewsapp.data.repository.NewsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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
        repository = mockk(relaxed = true)
        coEvery { repository.getArticles(any(), any(), any()) } returns Result.success(emptyList())
        viewModel = NewsListViewModel(repository)
    }

    @Test
    fun search_withBlankQuery_callsLoadArticlesWithNull() = runTest {
        coEvery { repository.getArticles(any(), any(), any()) } returns Result.success(emptyList())

        viewModel.search("   ")
        advanceUntilIdle()

        assertEquals(emptyList<Article>(), viewModel.articles.value)
    }

    @Test
    fun search_withValidQuery_loadsArticles() = runTest {
        coEvery { repository.getArticles(any(), any(), any()) } returns Result.success(testArticles)

        viewModel.search("spacex")
        advanceUntilIdle()

        assertEquals(testArticles, viewModel.articles.value)
    }

    @Test
    fun loadArticles_onSuccess_updatesArticles() = runTest {
        coEvery { repository.getArticles(any(), any(), any()) } returns Result.success(testArticles)

        viewModel.loadArticles()
        advanceUntilIdle()

        assertEquals(testArticles, viewModel.articles.value)
        assertEquals(false, viewModel.isLoading.value)
        assertNull(viewModel.errorMessage.value)
    }

    @Test
    fun loadArticles_onFailure_updatesErrorMessage() = runTest {
        val exception = Exception("Network error")
        coEvery { repository.getArticles(any(), any(), any()) } returns Result.failure(exception)

        viewModel.loadArticles()
        advanceUntilIdle()

        assertEquals(false, viewModel.isLoading.value)
        assertEquals("Network error", viewModel.errorMessage.value)
    }
}
