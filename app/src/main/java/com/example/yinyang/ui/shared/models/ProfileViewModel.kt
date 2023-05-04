package com.example.yinyang.ui.shared.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yinyang.ui.utils.client
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _userInfo = mutableStateOf<User?>(null)
    val userInfo: MutableState<User?> = _userInfo

    private suspend fun getUserInfo(): User {
        val result = client.postgrest["user"]
            .select(
                single = true,
            ) {
                User::id eq client.gotrue.retrieveUserForCurrentSession().id
            }

        return result.decodeAs()
    }

    init {
        viewModelScope.launch {
            _userInfo.value = getUserInfo()
        }
    }
}