package com.example.spaceflightnewsapp

import android.app.Application
import com.example.spaceflightnewsapp.data.local.AppDatabase
import com.example.spaceflightnewsapp.data.repository.FavoritesRepository

class SpaceFlightNewsApplication : Application() {

    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }
    val favoritesRepository: FavoritesRepository by lazy {
        FavoritesRepository(database)
    }
}
