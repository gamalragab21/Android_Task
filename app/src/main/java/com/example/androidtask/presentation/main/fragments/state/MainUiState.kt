package com.example.androidtask.presentation.main.fragments.state

import com.example.androidtask.domain.mappers.Product
import com.example.androidtask.domain.modules.User

data class MainUiState(
    val loading: Boolean = false,
    val data: List<Product> = emptyList(),
    val message: String? = null,
)