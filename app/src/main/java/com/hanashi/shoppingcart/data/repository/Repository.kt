package com.hanashi.shoppingcart.data.repository

import com.hanashi.shoppingcart.data.api.RetrofitInstance
import com.hanashi.shoppingcart.data.db.CartDao
import com.hanashi.shoppingcart.data.model.CartItem

class Repository(private val cartDao: CartDao) {

    val allCartItems = cartDao.getAllCartItems()

    suspend fun fetchItemsFromApi() = RetrofitInstance.api.getItems()

    suspend fun insertCartItem(item: CartItem) = cartDao.insertCartItem(item)

    suspend fun updateCartItem(item: CartItem) = cartDao.updateCartItem(item)

    suspend fun deleteCartItem(item: CartItem) = cartDao.deleteCartItem(item)

    suspend fun clearCart() = cartDao.clearCart()

    suspend fun getCartItemById(itemID: String): CartItem? = cartDao.getCartItemById(itemID)
    suspend fun getAllCartItemEntities(): List<CartItem> =
        cartDao.getAllCartItemsNow()
}