package com.example.spaceflightnewsapp.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleTest {

    @Test
    fun authorNames_singleAuthor_returnsAuthorName() {
        val article = Article(
            id = 1,
            title = "Test",
            authors = listOf(Author("John Doe")),
            url = "https://example.com",
            imageUrl = null,
            newsSite = "TestSite",
            summary = "Summary",
            publishedAt = "2026-01-01T00:00:00Z",
            updatedAt = "2026-01-01T00:00:00Z"
        )
        assertEquals("John Doe", article.authorNames)
    }

    @Test
    fun authorNames_multipleAuthors_returnsCommaSeparated() {
        val article = Article(
            id = 1,
            title = "Test",
            authors = listOf(Author("Alice"), Author("Bob")),
            url = "https://example.com",
            imageUrl = null,
            newsSite = "TestSite",
            summary = "Summary",
            publishedAt = "2026-01-01T00:00:00Z",
            updatedAt = "2026-01-01T00:00:00Z"
        )
        assertEquals("Alice, Bob", article.authorNames)
    }

    @Test
    fun authorNames_emptyAuthors_returnsUnknown() {
        val article = Article(
            id = 1,
            title = "Test",
            authors = emptyList(),
            url = "https://example.com",
            imageUrl = null,
            newsSite = "TestSite",
            summary = "Summary",
            publishedAt = "2026-01-01T00:00:00Z",
            updatedAt = "2026-01-01T00:00:00Z"
        )
        assertEquals("Unknown", article.authorNames)
    }
}
