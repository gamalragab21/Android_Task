package com.example.androidtask.domain.repositories

import com.example.androidtask.data.modules.ProductsResponseDto

interface IMainRepository {
    suspend fun getProducts(skip:Int): ProductsResponseDto

}