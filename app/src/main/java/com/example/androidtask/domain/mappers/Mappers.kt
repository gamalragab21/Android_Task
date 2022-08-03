package com.example.androidtask.domain.mappers

import com.example.androidtask.data.modules.ProductDto

fun ProductDto.toProduct():Product=
    Product(brand, category, description, discountPercentage, id, images, price, rating, stock, thumbnail, title)