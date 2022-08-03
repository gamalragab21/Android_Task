package com.example.androidtask.common.utils

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ErrorResponseBody<T>(
    @SerializedName("message")
    @Expose
    var message: T? = null,
    @Expose
    @SerializedName("status")
    var status: Boolean=false
)