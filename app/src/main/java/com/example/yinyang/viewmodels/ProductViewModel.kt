package com.example.yinyang.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yinyang.models.Product
import com.example.yinyang.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor (
    private val productRepository: ProductRepository,
    ) :
    ViewModel()  {
    private val _products = MutableStateFlow(emptyList<Product>())
    val products: StateFlow<List<Product>> = _products

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun refresh() {
        viewModelScope.launch {
            try {
                val productsDeferred = async { productRepository.getProducts() }

                _isRefreshing.emit(true)

                _products.emit(productsDeferred.await())

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