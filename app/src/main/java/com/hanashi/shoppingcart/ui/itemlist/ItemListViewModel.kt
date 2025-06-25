package com.hanashi.shoppingcart.ui.itemlist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanashi.shoppingcart.data.model.CartItem
import com.hanashi.shoppingcart.data.model.Item
import com.hanashi.shoppingcart.data.repository.Repository
import com.hanashi.shoppingcart.utils.NetworkUtils
import kotlinx.coroutines.launch

class ItemListViewModel(
    private val repository: Repository,
    private val context: Context
) : ViewModel() {

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> get() = _items

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    init {
        fetchItems()
    }

    fun fetchItems() = viewModelScope.launch {
        if (NetworkUtils.isConnected(context)) {
            try {
                val response = repository.fetchItemsFromApi()
                if (response.isSuccessful) {
                    _items.value = response.body()
                } else {
                    _error.value = "Failed to load items"
                }
            } catch (e: Exception) {
                _error.value = "API Error: ${e.localizedMessage}"
            }
        } else {
            val localCartItems = repository.getAllCartItemEntities()
            if (localCartItems.isNotEmpty()) {
                _items.value = localCartItems.map {
                    Item(it.itemID, it.itemName, it.sellingPrice, it.taxPercentage)
                }
            } else {
                _error.value = "No internet. Please connect to load items."
            }
        }
    }

    fun addItemToCart(item: Item) = viewModelScope.launch {
        val existing = repository.getCartItemById(item.itemID)
        if (existing != null) {
            existing.quantity += 1
            repository.updateCartItem(existing)
        } else {
            val newItem = CartItem(
                itemID = item.itemID,
                itemName = item.itemName,
                sellingPrice = item.sellingPrice,
                taxPercentage = item.taxPercentage,
                quantity = 1
            )
            repository.insertCartItem(newItem)
        }
    }
}