package com.luthtan.github_user_android.feature.detail

import com.luthtan.github_user_android.base.BaseAdapter
import com.luthtan.github_user_android.data.dtos.response.ReposResponse
import com.luthtan.github_user_android.databinding.ItemReposBinding

class ReposAdapter : BaseAdapter<ItemReposBinding, ReposResponse>(
    inflate = ItemReposBinding::inflate
) {

    override fun bind(binding: ItemReposBinding, data: ReposResponse?, index: Int) {
        data?.let { model ->
            with(binding) {
                tvName.text = model.name
                tvUrl.text = model.url
                reposLyt.setOnClickListener {
                    customOnClickListener?.onClickListener(data, index)
                }
            }
        }
    }
}