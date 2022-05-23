package com.example.rxvscoroutines.tests

import com.example.rxvscoroutines.Profiler
import com.example.rxvscoroutines.algorithm.BlurAlgorithm

abstract class Test {

    protected val profiler = Profiler()
    protected abstract val algorithm: BlurAlgorithm

    abstract fun run(onComplete: (Profiler.Results) -> Unit)

    companion object {

        const val RUNS_AMOUNT = 100
    }
}
