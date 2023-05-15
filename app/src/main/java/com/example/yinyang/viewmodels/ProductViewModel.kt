package com.example.yinyang.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yinyang.models.Product
import com.example.yinyang.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor (
    private val productRepository: ProductRepository,
    ) :
    ViewModel()  {
    private val _products = mutableStateOf(emptyList<Product>())
    val products: State<List<Product>> = _products

    init {
        viewModelScope.launch {
            _products.value = productRepository.getProducts()
        }
    }
}