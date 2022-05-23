package com.example.rxvscoroutines.tests

import android.content.Context
import com.example.rxvscoroutines.Profiler
import com.example.rxvscoroutines.algorithm.BlurAlgorithm
import com.example.rxvscoroutines.algorithm.CoroutinesBlurAlgorithm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MultiCoroutinesTest(
    context: Context,
    private val coroutineScope: CoroutineScope,
): Test() {

    override val algorithm: BlurAlgorithm = CoroutinesBlurAlgorithm(context)

    override fun run(onComplete: (Profiler.Results) -> Unit) {
        coroutineScope.launch {
            profiler.renew()

            repeat(RUNS_AMOUNT) {
                coroutineScope {
                    val t = profiler.startNewTrack()

                    algorithm.blurred(t)
                }
            }

            onComplete(profiler.getResults())
        }
    }
}
