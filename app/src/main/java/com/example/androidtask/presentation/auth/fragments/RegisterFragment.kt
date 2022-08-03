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
import com.example.androidtask.common.helpers.AppValidation
import com.example.androidtask.common.utils.*
import com.example.androidtask.databinding.FragmentLoginBinding
import com.example.androidtask.databinding.FragmentRegisterBinding
import com.example.androidtask.domain.modules.User
import com.example.androidtask.presentation.auth.events.AuthUiEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment:BaseFragment<FragmentRegisterBinding,AuthViewModel> (){
    override val viewmodel: AuthViewModel by  viewModels()

    override fun getViewBinding(): FragmentRegisterBinding = FragmentRegisterBinding.inflate(layoutInflater)

    override fun subscribeToObservables() {
        viewmodel.registerUiState.observe(viewLifecycleOwner) {
            uiCommunicationListener.showProgressBar(it.loading)
            it.data?.let {
                saveLoggedDataAndNavigate(it)
            }
            it.message?.let {
                snackbar(it)
            }

        }

    }

    private fun saveLoggedDataAndNavigate(it: User) {
        complexPreferences.putBoolean(Constants.IS_LOGIN, true)
        complexPreferences.commit()
        val options = findNavController().deleteCurrentFragmentAfterNavigate()
        findNavController().navigateSafely(
            AppIds.action_registerFragment_to_homeFragment,
            navOptions = options
        )
    }

    override fun actions() {
        binding.haveAnAccountTv.setOnClickListener {
            val options = findNavController().deleteCurrentFragmentAfterNavigate()

            findNavController().navigateSafely(AppIds.action_registerFragment_to_loginFragment, navOptions = options)
        }
        binding.registerBtn.setOnClickListener {

            clearErrorsFromTextFields()
            val name = binding.registerEtName.text.toString()
            val email = binding.registerEtEmail.text.toString()
            val phone = binding.registerEtPhone.text.toString()
            val password = binding.registerEtPassword.text.toString()
            val confirmPassword = binding.registerEtRepeatPassword.text.toString()
            when {
                name.isEmpty() -> {
                    snackbar(getString(AppStrings.name_is_empty))
                    binding.registerLayoutName.error = getString(AppStrings.name_is_empty)
                }
                email.isEmpty() -> {
                    snackbar(getString(AppStrings.email_is_empty))
                    binding.registerLayoutEmail.error = getString(AppStrings.email_is_empty)
                }
                email.isNotEmpty() && !AppValidation.isValidEmail(email) -> {
                    snackbar(getString(AppStrings.email_is_not_vaild))
                    binding.registerLayoutEmail.error = getString(AppStrings.email_is_not_vaild)
                }
                phone.isEmpty() -> {
                    snackbar(getString(AppStrings.phone_is_empty))
                    binding.registerLayoutPhone.error = getString(AppStrings.phone_is_empty)
                }
                phone.isNotEmpty() && !AppValidation.validateMobile(phone) -> {
                    snackbar(getString(AppStrings.phone_is_not_vaild))
                    binding.registerLayoutPhone.error = getString(AppStrings.phone_is_not_vaild)
                }
                password.isEmpty() -> {
                    snackbar(getString(AppStrings.password_is_empty))
                    binding.registerLayoutPassword.error = getString(AppStrings.password_is_empty)
                }
                password.isNotEmpty() && !AppValidation.optionOneForPassword(password) -> {
                    snackbar(getString(AppStrings.password_grate_than_8))
                    binding.registerLayoutPassword.error = getString(AppStrings.password_grate_than_8)
                }
                password.isNotEmpty() && !AppValidation.optionTwoForPassword(password) -> {
                    snackbar(getString(AppStrings.password_up_low_case))
                    binding.registerLayoutPassword.error = getString(AppStrings.password_up_low_case)
                }
                password.isNotEmpty() && !AppValidation.optionThreeForPassword(password) -> {
                    snackbar(getString(AppStrings.password_include_1_number))
                    binding.registerLayoutPassword.error = getString(AppStrings.password_include_1_number)
                }
                confirmPassword.isEmpty() -> {
                    snackbar(getString(AppStrings.confirm_password_is_empty))
                    binding.registerLayoutRepeatPassword.error =
                        getString(AppStrings.confirm_password_is_empty)
                }
                password.isNotEmpty() && confirmPassword.isNotEmpty() && confirmPassword != password -> {
                    snackbar(getString(AppStrings.confirm_password_not_match))
                    binding.registerLayoutRepeatPassword.error = getString(AppStrings.confirm_password_not_match)

                }
                else -> {
                    binding.root.hideKeyboard()
                    viewmodel.sendAction(AuthUiEvent.Register(name,email,phone,password,confirmPassword))
                }

            }
        }
    }
    private fun clearErrorsFromTextFields() {
        binding.registerLayoutName.isErrorEnabled = false
        binding.registerLayoutName.clearFocus()
        binding.registerLayoutEmail.isErrorEnabled = false
        binding.registerLayoutEmail.clearFocus()
        binding.registerLayoutPhone.isErrorEnabled = false
        binding.registerLayoutPhone.clearFocus()
        binding.registerLayoutPassword.isErrorEnabled = false
        binding.registerLayoutPassword.clearFocus()
        binding.registerLayoutRepeatPassword.isErrorEnabled = false
        binding.registerLayoutRepeatPassword.clearFocus()
    }

    override fun onFragmentCreate(view: View, savedInstanceState: Bundle?) {

    }
}