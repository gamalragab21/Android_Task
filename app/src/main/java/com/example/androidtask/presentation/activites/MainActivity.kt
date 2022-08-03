package com.example.androidtask.presentation.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavInflater
import androidx.navigation.fragment.NavHostFragment
import com.example.androidtask.R
import com.example.androidtask.common.utils.UICommunicationListener
import com.example.androidtask.data.cache.ComplexPreferences
import com.example.androidtask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener,UICommunicationListener {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var inflater: NavInflater
    private lateinit var graph: NavGraph
    private lateinit var navController: NavController
    @Inject
    lateinit var complexPreferences: ComplexPreferences
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
            navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        inflater = navHostFragment.navController.navInflater
        graph = inflater.inflate(R.navigation.navigation_graph)
        navHostFragment.navController.setGraph(graph)

    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
//        when (destination.id) {
//            AppIds.loginFragment,
//            AppIds.registerFragment,
//            AppIds.splashFragment,
//            AppIds.onBoardingFragment,
//            -> {
//                binding.bottomNav.visibility = View.GONE
//            }
//            else -> {
//                binding.bottomNav.visibility = View.VISIBLE
//            }
//        }
    }

    override fun showProgressBar(isLoading: Boolean) {
        binding.layoutProgressDialog.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}