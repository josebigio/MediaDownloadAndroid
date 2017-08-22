package com.josebigio.utils.RXUtils

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
* Created by josebigio on 8/21/17.
*/
class RetryWithDelay(private val maxRetries: Int, private val retryDelayMillis: Long): Function<Observable<Throwable>, ObservableSource<*>> {

    private var retryCount = 0

    override fun apply(attempts: Observable<Throwable>): ObservableSource<*> {
        return attempts.flatMap { t ->
            if (++retryCount < maxRetries) {
                Timber.d("Request failed. trying again. retry: $retryCount/$maxRetries")
                return@flatMap Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS)
            }
            Timber.d("Request failed. returning error")
            return@flatMap Observable.error<Throwable>(t)
        }
    }

}
