package com.example.rxvscoroutines.tests

import android.content.Context
import com.example.rxvscoroutines.Profiler
import com.example.rxvscoroutines.algorithm.BlurAlgorithm
import com.example.rxvscoroutines.algorithm.FlowBlurAlgorithm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FlowTest(
    private val activityScope: CoroutineScope,
    private val context: Context,
): Test() {

    override val algorithm: BlurAlgorithm
        get() = FlowBlurAlgorithm(context)

    override fun run(onComplete: (Profiler.Results) -> Unit) {
        profiler.renew()

        activityScope.launch {
            repeat(RUNS_AMOUNT) {
                val track = profiler.startNewTrack()

                algorithm
                    .blurredFlow(track)
                    .collect {
                        track.saveFinished()
                    }
            }

            onComplete(profiler.getResults())
        }
    }
}