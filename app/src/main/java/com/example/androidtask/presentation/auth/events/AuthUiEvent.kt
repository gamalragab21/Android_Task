package com.example.androidtask.presentation.auth.events

sealed class AuthUiEvent {
    class Login(val username:String,val password:String):AuthUiEvent()
    class Register(val name:String,val mail:String,val phone:String,val password:String,val confirmPassword:String):AuthUiEvent()
}