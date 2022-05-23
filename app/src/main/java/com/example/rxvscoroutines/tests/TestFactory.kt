package com.example.rxvscoroutines.tests

import android.content.Context
import kotlinx.coroutines.CoroutineScope

class TestFactory(
    private val activityContext: Context,
    private val activityScope: CoroutineScope,
) {

    fun createTest(mode: TestRunner.Mode): Test {
        return when(mode) {
            TestRunner.Mode.SingleCoroutine -> SingleCoroutineTest(activityContext, activityScope)
            TestRunner.Mode.MultipleCoroutines -> MultiCoroutinesTest(activityContext, activityScope)
            TestRunner.Mode.Flow -> FlowTest(activityScope, activityContext)
            TestRunner.Mode.Rx -> RxTest(activityContext)
        }
    }
}