package com.example.androidtask.presentation.main.fragments.state

import com.example.androidtask.domain.modules.User

data class LogoutUiState(
    val loading: Boolean = false,
    val data: User?=null,
    val message: String? = null,
)