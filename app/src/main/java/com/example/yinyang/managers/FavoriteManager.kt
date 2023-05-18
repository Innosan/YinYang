package com.example.yinyang.managers

import androidx.compose.runtime.MutableState
import com.example.yinyang.repository.FavoriteRepository
import com.example.yinyang.viewmodels.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FavoriteManager(
    private val viewModelScope: CoroutineScope,
    private val favoriteRepository: FavoriteRepository,
    private val profile: MutableState<ProfileViewModel.Profile>
) {
    fun deleteFavorite(favoriteId: Int) {
        viewModelScope.launch {
            favoriteRepository.deleteFromFavorite(favoriteId)
            updateFavorites()
        }
    }

    fun addFavorite(userId: Int, productId: Int) {
        viewModelScope.launch {
            favoriteRepository.addToFavorite(userId, productId)
            updateFavorites()
        }
    }

    private suspend fun updateFavorites() {
        val updatedFavorites = favoriteRepository.getFavorites(profile.value.userInfo?.value?.id)
        profile.value = profile.value.copy(userFavorite = updatedFavorites)
    }
}