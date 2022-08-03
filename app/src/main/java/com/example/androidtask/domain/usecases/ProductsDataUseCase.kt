package com.example.androidtask.domain.usecases

import android.util.Log
import com.example.androidtask.common.TAG
import com.example.androidtask.common.helpers.Resource
import com.example.androidtask.domain.mappers.Product
import com.example.androidtask.domain.mappers.toProduct
import com.example.androidtask.domain.repositories.IMainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductsDataUseCase @Inject constructor(
    private val repository: IMainRepository,
) {
    operator fun invoke(skip: Int): Flow<Resource<List<Product>, String>> = flow {
        emit(Resource.Loading())
        try {
            val result = repository.getProducts(skip)


            val homeData = Resource.Success(result.productDtos.map { it.toProduct() })
            emit(homeData)
        } catch (e: Exception) {
//            val errorResponse: ErrorResponseBody<String> =
//                ErrorUtils.parseError<ErrorResponseBody<String>>(e)
//                    ?: ErrorResponseBody(e.localizedMessage)
//            Log.i(TAG, "invoke: ${errorResponse.toString()}")
//            emit(Resource.Error(errorResponse.message, null))
            Log.e(TAG, "invoke: home data ${e.localizedMessage}")
            emit(Resource.Error(e.message, null))
        }
    }
}