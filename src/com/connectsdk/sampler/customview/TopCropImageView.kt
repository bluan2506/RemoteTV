package com.connectsdk.sampler.customview

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView


/**
 * ImageView to display top-crop scale of an image view.
 *
 * @author Chris Arriola
 */
@SuppressLint("AppCompatCustomView")
class TopCropImageView(context: Context?) : ImageView(context) {
    init {
        scaleType = ScaleType.MATRIX
    }

    override fun setFrame(l: Int, t: Int, r: Int, b: Int): Boolean {
        val matrix = imageMatrix

        val scale: Float
        val viewWidth = width - paddingLeft - paddingRight
        val viewHeight = height - paddingTop - paddingBottom
        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight

        scale = if (drawableWidth * viewHeight > drawableHeight * viewWidth) {
            viewHeight.toFloat() / drawableHeight.toFloat()
        } else {
            viewWidth.toFloat() / drawableWidth.toFloat()
        }

        matrix.setScale(scale, scale)
        imageMatrix = matrix

        return super.setFrame(l, t, r, b)
    }
}