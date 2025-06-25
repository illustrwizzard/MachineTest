package com.hanashi.shoppingcart.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanashi.shoppingcart.data.model.CartItem
import com.hanashi.shoppingcart.data.repository.Repository
import kotlinx.coroutines.launch

class CartViewModel(private val repository: Repository) : ViewModel() {

    val cartItems: LiveData<List<CartItem>> = repository.allCartItems

    fun getSubtotal(items: List<CartItem>): Double {
        return items.sumOf { it.sellingPrice * it.quantity }
    }

    fun getTax(items: List<CartItem>): Double {
        return items.sumOf { (it.sellingPrice * it.taxPercentage / 100) * it.quantity }
    }

    fun getTotal(items: List<CartItem>): Double {
        return getSubtotal(items) + getTax(items)
    }

    fun clearCart() = viewModelScope.launch {
        repository.clearCart()
    }

    fun deleteItem(item: CartItem) = viewModelScope.launch {
        repository.deleteCartItem(item)
    }

    fun updateItem(item: CartItem) = viewModelScope.launch {
        repository.updateCartItem(item)
    }
}