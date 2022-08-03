package com.example.androidtask.data.remote

import com.example.androidtask.data.modules.ProductsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {
    @GET("products")
    suspend fun getProducts(@Query("skip") skip: Int,@Query("limit") limit: Int=30): ProductsResponseDto
}