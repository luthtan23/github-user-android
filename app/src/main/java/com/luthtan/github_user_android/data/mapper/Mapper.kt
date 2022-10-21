package com.luthtan.github_user_android.data.mapper

import io.reactivex.rxjava3.functions.Function

/**
 * Created by Abraham Lay on 14/06/20.
 */
abstract class Mapper<FROM: Any, TO: Any> : Function<FROM, TO> {
    @Throws(Exception::class)
    fun apply(fromList: Collection<FROM>): Collection<TO> {
        val result: MutableCollection<TO> = ArrayList()
        for (from in fromList) {
            val item = apply(from)
            result.add(item)
        }
        return result
    }
}