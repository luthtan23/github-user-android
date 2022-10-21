package com.luthtan.github_user_android.domain

import io.reactivex.rxjava3.core.Scheduler

interface PostExecutionThread {
    fun getScheduler(): Scheduler
}