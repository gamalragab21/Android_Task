package com.example.androidtask.presentation.general

import android.os.Bundle
import android.provider.SyncStateContract
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.androidtask.common.AppIds
import com.example.androidtask.common.Constants
import com.example.androidtask.common.utils.BaseFragment
import com.example.androidtask.common.utils.deleteCurrentFragmentAfterNavigate
import com.example.androidtask.common.utils.navigateSafely
import com.example.androidtask.databinding.FragmentSplashBinding
import com.example.androidtask.presentation.auth.fragments.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@AndroidEntryPoint
 class SplashFragment:BaseFragment<FragmentSplashBinding,AuthViewModel>() {
     override val viewmodel: AuthViewModel by viewModels()


    override fun getViewBinding(): FragmentSplashBinding =FragmentSplashBinding.inflate(layoutInflater)

    override fun subscribeToObservables() {

    }

    override fun actions() {

    }

    override fun onFragmentCreate(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launchWhenStarted {
            delay(3000)
            withContext(Dispatchers.Main) {
                checkUserStatsAndNavigate()
            }
        }
    }

    private fun checkUserStatsAndNavigate() {
        val isLogged = complexPreferences.getBoolean(Constants.IS_LOGIN, false)
        val navOptions = findNavController().deleteCurrentFragmentAfterNavigate()
        if (isLogged ) {
            findNavController().navigateSafely(
                AppIds.action_splashFragment_to_homeFragment,
                navOptions = navOptions
            )
        } else {
            findNavController().navigateSafely(
                AppIds.action_splashFragment_to_loginFragment,
                navOptions = navOptions
            )

        }
    }
}