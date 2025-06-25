package com.hanashi.shoppingcart.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hanashi.shoppingcart.data.model.CartItem

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): LiveData<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartItem)

    @Update
    suspend fun updateCartItem(item: CartItem)

    @Delete
    suspend fun deleteCartItem(item: CartItem)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Query("SELECT * FROM cart_items WHERE itemID = :itemID LIMIT 1")
    suspend fun getCartItemById(itemID: String): CartItem?

    @Query("SELECT * FROM cart_items")
    suspend fun getAllCartItemsNow(): List<CartItem>

}