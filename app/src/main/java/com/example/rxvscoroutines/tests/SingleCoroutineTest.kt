package com.example.rxvscoroutines.tests

import android.content.Context
import com.example.rxvscoroutines.Profiler
import com.example.rxvscoroutines.algorithm.CoroutinesBlurAlgorithm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SingleCoroutineTest(
    context: Context,
    private val coroutineScope: CoroutineScope,
): Test() {

    override val algorithm = CoroutinesBlurAlgorithm(context)

    override fun run(onComplete: (Profiler.Results) -> Unit) {
        coroutineScope.launch {
            profiler.renew()

            runInternal()

            onComplete(profiler.getResults())
        }
    }

    private suspend fun runInternal() {
        repeat(RUNS_AMOUNT) {
            val track = profiler.startNewTrack()

            algorithm.blurred(track)
        }
    }
}