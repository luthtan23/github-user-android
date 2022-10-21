package com.luthtan.github_user_android.domain

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler

/**
 * Created by Abraham Lay on 2019-12-28.
 */

class AndroidUIThread : PostExecutionThread {
    override fun getScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}