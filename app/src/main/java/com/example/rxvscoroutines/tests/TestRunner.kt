package com.example.rxvscoroutines.tests

import com.example.rxvscoroutines.Profiler

class TestRunner(
    private val factory: TestFactory
) {

    enum class Mode {
        SingleCoroutine,
        MultipleCoroutines,
        Flow,
        Rx
    }

    fun runTest(mode: Mode, onComplete: (Profiler.Results) -> Unit) {
        val test = factory.createTest(mode)

        test.run(onComplete)
    }
}