package com.example.yinyang.repository

import com.example.yinyang.models.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.postgrest

class UserRepository(private val client: SupabaseClient) {
    suspend fun getUserInfo() : User {
        return try {
            val result = client.postgrest["user"]
                .select(
                    single = true,
                ) {
                    User::userUuid eq client.gotrue.retrieveUserForCurrentSession().id
                }

            result.decodeAs()
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