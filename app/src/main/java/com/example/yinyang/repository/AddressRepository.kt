package com.example.yinyang.repository

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yinyang.models.DeliveryAddress
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class AddressRepository(private val client: SupabaseClient) {
    suspend fun getUserAddresses(userId: Int?) : MutableState<List<DeliveryAddress>> {
        val addresses = mutableStateOf(emptyList<DeliveryAddress>())

        try {
            val result = client.postgrest["delivery_address"]
                .select {
                    DeliveryAddress::userId eq userId
                }

            addresses.value = result.decodeList()
        } catch (e: Exception) {
            println(e.message)
        }

        return addresses
    }

    suspend fun addAddress(addressMessage: String, userId: Int) {
        val newAddress = DeliveryAddress(
            isStarred = false,
            id = null,
            address = addressMessage,
            description = "some desc",
            userId = userId
        )

        try {
            client.postgrest["delivery_address"]
                .insert(newAddress)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    suspend fun deleteAddress(addressId: Int) {
        try {
            client.postgrest["delivery_address"]
                .delete {
                    DeliveryAddress::id eq addressId
                }
        } catch (e: Exception) {
            println(e.message)
        }
    }
}