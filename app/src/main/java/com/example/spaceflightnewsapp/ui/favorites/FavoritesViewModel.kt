package com.example.spaceflightnewsapp.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.spaceflightnewsapp.SpaceFlightNewsApplication
import com.example.spaceflightnewsapp.data.model.Article

/**
 * ViewModel for the favorites screen.
 */
class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as SpaceFlightNewsApplication).favoritesRepository

    val favorites: LiveData<List<Article>> = repository.getAllFavorites().asLiveData()
}
