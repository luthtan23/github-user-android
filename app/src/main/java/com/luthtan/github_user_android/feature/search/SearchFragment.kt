package com.luthtan.github_user_android.feature.search

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.luthtan.github_user_android.base.BaseFragment
import com.luthtan.github_user_android.base.util.CustomOnClickListener
import com.luthtan.github_user_android.base.util.EndlessRecyclerViewScrollListener
import com.luthtan.github_user_android.data.dtos.entity.UserEntity
import com.luthtan.github_user_android.databinding.FragmentSearchBinding
import com.luthtan.github_user_android.domain.subscriber.ResultState
import com.luthtan.github_user_android.feature.FragmentBottomNav
import com.luthtan.github_user_android.feature.common.cutom_ui.SearchBarWithVoice
import com.luthtan.github_user_android.feature.home.HomeAdapter
import com.luthtan.github_user_android.feature.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()

    override val binding: FragmentSearchBinding by lazy {
        FragmentSearchBinding.inflate(layoutInflater)
    }

    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter()
    }

    private lateinit var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener

    override fun onInitViews() {
        binding.lifecycleOwner = this

        binding.toolbar.registerSearchBar(this, object : SearchBarWithVoice.OnSearchListener {
            override fun onSearch(query: String) {
                endlessRecyclerViewScrollListener.resetState()
                viewModel.pages = 1
                viewModel.userTemp.clear()
                viewModel.searchUser(query)
            }
        })

        initRecyclerView()
    }

    override fun onInitObservers() {

        viewModel.localData()

        viewModel.empty.observe(this) {
            it.getContentIfNotHandled()?.let {
                binding.tvDataEmpty.visibility = View.VISIBLE
            }
        }
    }

    private fun initRecyclerView() {

        endlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(binding.rvUser.layoutManager!!) {
            override fun onLoadMore(pageNext: Int) {
                if (pageNext == viewModel.userTemp.size && !homeAdapter.isLoadMore) {
                    viewModel.pages += 1
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
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ResultState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvDataEmpty.visibility = View.GONE
                    homeAdapter.setData(it.data)
                }
                is ResultState.Error -> {

                }
            }
        }

        homeAdapter.setOnClickListener(object : CustomOnClickListener<UserEntity>() {
            override fun onClickListener(data: UserEntity, index: Int) {
                findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(data.login ?: ""))
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