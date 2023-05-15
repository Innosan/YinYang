package com.example.yinyang.repository

import com.example.yinyang.models.Product
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class ProductRepository(val client: SupabaseClient) {
    suspend fun getProducts(): List<Product> {
        val result = com.example.yinyang.network.client.postgrest["product"]
            .select("*, category_id(title)")

        return result.decodeList()
    }
}