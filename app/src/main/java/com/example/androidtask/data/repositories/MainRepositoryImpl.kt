package com.example.androidtask.data.repositories

import com.example.androidtask.data.modules.ProductsResponseDto
import com.example.androidtask.data.remote.IApiService
import com.example.androidtask.domain.repositories.IMainRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val IApiService: IApiService
) : IMainRepository {
    override suspend fun getProducts(skip:Int): ProductsResponseDto =
        IApiService.getProducts(skip)

}