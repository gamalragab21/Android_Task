package com.example.androidtask.domain.mappers

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Product(
    var brand: String,
    var category: String,
    var description: String,
    var discountPercentage: Double,
    var id: Int,
    var images: List<String>,
    var price: Int,
    var rating: Double,
    var stock: Int,
    var thumbnail: String,
    var title: String
)
