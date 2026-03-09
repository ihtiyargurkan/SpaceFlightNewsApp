package com.example.spaceflightnewsapp.util

import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Formats ISO 8601 date string (e.g. "2026-03-09T13:00:00Z") for display.
 */
object DateUtils {

    private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    private val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    fun formatPublishedDate(isoDate: String): String {
        return try {
            val date = inputFormat.parse(isoDate)
            date?.let { outputFormat.format(it) } ?: isoDate
        } catch (e: Exception) {
            isoDate
        }
    }
}
