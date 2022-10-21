package com.luthtan.github_user_android.feature.detail

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.luthtan.github_user_android.R
import com.luthtan.github_user_android.base.BaseFragment
import com.luthtan.github_user_android.base.util.CustomOnClickListener
import com.luthtan.github_user_android.base.util.EndlessRecyclerViewScrollListener
import com.luthtan.github_user_android.base.util.GlideHelper
import com.luthtan.github_user_android.data.dtos.entity.UserEntity
import com.luthtan.github_user_android.data.dtos.response.ReposResponse
import com.luthtan.github_user_android.databinding.FragmentDetailBinding
import com.luthtan.github_user_android.domain.subscriber.ResultState
import com.luthtan.github_user_android.feature.FragmentBottomNav
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>() {

    override val viewModel: DetailViewModel by viewModels()

    override val binding: FragmentDetailBinding by lazy {
        FragmentDetailBinding.inflate(layoutInflater)
    }

    private val rvAdapter: ReposAdapter by lazy {
        ReposAdapter()
    }

    private val args: DetailFragmentArgs by navArgs()

    override fun onInitViews() {
        binding.lifecycleOwner = this

        binding.toolbar.setTitleToolbar(getString(R.string.menu_favorite))

        binding.toolbar.setOnBackButtonClick {
            onBackPressed()
        }

        initRecyclerView()
    }

    override fun onInitObservers() {

        viewModel.initData(args.user)

        viewModel.detail.observe(this) {
            with(binding) {
                GlideHelper.showThumbnail(it.avatarUrl ?: "", imgProfile)
                tvProfileName.text = if (it.login != null) it.login.toString() else ""
                tvReposCount.text = if (it.publicRepos != null) it.publicRepos.toString() else ""
                tvFollowing.text = if (it.following != null) it.following.toString() else ""
                tvFollower.text = if (it.followers != null) it.followers.toString() else ""
            }
        }

        viewModel.loading.observe(this) {
            it.getContentIfNotHandled()?.let { state ->
                binding.progressLyt.visibility = if (state) {
                    binding.dataLyt.visibility = View.GONE
                    View.VISIBLE
                } else {
                    binding.dataLyt.visibility = View.VISIBLE
                    View.GONE
                }
            }
        }

    }

    private fun initRecyclerView() {
        with(binding.rvUser) {
            adapter = rvAdapter
            setHasFixedSize(true)
        }

        viewModel.repos.observe(this) {
            rvAdapter.setData(it)
        }

        rvAdapter.setOnClickListener(object : CustomOnClickListener<ReposResponse>() {
            override fun onClickListener(data: ReposResponse, index: Int) {
                val goToUrl = Intent(Intent.ACTION_VIEW, Uri.parse(data.htmlUrl))
                startActivity(goToUrl)
            }
        })
    }

}