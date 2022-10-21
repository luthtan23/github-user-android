package com.luthtan.github_user_android.domain.baseusecase.rxjava

import com.luthtan.github_user_android.domain.PostExecutionThread
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber


abstract class FlowableUseCase<T : Any, in Params>(private val postExecutionThread: PostExecutionThread) {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * Builds an [Flowable] which will be used when executing the current [FlowableUseCase].
     */
    abstract fun build(params: Params): Flowable<T>

    fun executable(subscriber: DisposableSubscriber<T>, params: Params) {
        val disposable = build(params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.getScheduler())
            .subscribeWith(subscriber)
        addDisposable(disposable)
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    fun dispose() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    private fun addDisposable(disposable: Disposable?) {
        if (compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }

        compositeDisposable.add(disposable!!)
    }
}