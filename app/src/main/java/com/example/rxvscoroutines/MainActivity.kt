package com.example.rxvscoroutines

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.rxvscoroutines.tests.TestFactory
import com.example.rxvscoroutines.tests.TestRunner

class MainActivity : AppCompatActivity() {

    private val resultsView: TextView by lazy { findViewById(R.id.results) }

    private val trackAnalyzer = TrackAnalyzer()

    private val testFactory = TestFactory(
        activityContext = this,
        activityScope = lifecycleScope,
    )

    private val testRunner = TestRunner(testFactory)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.run_single).setOnClickListener {
            testRunner.runTest(TestRunner.Mode.SingleCoroutine, ::showResults)
        }

        findViewById<View>(R.id.run_multi).setOnClickListener {
            testRunner.runTest(TestRunner.Mode.MultipleCoroutines, ::showResults)
        }

        findViewById<View>(R.id.run_flow).setOnClickListener {
            testRunner.runTest(TestRunner.Mode.Flow, ::showResults)
        }

        findViewById<View>(R.id.run_rx).setOnClickListener {
            testRunner.runTest(TestRunner.Mode.Rx, ::showResults)
        }

        resultsView.text = ""
    }

    private fun showResults(results: Profiler.Results) {
        resultsView.text = trackAnalyzer.process(results)
    }
}