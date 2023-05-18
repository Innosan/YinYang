package com.example.yinyang.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yinyang.managers.AddressManager
import com.example.yinyang.managers.FavoriteManager
import com.example.yinyang.models.DeliveryAddress
import com.example.yinyang.models.Favorite
import com.example.yinyang.models.User
import com.example.yinyang.repository.AddressRepository
import com.example.yinyang.repository.FavoriteRepository
import com.example.yinyang.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor (
    private val userRepository: UserRepository,
    private val addressRepository: AddressRepository,
    private val favoriteRepository: FavoriteRepository
    ) : ViewModel() {
    data class Profile(
        val userInfo: MutableState<User?>?,
        val userSession: UserInfo?,
        val userAddresses: MutableState<List<DeliveryAddress>>?,
        val userFavorite: MutableState<List<Favorite>>?,
    )

    private val _profile = mutableStateOf(Profile(null, null, null, null))
    val profile: MutableState<Profile> = _profile

    val addressManager: AddressManager =
        AddressManager(viewModelScope, addressRepository, profile)

    val favoriteManager: FavoriteManager =
        FavoriteManager(viewModelScope, favoriteRepository, profile)

    fun updateUserInfo(userId: Int, newName: String, newLastname: String) {
        viewModelScope.launch {
            userRepository.updateUserInfo(userId, newName, newLastname)

            val updatedUserInfo = userRepository.getUserInfo()
            _profile.value = profile.value.copy(userInfo = updatedUserInfo)
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            try {
                val userInfoDeferred = async { userRepository.getUserInfo() }
                val userSessionDeferred = async { userRepository.getUserSession() }
                val deliveryAddressesDeferred = async {
                    addressRepository.getUserAddresses(userInfoDeferred.await().value?.id)
                }
                val favoriteDeferred = async {
                    favoriteRepository.getFavorites(userInfoDeferred.await().value?.id)
                }

                _profile.value = Profile(
                    userInfoDeferred.await(),
                    userSessionDeferred.await(),
                    deliveryAddressesDeferred.await(),
                    favoriteDeferred.await()
                )
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    init {
        loadProfile()
    }
}