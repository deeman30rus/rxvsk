package com.example.rxvscoroutines.algorithm

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.annotation.DrawableRes
import com.example.rxvscoroutines.Profiler
import kotlinx.coroutines.flow.Flow

abstract class BlurAlgorithm {

    abstract suspend fun blurred(track: Profiler.Track): Bitmap

    open fun blurredFlow(track: Profiler.Track): Flow<Bitmap> {
        TODO("implement me")
    }

    companion object {

        fun resize(
            resources: Resources,
            @DrawableRes src: Int,
            width: Int,
            height: Int
        ): Bitmap {
            val original = BitmapFactory.decodeResource(resources, src)

            return resize(original, width, height)
        }

        fun blur(
            context: Context,
            bmp: Bitmap,
            radius: Int
        ): Bitmap {
            var smallBitmap = bmp

            try {
                smallBitmap = RGB565toARGB888(bmp)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val bitmap = Bitmap.createBitmap(
                smallBitmap.width, smallBitmap.height,
                Bitmap.Config.ARGB_8888
            )

            val renderScript = RenderScript.create(context)
            val blurInput = Allocation.createFromBitmap(renderScript, smallBitmap)
            val blurOutput = Allocation.createFromBitmap(renderScript, bitmap)
            val blur = ScriptIntrinsicBlur.create(
                renderScript,
                Element.U8_4(renderScript)
            )

            blur.setInput(blurInput)
            blur.setRadius(radius.toFloat()) // radius must be 0 < r <= 25
            blur.forEach(blurOutput)
            blurOutput.copyTo(bitmap)
            renderScript.destroy()

            return bitmap
        }

        fun resize(bmp: Bitmap, width: Int, height: Int): Bitmap {
            return Bitmap.createScaledBitmap(bmp, height, width, true)
        }

        private fun RGB565toARGB888(img: Bitmap): Bitmap {
            val numPixels = img.width * img.height
            val pixels = IntArray(numPixels)

            //Get JPEG pixels.  Each int is the color values for one pixel.
            img.getPixels(pixels, 0, img.width, 0, 0, img.width, img.height)

            //Create a Bitmap of the appropriate format.
            val result = Bitmap.createBitmap(img.width, img.height, Bitmap.Config.ARGB_8888)

            //Set RGB pixels.
            result.setPixels(pixels, 0, result.width, 0, 0, result.width, result.height)
            return result
        }
    }
}