package com.example.androidtask.presentation.main.fragments

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtask.common.Constants
import com.example.androidtask.common.helpers.Resource
import com.example.androidtask.data.cache.ComplexPreferences
import com.example.androidtask.domain.mappers.Product
import com.example.androidtask.domain.modules.User
import com.example.androidtask.domain.usecases.ProductsDataUseCase
import com.example.androidtask.presentation.main.fragments.events.MainUiEvent
import com.example.androidtask.presentation.main.fragments.state.LogoutUiState
import com.example.androidtask.presentation.main.fragments.state.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val complexPreferences: ComplexPreferences,
    private val productsDataUseCase: ProductsDataUseCase
) : ViewModel() {

    private val mainUiActions = Channel<MainUiEvent>(Channel.UNLIMITED)

    private val _userState = MutableLiveData<User>()
    val userState: LiveData<User> get() = _userState

    private val _logoutState = MutableLiveData<LogoutUiState>()
    val logoutState: LiveData<LogoutUiState> get() = _logoutState

    private val _productsUIState = MutableLiveData<MainUiState>()
    val productsUIState: LiveData<MainUiState> get() = _productsUIState



    init {
        triggerAction()
        sendAction(MainUiEvent.GetUserData)
    }

    fun sendAction(action: MainUiEvent) {
        viewModelScope.launch {
            mainUiActions.send(action)
        }
    }

    private fun triggerAction() {
        viewModelScope.launch {
            mainUiActions.consumeAsFlow().collect {action->
                when (action) {
                    is MainUiEvent.GetUserData -> {
                        getUserData()
                    }
                    is MainUiEvent.LoadProducts -> {
                        loadAllProducts(action.skip)
                    }
                    is MainUiEvent.Logout -> {
                        logout()
                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun logout() {
        _logoutState.postValue(LogoutUiState(true, null, null))
        viewModelScope.launch {
            complexPreferences.putBoolean(Constants.IS_LOGIN, false)
            complexPreferences.commit()
            val user = complexPreferences.getObject(Constants.USER_DATA, User::class.java)
            _logoutState.postValue(LogoutUiState(false, user, "Logout Successfully"))
        }
    }

    private fun loadAllProducts(skip: Int) {
        productsDataUseCase(skip).onEach {
            when (it) {
                is Resource.Success -> {
                    _productsUIState.postValue(
                        MainUiState(
                            data =it.data!!
                        )
                    )
                }
                is Resource.Error -> {
                    _productsUIState.postValue(
                        MainUiState(
                            message = it.message,
                        )
                    )
                }
                is Resource.Loading -> {
                    _productsUIState.postValue(MainUiState(loading = true))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getUserData() {
        viewModelScope.launch {
            val user = complexPreferences.getObject(Constants.USER_DATA, User::class.java)
            _userState.postValue(user!!)
        }
    }


}