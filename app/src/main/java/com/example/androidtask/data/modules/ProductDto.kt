package com.example.androidtask.data.modules


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class ProductDto(
    @SerializedName("brand")
    @Expose
    var brand: String,
    @SerializedName("category")
    @Expose
    var category: String,
    @SerializedName("description")
    @Expose
    var description: String,
    @SerializedName("discountPercentage")
    @Expose
    var discountPercentage: Double,
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("images")
    @Expose
    var images: List<String>,
    @SerializedName("price")
    @Expose
    var price: Int,
    @SerializedName("rating")
    @Expose
    var rating: Double,
    @SerializedName("stock")
    @Expose
    var stock: Int,
    @SerializedName("thumbnail")
    @Expose
    var thumbnail: String,
    @SerializedName("title")
    @Expose
    var title: String
)