package com.example.yinyang.ui.shared.models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yinyang.ui.utils.client
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _products = mutableStateOf(emptyList<Product>())
    val products: State<List<Product>> = _products

    private suspend fun getProducts(): List<Product> {
        val result = client.postgrest["product"]
            .select("*, category_id(title)")

        return result.decodeList()
    }

    init {
        viewModelScope.launch {
            _products.value = getProducts()
        }
    }
}