package com.example.yinyang.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yinyang.managers.AddressManager
import com.example.yinyang.managers.CartManager
import com.example.yinyang.managers.FavoriteManager
import com.example.yinyang.managers.OrderManager
import com.example.yinyang.models.*
import com.example.yinyang.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor (
    private val userRepository: UserRepository,
    private val addressRepository: AddressRepository,
    private val favoriteRepository: FavoriteRepository,
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    ) : ViewModel() {
    data class Profile(
        val userInfo: MutableState<User?>?,
        val userSession: UserInfo?,
        val userAddresses: MutableState<List<DeliveryAddress>>?,
        val userFavorite: MutableState<List<Favorite>>?,
        val userCart: MutableState<List<CartItem>>?,
        val userOrders: MutableState<List<Order>>?,
    )

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private val _profile = mutableStateOf(
        Profile(
            null,
            null,
            null,
            null,
            null,
            null,
        )
    )
    val profile: MutableState<Profile> = _profile

    val addressManager: AddressManager =
        AddressManager(viewModelScope, addressRepository, profile)

    val favoriteManager: FavoriteManager =
        FavoriteManager(viewModelScope, favoriteRepository, profile)

    val cartManager: CartManager =
        CartManager(viewModelScope, cartRepository, profile)

    val orderManager: OrderManager =
        OrderManager(viewModelScope, orderRepository, cartManager)

    fun updateUserInfo(userId: Int, newName: String, newLastname: String) {
        viewModelScope.launch {
            userRepository.updateUserInfo(userId, newName, newLastname)

            val updatedUserInfo = userRepository.getUserInfo()
            _profile.value = profile.value.copy(userInfo = updatedUserInfo)
        }
    }

    fun getUserId(): Int? {
        return profile.value.userInfo?.value?.id
    }

    fun refresh() {
        viewModelScope.launch {
            try {
                _isRefreshing.emit(true)

                val userInfoDeferred = async { userRepository.getUserInfo() }
                val userSessionDeferred = async { userRepository.getUserSession() }
                val deliveryAddressesDeferred = async {
                    addressRepository.getUserAddresses(userInfoDeferred.await().value?.id)
                }
                val favoriteDeferred = async {
                    favoriteRepository.getFavorites(userInfoDeferred.await().value?.id)
                }
                val cartDeferred = async {
                    cartRepository.getCart(userInfoDeferred.await().value?.id)
                }
                val orderDeferred = async {
                    orderRepository.getOrders(userInfoDeferred.await().value?.id)
                }

                _profile.value = Profile(
                    userInfoDeferred.await(),
                    userSessionDeferred.await(),
                    deliveryAddressesDeferred.await(),
                    favoriteDeferred.await(),
                    cartDeferred.await(),
                    orderDeferred.await(),
                )
            } catch (e: Exception) {
                println(e.message)
            }

            _isRefreshing.emit(false)
        }
    }

    init {
        refresh()
    }
}