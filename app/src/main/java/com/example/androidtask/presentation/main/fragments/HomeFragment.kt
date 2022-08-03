package com.example.androidtask.presentation.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.RequestManager
import com.example.androidtask.common.AppIds
import com.example.androidtask.common.Constants
import com.example.androidtask.common.TAG
import com.example.androidtask.common.dialog.CustomDialog
import com.example.androidtask.common.utils.BaseFragment
import com.example.androidtask.common.utils.deleteCurrentFragmentAfterNavigate
import com.example.androidtask.common.utils.navigateSafely
import com.example.androidtask.common.utils.snackbar
import com.example.androidtask.databinding.FragmentHomeBinding
import com.example.androidtask.domain.mappers.Product
import com.example.androidtask.domain.modules.User
import com.example.androidtask.presentation.main.fragments.adapters.ProductAdapter
import com.example.androidtask.presentation.main.fragments.events.MainUiEvent
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, MainViewModel>() {
    override val viewmodel: MainViewModel by viewModels()

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var productAdapter: ProductAdapter

    private var currentAvailable: Int = 30

    private var skip = 30
    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    private val newProducts = ArrayList<Product>()

    override fun subscribeToObservables() {

        viewmodel.userState.observe(viewLifecycleOwner) {
            it?.let { loadUserData(it) }
        }
        viewmodel.logoutState.observe(viewLifecycleOwner) {
            it.data?.let {
                val option = findNavController().deleteCurrentFragmentAfterNavigate()
                findNavController().navigateSafely(
                    AppIds.action_homeFragment_to_loginFragment,
                    navOptions = option
                )
            }

            uiCommunicationListener.showProgressBar(it.loading)

            it.message?.let {
                snackbar(it)
            }
        }
        viewmodel.productsUIState.observe(viewLifecycleOwner) {
            it.data?.let {
                if (it.isNotEmpty()) {
                    val oldCount: Int = newProducts.size
                    newProducts.addAll(it)
                    Log.i(TAG, "subscribeToObservables: ${newProducts.size}")
                    productAdapter.products = newProducts
                    productAdapter.notifyItemRangeChanged(oldCount, newProducts.size)
                }
            }
            uiCommunicationListener.showProgressBar(it.loading)
            it.message?.let {
                snackbar(it)
            }
        }

    }

    private fun loadUserData(it: User) {
        glide.load(Constants.DEFAULT_IMAGE).into(binding.userImageProfile)
        binding.usernameTopBar.text = it.username
        binding.emailTopBar.text = it.email
        binding.phoneTopBar.text = it.phone
    }

    private fun setupProductRecyclerView() = binding.recylerViewProduct.apply {
        itemAnimator = null
        isNestedScrollingEnabled = true
        layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        adapter = productAdapter
    }

    override fun actions() {

        binding.logoutAppBar.setOnClickListener {
            CustomDialog.showDialogForLogout(requireContext()) {
                if (it)viewmodel.sendAction(MainUiEvent.Logout)
            }
        }

        productAdapter.setOnItemClickListener {
            CustomDialog.showDialogDetails(requireContext(),it,glide)
        }
    }

    override fun onFragmentCreate(view: View, savedInstanceState: Bundle?) {
        viewmodel.sendAction(MainUiEvent.LoadProducts(skip))
        setupProductRecyclerView()
        binding.recylerViewProduct.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.recylerViewProduct.canScrollVertically(1)) {
//                    if (skip>=limit) {
//                        skip += limit
                    skip = newProducts.size
                    viewmodel.sendAction(MainUiEvent.LoadProducts(skip))
                    //  }
                }


            }

        })

    }


    private fun cancelShimmer(shimmerFrameLayout: ShimmerFrameLayout) {
        shimmerFrameLayout.stopShimmer()
        shimmerFrameLayout.visibility = View.GONE
    }
}