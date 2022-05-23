package com.example.rxvscoroutines.algorithm

import android.content.Context
import android.graphics.Bitmap
import com.example.rxvscoroutines.Profiler
import com.example.rxvscoroutines.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class FlowBlurAlgorithm(
    private val context: Context
) : BlurAlgorithm() {

    private val resources = context.resources

    override suspend fun blurred(track: Profiler.Track): Bitmap {
        error("unimplemented")
    }

    override fun blurredFlow(track: Profiler.Track): Flow<Bitmap> {
        track.saveStarted()
        return flow {
            track.saveResize1Started()
            val bmp = resize(resources, R.drawable.cat, 240, 200)
            track.saveResize1Finished()

            emit(bmp)
        }.map {
            track.saveBlurStarted()
            val bmp = blur(context, it, 20)
            track.saveBlurFinished()

            bmp
        }.map {
            track.saveResize2Started()
            val bmp = resize(it, 1190, 900)
            track.saveResize2Finished()

            bmp
        }.flowOn(Dispatchers.Default)
    }
}