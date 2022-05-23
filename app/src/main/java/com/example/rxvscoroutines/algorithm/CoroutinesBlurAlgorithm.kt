package com.example.rxvscoroutines.algorithm

import android.content.Context
import android.graphics.Bitmap
import com.example.rxvscoroutines.Profiler
import com.example.rxvscoroutines.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoroutinesBlurAlgorithm(
    private val context: Context,
): BlurAlgorithm() {
    private val resources = context.resources

    override suspend fun blurred(track: Profiler.Track): Bitmap {
        track.saveStarted()
        val smallBmp = withContext(Dispatchers.Default) {
            track.saveResize1Started()
            val bmp = resize(resources, R.drawable.cat, 240, 200)
            track.saveResize1Finished()

            bmp
        }

        val blurredBmp = withContext(Dispatchers.Default) {
            track.saveBlurStarted()
            val bmp = blur(context, smallBmp, 20)
            track.saveBlurFinished()

            bmp
        }

        val originalSizedBmp = withContext(Dispatchers.Default) {
            track.saveResize2Started()
            val bmp = resize(blurredBmp, 1190, 900)

            track.saveResize2Finished()
            bmp
        }

        track.saveFinished()
        return originalSizedBmp
    }
}