package com.luthtan.github_user_android.feature.home

import androidx.core.content.ContextCompat
import com.luthtan.github_user_android.R
import com.luthtan.github_user_android.base.BaseAdapter
import com.luthtan.github_user_android.base.util.GlideHelper
import com.luthtan.github_user_android.data.dtos.entity.UserEntity
import com.luthtan.github_user_android.data.dtos.response.UserResponse
import com.luthtan.github_user_android.databinding.ItemUserBinding

class HomeAdapter : BaseAdapter<ItemUserBinding, UserEntity>(
    inflate = ItemUserBinding::inflate
) {

    private var setOnFavClicked: (data: UserEntity, index: Int) -> Unit = { _, _ -> }

    fun setOnFavClicked(find: (data: UserEntity, index: Int) -> Unit) {
        setOnFavClicked = { data, index ->
            find(data, index)
        }
    }


    override fun bind(binding: ItemUserBinding, data: UserEntity?, index: Int) {
        data?.let { model ->
            with(binding) {
                GlideHelper.showThumbnail(
                    model.avatarUrl ?: "",
                    feedUserImage
                )
                feedUserName.text = model.login
                feedDescription.text = model.reposUrl
                dataLyt.setOnClickListener {
                    customOnClickListener?.onClickListener(data, index)
                }
                imgFav.setOnClickListener {
                    setOnFavClicked.invoke(data, index)
                }
                if (data.isSelected) {
                    imgFav.setColorFilter(ContextCompat.getColor(imgFav.context, R.color.yellow_A700))
                } else {
                    imgFav.setColorFilter(ContextCompat.getColor(imgFav.context, R.color.black))
                }
            }
        }
    }
}