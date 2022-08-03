package com.example.androidtask.presentation.auth.states

import com.example.androidtask.domain.modules.User

data class LoginUIState(
    val loading: Boolean = false,
    val data: User?=null,
    val message: String? = null,
)