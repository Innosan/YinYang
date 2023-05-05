package com.example.yinyang.ui.shared.models.user

import com.example.yinyang.ui.shared.models.DeliveryAddress
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.postgrest

class UserRepository(private val client: SupabaseClient) {
    suspend fun getUserInfo() : User? {
        return try {
            val result = com.example.yinyang.ui.utils.client.postgrest["user"]
                .select(
                    single = true,
                ) {
                    User::userUuid eq com.example.yinyang.ui.utils.client.gotrue.retrieveUserForCurrentSession().id
                }

            result.decodeAs()
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }

    suspend fun getUserSession(): UserInfo? {
        return try {
            com.example.yinyang.ui.utils.client.gotrue.retrieveUserForCurrentSession()
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }

    suspend fun getUserAddresses(userId: Int?) : List<DeliveryAddress> {
        return try {
            val result = com.example.yinyang.ui.utils.client.postgrest["delivery_address"]
                .select() {
                    DeliveryAddress::userId eq userId
                }

            result.decodeList()
        } catch (e: Exception) {
            println(e.message)
            emptyList()
        }
    }
}