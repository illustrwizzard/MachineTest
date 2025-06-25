package com.hanashi.shoppingcart.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hanashi.shoppingcart.data.repository.Repository
import com.hanashi.shoppingcart.ui.cart.CartViewModel
import com.hanashi.shoppingcart.ui.itemlist.ItemListViewModel

class ViewModelFactory(
    private val repository: Repository,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ItemListViewModel::class.java) ->
                ItemListViewModel(repository, context) as T
            modelClass.isAssignableFrom(CartViewModel::class.java) ->
                CartViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}