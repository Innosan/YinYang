package com.example.yinyang.repository

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yinyang.models.DeliveryAddress
import com.example.yinyang.models.DeliveryAddressAdd
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Returning

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
            throw e
        }

        return addresses
    }

    suspend fun addAddress(userId: Int, addressMessage: String) {
        val newAddress = DeliveryAddressAdd(
            address = addressMessage,
            userId = userId
        )

        try {
            client.postgrest["delivery_address"]
                .insert(
                    value = newAddress,
                    returning = Returning.MINIMAL
                )
        } catch (e: Exception) {
            println(e.message)
        }
    }

    suspend fun deleteAddress(addressId: Int) {
        try {
            client.postgrest["delivery_address"]
                .delete(
                    returning = Returning.MINIMAL
                ) {
                    DeliveryAddress::id eq addressId
                }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    suspend fun updateAddress(addressId: Int, newAddress: String) {
        try {
            client.postgrest["delivery_address"]
                .update(
                    update = {
                        DeliveryAddress::address setTo newAddress

                    },

                    returning = Returning.MINIMAL,
                ) {
                    DeliveryAddress::id eq addressId
                }
        } catch (e: Exception) {
            println(e.message)
        }
    }
}