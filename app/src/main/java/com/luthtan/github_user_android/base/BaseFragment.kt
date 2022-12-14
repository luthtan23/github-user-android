package com.luthtan.github_user_android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.luthtan.github_user_android.R
import timber.log.Timber

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    abstract val viewModel: VM
    abstract val binding: VB

    private var hasInitialize: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    protected open fun onBackPressed() {
        if (!findNavController().navigateUp()) {
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.background_base_F2F5F7)

        requireActivity().window.setBackgroundDrawable(null)

        if (!hasInitialize) {
            onInitViews()
            onInitObservers()
        }

        viewModel.errorApiHandler.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { errorModel ->
                Timber.d("Error model ${errorModel.code}")
                Snackbar.make(binding.root, "Network error", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    abstract fun onInitViews()

    abstract fun onInitObservers()

    override fun onPause() {
        super.onPause()
        hasInitialize = true
    }
}