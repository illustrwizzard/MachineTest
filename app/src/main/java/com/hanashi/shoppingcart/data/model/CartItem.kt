package com.hanashi.shoppingcart.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val itemID: String,
    val itemName: String,
    val sellingPrice: Double,
    val taxPercentage: Double,
    var quantity: Int
)