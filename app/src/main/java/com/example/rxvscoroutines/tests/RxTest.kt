package com.example.rxvscoroutines.tests

import android.content.Context
import android.graphics.Bitmap
import com.example.rxvscoroutines.Profiler
import com.example.rxvscoroutines.algorithm.BlurAlgorithm
import com.example.rxvscoroutines.algorithm.RxBlurAlgorithm
import io.reactivex.Observable

class RxTest(
    context: Context,
): Test() {

    override val algorithm: BlurAlgorithm = RxBlurAlgorithm(context)

    override fun run(onComplete: (Profiler.Results) -> Unit) {
        profiler.renew()

        val a = algorithm as RxBlurAlgorithm
        val observables = mutableListOf<Observable<Bitmap>>()

        for (i in 0 until RUNS_AMOUNT) {
            val track = profiler.startNewTrack()
            observables.add(a.blurredSingle(track))
        }

        Observable.merge(observables)
            .doOnComplete {
                val results = profiler.getResults()

                onComplete(results)
            }.subscribe()
    }
}