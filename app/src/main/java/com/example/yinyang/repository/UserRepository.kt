package com.example.yinyang.repository

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yinyang.models.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.postgrest

class UserRepository(private val client: SupabaseClient) {
    suspend fun getUserInfo() : MutableState<User?> {
        val userInfo = mutableStateOf<User?>(null)

        try {
            val result = client.postgrest["user"]
                .select(
                    single = true,
                ) {
                    User::userUuid eq client.gotrue.retrieveUserForCurrentSession().id
                }

            userInfo.value = result.decodeAs()
        } catch (e: Exception) {
            throw e
        }

        return userInfo
    }

    suspend fun updateUserInfo(
        userId: Int,
        newName: String,
        newLastname: String
    ) {
        try {
            client.postgrest["user"]
                .update({
                    User::firstName setTo newName
                    User::lastName setTo newLastname
                }) {
                    User::id eq userId
                }
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getUserSession(): UserInfo {
        return try {
            client.gotrue.retrieveUserForCurrentSession()
        } catch (e: Exception) {
            throw e
        }
    }
}