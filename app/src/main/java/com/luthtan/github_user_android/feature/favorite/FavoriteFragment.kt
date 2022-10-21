package com.luthtan.github_user_android.feature.favorite

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.luthtan.github_user_android.R
import com.luthtan.github_user_android.base.BaseFragment
import com.luthtan.github_user_android.base.util.CustomOnClickListener
import com.luthtan.github_user_android.data.dtos.entity.UserEntity
import com.luthtan.github_user_android.databinding.FragmentHomeBinding
import com.luthtan.github_user_android.feature.FragmentBottomNav
import com.luthtan.github_user_android.feature.home.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentHomeBinding, FavoriteViewModel>() {

    override val viewModel: FavoriteViewModel by viewModels()

    override val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter()
    }

    override fun onInitViews() {
        binding.lifecycleOwner = this
//        binding.listener = viewModel

        initRecyclerView()

        binding.toolbar.setOnSearchButtonClick {
            ((parentFragment as NavHostFragment).parentFragment as FragmentBottomNav).goToSearchFragment()
        }

        binding.toolbar.setTitleToolbar(getString(R.string.menu_favorite))
    }

    override fun onInitObservers() {

        viewModel.initData()

    }

    private fun initRecyclerView() {

        with(binding.rvUser) {
            adapter = homeAdapter
            setHasFixedSize(true)
        }

        viewModel.users.observe(this) {
            binding.tvDataEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            homeAdapter.setData(it)
        }

        homeAdapter.setOnClickListener(object : CustomOnClickListener<UserEntity>() {
            override fun onClickListener(data: UserEntity, index: Int) {
                ((parentFragment as NavHostFragment).parentFragment as FragmentBottomNav).goToDetailFragment(data.login ?: "")
            }

        })

        homeAdapter.setOnFavClicked { data, _ ->
            viewModel.deleteUser(data)
        }

    }
}