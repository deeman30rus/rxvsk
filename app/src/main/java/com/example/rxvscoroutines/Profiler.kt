package com.example.rxvscoroutines

interface Clock {

    fun getCurrentTimeMillis(): Long
}

class Profiler: Clock {

    private val tracks = mutableListOf<Track>()

    fun renew() {
        tracks.clear()
    }

    fun startNewTrack(): Track {
        return Track(this, tracks.size).also { tracks.add(it) }
    }

    fun getResults() = Results(tracks)

    override fun getCurrentTimeMillis(): Long {
        return System.currentTimeMillis()
    }

    class Track(
        val clock: Clock,
        val id: Int
        ) {

        var started = -1L
            private set
        var resize1Started = -1L
            private set
        var resize1Finished = -1L
            private set
        var blurStarted = -1L
            private set
        var blurFinished = -1L
            private set
        var resize2Started = -1L
            private set
        var resize2Finished = -1L
            private set
        var finished = -1L
            private set

        val isValid: Boolean
            get() = started != -1L
                && resize1Started != -1L
                && resize1Finished != -1L
                && blurStarted != -1L
                && blurFinished != -1L
                && resize2Started != -1L
                && resize2Finished != -1L
                && finished != -1L

        fun saveStarted() {
            started = clock.getCurrentTimeMillis()
        }

        fun saveResize1Started() {
            resize1Started = clock.getCurrentTimeMillis()
        }

        fun saveResize1Finished() {
            resize1Finished = clock.getCurrentTimeMillis()
        }

        fun saveBlurStarted() {
            blurStarted = clock.getCurrentTimeMillis()
        }

        fun saveBlurFinished() {
            blurFinished = clock.getCurrentTimeMillis()
        }

        fun saveResize2Started() {
            resize2Started = clock.getCurrentTimeMillis()
        }

        fun saveResize2Finished() {
            resize2Finished = clock.getCurrentTimeMillis()
        }

        fun saveFinished() {
            finished = clock.getCurrentTimeMillis()
        }
    }

    class Results(val tracks: List<Track>)
}