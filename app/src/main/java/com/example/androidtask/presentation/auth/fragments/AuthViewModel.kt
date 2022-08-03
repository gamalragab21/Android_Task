package com.example.androidtask.presentation.auth.fragments

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtask.common.Constants
import com.example.androidtask.data.cache.ComplexPreferences
import com.example.androidtask.domain.modules.User
import com.example.androidtask.presentation.auth.events.AuthUiEvent
import com.example.androidtask.presentation.auth.states.LoginUIState
import com.example.androidtask.presentation.auth.states.RegisterUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val complexPreferences: ComplexPreferences
) : ViewModel() {

    private val authUiActions = Channel<AuthUiEvent>(Channel.UNLIMITED)

    private val _loginUiState = MutableLiveData<LoginUIState>()
    val loginUiState: LiveData<LoginUIState> get() = _loginUiState


    private val _registerUiState = MutableLiveData<RegisterUIState>()
    val registerUiState: LiveData<RegisterUIState> get() = _registerUiState

    init {
        triggerAction()
    }

    fun sendAction(action: AuthUiEvent) {
        viewModelScope.launch {
            authUiActions.send(action)
        }
    }

    private fun triggerAction() {
        viewModelScope.launch {
            authUiActions.consumeAsFlow().collect {
                when (it) {
                    is AuthUiEvent.Login -> {
                        loginUser(it.username, it.password)
                    }
                    is AuthUiEvent.Register -> {
                        registerNewUser(
                            it.name,
                            it.mail,
                            it.phone,
                            it.password
                        )
                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun registerNewUser(
        name: String,
        mail: String,
        phone: String,
        password: String,
    ) {
        _registerUiState.postValue(RegisterUIState(true,))
        viewModelScope.launch {

            val user = User(name, mail, phone,password)
            complexPreferences.putObject(Constants.USER_DATA, user)
            complexPreferences.commit()
            _registerUiState.postValue(RegisterUIState(false, user, "Register Successfully"))
        }
    }

    private fun loginUser(username: String, password: String) {
        _loginUiState.postValue(LoginUIState(true))
        viewModelScope.launch {
            val user = complexPreferences.getObject(Constants.USER_DATA, User::class.java)
            if (user != null) {
                if ((user.email != username) and  (user.phone != username)) {
                    _loginUiState.postValue(
                        LoginUIState(
                            false,
                            null,
                            "Username Or Email not valid"
                        )
                    )

                } else if (user.password != password) {
                    _loginUiState.postValue(LoginUIState(false, null, "Password not valid"))
                } else {
                    _loginUiState.postValue(LoginUIState(false, user, "Login Successfully"))
                }
            } else {
                _loginUiState.postValue(LoginUIState(false, null, "Password and Email  not Found"))

            }
        }
    }


}