package com.example.spaceflightnewsapp.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DateUtilsTest {

    @Test
    fun formatPublishedDate_validIso8601Date_returnsFormattedDate() {
        val isoDate = "2026-03-09T13:00:00Z"
        val result = DateUtils.formatPublishedDate(isoDate)
        assertTrue(result.contains("09"))
        assertTrue(result.contains("2026"))
        assertTrue(result.matches(Regex("\\d{2} \\w{3} \\d{4}")))
    }

    @Test
    fun formatPublishedDate_differentDate_returnsFormattedDate() {
        val isoDate = "2025-12-25T10:30:00Z"
        val result = DateUtils.formatPublishedDate(isoDate)
        assertTrue(result.contains("25"))
        assertTrue(result.contains("2025"))
        assertTrue(result.matches(Regex("\\d{2} \\w{3} \\d{4}")))
    }

    @Test
    fun formatPublishedDate_invalidDate_returnsOriginalString() {
        val invalidDate = "invalid-date"
        val result = DateUtils.formatPublishedDate(invalidDate)
        assertEquals("invalid-date", result)
    }

    @Test
    fun formatPublishedDate_emptyString_returnsEmptyString() {
        val result = DateUtils.formatPublishedDate("")
        assertEquals("", result)
    }
}
