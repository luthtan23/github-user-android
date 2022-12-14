package com.luthtan.github_user_android.feature

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.luthtan.github_user_android.MainViewModel
import com.luthtan.github_user_android.R
import com.luthtan.github_user_android.base.BaseFragment
import com.luthtan.github_user_android.databinding.FragmentBottomNavBinding

class FragmentBottomNav : BaseFragment<FragmentBottomNavBinding, MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()
    override val binding: FragmentBottomNavBinding by lazy {
        FragmentBottomNavBinding.inflate(layoutInflater)
    }

    private lateinit var navHostFragment: NavHostFragment


    override fun onInitViews() {

//        binding.bottomNav.itemIconTintList = null

        navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_BottomNav) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNav, navHostFragment.navController)

        if (parentFragmentManager.findFragmentById(R.id.nav_host_BottomNav) is FragmentBottomNav) {
            requireActivity().finish()
        }

    }

    override fun onInitObservers() {

    }

    fun goToProfileFragment() {
        findNavController().navigate(FragmentBottomNavDirections.actionFragmentBottomNavToProfileFragment())
    }

    fun goToDetailFragment(user: String) {
        findNavController().navigate(FragmentBottomNavDirections.actionFragmentBottomNavToDetailFragment(user))
    }

    fun goToSearchFragment() {
        findNavController().navigate(FragmentBottomNavDirections.actionFragmentBottomNavToSearchFragment())
    }

}