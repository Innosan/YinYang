package com.example.yinyang.repository

import com.example.yinyang.models.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class ProductRepository(val client: SupabaseClient) {
    private val gson = Gson()
    val productListType = object : TypeToken<List<Product>>() {}.type
    suspend fun getProducts(): List<Product> {
        val result = com.example.yinyang.network.client.postgrest["product"]
            .select("*, category_id(title)")

        return gson.fromJson(result.body.toString(), productListType)
    }
}