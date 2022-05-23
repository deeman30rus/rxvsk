package com.example.rxvscoroutines.algorithm

import android.content.Context
import android.graphics.Bitmap
import com.example.rxvscoroutines.Profiler
import com.example.rxvscoroutines.R
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class RxBlurAlgorithm(
    private val context: Context
): BlurAlgorithm() {

    private val resources = context.resources

    override suspend fun blurred(track: Profiler.Track): Bitmap {
        TODO("Not yet implemented")
    }

    fun blurredSingle(track: Profiler.Track): Observable<Bitmap> {
        return Observable.fromCallable {
            track.saveResize1Started()
            val bmp = resize(resources, R.drawable.cat, 240, 200)
            track.saveResize1Finished()

            bmp
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
        }.observeOn(Schedulers.computation())
            .doOnSubscribe {
                track.saveStarted()
            }
            .doOnComplete {
                track.saveFinished()
            }
    }
}