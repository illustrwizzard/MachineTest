package com.hanashi.shoppingcart.data.model

data class Item(
    val itemID: String,
    val itemName: String,
    val sellingPrice: Double,
    val taxPercentage: Double
)
