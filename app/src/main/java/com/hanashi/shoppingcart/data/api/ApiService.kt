package com.hanashi.shoppingcart.data.api

import com.hanashi.shoppingcart.data.model.Item
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/")
    suspend fun getItems(): Response<List<Item>>
}