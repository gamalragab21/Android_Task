package com.example.androidtask.presentation.main.fragments.events

sealed class MainUiEvent {
    class LoadProducts(val skip:Int):MainUiEvent()
    object GetUserData:MainUiEvent()
    object Logout:MainUiEvent()
}