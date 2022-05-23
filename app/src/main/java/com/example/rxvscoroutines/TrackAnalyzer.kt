package com.example.rxvscoroutines

class TrackAnalyzer {

    fun process(results: Profiler.Results): String {
        val total = results.tracks.sumOf { it.finished - it.started }

        val resize1Duration = results.tracks.sumOf { it.resize1Finished - it.resize1Started }
        val blurDuration = results.tracks.sumOf { it.blurFinished - it.blurStarted }
        val resize2Duration = results.tracks.sumOf { it.resize2Finished - it.resize2Started }

        val debounceTime = total - (resize1Duration + blurDuration + resize2Duration)

        val effectiveness = (((total - debounceTime.toFloat()) / total) * 100).toInt()

        return """
            tests run: ${results.tracks.size}
            total time: ${Duration(total)}
            debounce time: ${Duration(debounceTime)}
            effectiveness: $effectiveness %
        """.trimIndent()
    }
}

private inline class Duration(val millisTotal: Long) {

    val millis: Long
        get() = millisTotal % 1000

    val seconds: Long
        get() = (millisTotal / 1000) % 60

    val minutes: Long
        get() = (millisTotal / 1000) / 60 % 60

    val hours: Long
        get() = (millisTotal / 1000) / 60 / 60 % 24

    override fun toString(): String {
        return "$hours : $minutes : $seconds.$millis"
    }
}