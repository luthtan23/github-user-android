package com.luthtan.github_user_android.data.mapper

import com.luthtan.github_user_android.data.dtos.entity.UserEntity
import com.luthtan.github_user_android.data.dtos.response.UserResponse

class UserMapper : Mapper<UserResponse, UserEntity>() {
    override fun apply(t: UserResponse): UserEntity {
        return t.let { model ->
            UserEntity(
                id = model.id,
                gistsUrl = model.gistsUrl,
                reposUrl = model.reposUrl,
                followingUrl = model.followingUrl,
                starredUrl = model.starredUrl,
                login = model.login,
                followersUrl = model.followersUrl,
                type = model.type,
                url = model.url,
                subscriptionsUrl = model.subscriptionsUrl,
                receivedEventsUrl = model.receivedEventsUrl,
                avatarUrl = model.avatarUrl,
                eventsUrl = model.eventsUrl,
                htmlUrl = model.htmlUrl,
                siteAdmin = model.siteAdmin,
                gravatarId = model.gravatarId,
                nodeId = model.nodeId,
                organizationsUrl = model.organizationsUrl
            )
        }
    }
}