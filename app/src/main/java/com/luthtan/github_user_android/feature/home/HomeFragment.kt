package com.luthtan.github_user_android.feature.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.luthtan.github_user_android.base.BaseFragment
import com.luthtan.github_user_android.base.util.CustomOnClickListener
import com.luthtan.github_user_android.base.util.EndlessRecyclerViewScrollListener
import com.luthtan.github_user_android.data.dtos.entity.UserEntity
import com.luthtan.github_user_android.databinding.FragmentHomeBinding
import com.luthtan.github_user_android.domain.subscriber.ResultState
import com.luthtan.github_user_android.feature.FragmentBottomNav
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()

    override val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter()
    }

    private lateinit var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener

    override fun onInitViews() {
        binding.lifecycleOwner = this

        binding.toolbar.setOnSearchButtonClick {
            ((parentFragment as NavHostFragment).parentFragment as FragmentBottomNav).goToSearchFragment()
        }

        initRecyclerView()
    }

    override fun onInitObservers() {

        viewModel.localData()

        viewModel.initData()

        viewModel.empty.observe(this) {
            it.getContentIfNotHandled()?.let {
                binding.tvDataEmpty.visibility = View.VISIBLE
            }
        }
    }

    private fun initRecyclerView() {

        endlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(binding.rvUser.layoutManager!!) {
            override fun onLoadMore(pageNext: Int) {
                if (pageNext == viewModel.pages && !homeAdapter.isLoadMore) {
                    homeAdapter.initLoadMore(true)
                    viewModel.initData()
                }
            }
        }
        with(binding.rvUser) {
            adapter = homeAdapter
            setHasFixedSize(true)
        }

        viewModel.users.observe(this) {
            when(it) {
                is ResultState.Loading -> {
                    binding.progressHome.visibility = View.VISIBLE
                }
                is ResultState.Success -> {
                    binding.progressHome.visibility = View.GONE
                    if (it.data.isEmpty()) {
                        binding.tvDataEmpty.visibility = View.VISIBLE
                    } else {
                        binding.tvDataEmpty.visibility = View.GONE
                    }
                    homeAdapter.setData(it.data)
                }
                is ResultState.Error -> {

                }
            }
        }

        homeAdapter.setOnClickListener(object : CustomOnClickListener<UserEntity>() {
            override fun onClickListener(data: UserEntity, index: Int) {
                ((parentFragment as NavHostFragment).parentFragment as FragmentBottomNav).goToDetailFragment(data.login ?: "")
            }
        })

        homeAdapter.setOnFavClicked { data, index ->
            if (data.isSelected) {
                viewModel.deleteUser(data, index)
            } else {
                viewModel.insertUser(data, index)
            }
        }

        binding.rvUser.addOnScrollListener(endlessRecyclerViewScrollListener)
    }

    override fun onResume() {
        super.onResume()

        viewModel.updateData()

    }
}