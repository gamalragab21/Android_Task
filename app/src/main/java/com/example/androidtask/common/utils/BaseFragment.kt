package com.example.androidtask.common.utils

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.androidtask.data.cache.ComplexPreferences
import javax.inject.Inject


abstract class BaseFragment<Binding : ViewBinding,VM:ViewModel?>: Fragment() {
    private var _binding: Binding? = null
    val binding get() = _binding!!

    abstract val viewmodel:VM

    @Inject
    lateinit var complexPreferences: ComplexPreferences
    lateinit var uiCommunicationListener: UICommunicationListener


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFragmentCreate(view,savedInstanceState)
        actions()
        subscribeToObservables()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = getViewBinding()
        return binding.root
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            uiCommunicationListener = context as UICommunicationListener
        } catch (e: ClassCastException) {
            Log.e("AppDebug", "onAttach: $context must implement UICommunicationListener")
        }
    }

    override fun onStop() {
        super.onStop()
        uiCommunicationListener.showProgressBar(false)
    }
//    @Suppress("UNCHECKED_CAST")
//    private fun viewModelClass(): KClass<T> {
//        // dirty hack to get generic type https://stackoverflow.com/a/1901275/719212
//        return ((javaClass.genericSuperclass as ParameterizedType)
//            .actualTypeArguments[0] as Class<T>).kotlin
//    }
    abstract fun getViewBinding(): Binding

    abstract fun subscribeToObservables()
    abstract fun actions()

    abstract fun onFragmentCreate(view: View, savedInstanceState: Bundle?)
}