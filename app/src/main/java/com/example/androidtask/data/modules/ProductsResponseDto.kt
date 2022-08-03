package com.example.androidtask.data.modules


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class ProductsResponseDto(
    @SerializedName("limit")
    @Expose
    var limit: Int,
    @SerializedName("products")
    @Expose
    var productDtos: List<ProductDto>,
    @SerializedName("skip")
    @Expose
    var skip: Int,
    @SerializedName("total")
    @Expose
    var total: Int
)