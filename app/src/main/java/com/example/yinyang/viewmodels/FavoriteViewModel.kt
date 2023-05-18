package com.example.yinyang.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yinyang.models.Favorite
import com.example.yinyang.models.Product
import com.example.yinyang.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
//class FavoriteViewModel @Inject constructor(
//    favoriteRepository: FavoriteRepository,
//) : ViewModel() {
//    private val _favorite = mutableStateOf(emptyList<Favorite>())
//    val favorite: State<List<Favorite>> = _favorite
//
//    init {
//        viewModelScope.launch {
//            _favorite.value = favoriteRepository.getFavorites()
//        }
//    }
//}