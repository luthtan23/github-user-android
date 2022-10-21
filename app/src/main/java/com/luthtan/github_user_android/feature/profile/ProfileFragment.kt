package com.luthtan.github_user_android.feature.profile

import androidx.fragment.app.viewModels
import com.luthtan.github_user_android.base.BaseFragment
import com.luthtan.github_user_android.data.db.ProfileModel
import com.luthtan.github_user_android.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModels()

    override val binding: FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    override fun onInitObservers() {

    }

    override fun onInitViews() {

    }
}