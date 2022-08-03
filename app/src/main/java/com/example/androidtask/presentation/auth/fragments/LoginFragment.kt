package com.example.androidtask.presentation.auth.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidtask.common.AppIds
import com.example.androidtask.common.AppStrings
import com.example.androidtask.common.Constants
import com.example.androidtask.common.utils.*
import com.example.androidtask.databinding.FragmentLoginBinding
import com.example.androidtask.domain.modules.User
import com.example.androidtask.presentation.auth.events.AuthUiEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, AuthViewModel>() {
    override val viewmodel: AuthViewModel by viewModels()

    override fun getViewBinding(): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater)

    override fun subscribeToObservables() {
        viewmodel.loginUiState.observe(viewLifecycleOwner) {
            it.data?.let {
            setLoggedAndNavigate(it)
            }
            it.message?.let {
                snackbar(it)
            }
            uiCommunicationListener.showProgressBar(it.loading)


        }
    }

    private fun setLoggedAndNavigate(it: User) {
        complexPreferences.putBoolean(Constants.IS_LOGIN, true)
        complexPreferences.commit()
        val options = findNavController().deleteCurrentFragmentAfterNavigate()
        findNavController().navigateSafely(
            AppIds.action_loginFragment_to_homeFragment,
            navOptions = options
        )
    }

    override fun actions() {
        binding.notHaveAnAccountTv.setOnClickListener {
            val options = findNavController().deleteCurrentFragmentAfterNavigate()
            findNavController().navigateSafely(AppIds.action_loginFragment_to_registerFragment, navOptions = options)
        }
        binding.loginBtn.setOnClickListener {
            clearErrorsFromTextFields()
            val username = binding.loginEtEmail.text.toString()
            val password = binding.loginEtPassword.text.toString()

            when {
                username.isEmpty() -> {
                    snackbar(getString(AppStrings.username_is_empty))
                    binding.loginLayoutEmail.error = getString(AppStrings.username_is_empty)
                }
                password.isEmpty() -> {
                    snackbar(getString(AppStrings.password_is_empty))
                    binding.loginLayoutPassword.error = getString(AppStrings.password_is_empty)
                }
                else -> {
                    binding.root.hideKeyboard()
                    viewmodel.sendAction(AuthUiEvent.Login(username, password))
                }
            }

        }
    }

    private fun clearErrorsFromTextFields() {
        binding.loginLayoutEmail.isErrorEnabled = false
        binding.loginLayoutEmail.clearFocus()
        binding.loginLayoutPassword.isErrorEnabled = false
        binding.loginLayoutPassword.clearFocus()
    }


    override fun onFragmentCreate(view: View, savedInstanceState: Bundle?) {

    }
}