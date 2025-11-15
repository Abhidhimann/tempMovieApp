package com.abhishek.tempmovieapp.data.local.prefs

import android.content.SharedPreferences
import javax.inject.Inject
import androidx.core.content.edit


// just to save current page while calling refresh, so user won't fetch previous page that is
// already on DB and to manage page limit reach
class MoviePrefs @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val KEY_CURRENT_PAGE = "current_page"
        private const val KEY_TOTAL_PAGES = "total_pages"
    }

    var currentPage: Int
        get() = sharedPreferences.getInt(KEY_CURRENT_PAGE, 1)
        set(value) = sharedPreferences.edit { putInt(KEY_CURRENT_PAGE, value) }

    var totalPages: Int
        get() = sharedPreferences.getInt(KEY_TOTAL_PAGES, Int.MAX_VALUE)
        set(value) = sharedPreferences.edit { putInt(KEY_TOTAL_PAGES, value) }
}