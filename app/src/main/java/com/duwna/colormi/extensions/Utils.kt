package com.duwna.colormi.extensions

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import java.util.*
import java.util.concurrent.TimeUnit

inline fun <T> tryOrNull(block: () -> T): T? = try {
    block()
} catch (t: Throwable) {
    null
}

fun View.circularShow() {
    ViewAnimationUtils
        .createCircularReveal(this, width / 2, height / 2, 0f, maxOf(width, height) / 2f)
        .apply {
            duration = 500
            doOnStart { isVisible = true }
        }.start()
}

fun View.circularHide() {
    ViewAnimationUtils
        .createCircularReveal(this, width / 2, height / 2, maxOf(width, height) / 2f, 0f)
        .apply {
            duration = 500
            doOnEnd { isVisible = false }
        }.start()
}

fun getDayDifference(date1: Date, date2: Date): Int {
    val diff = date2.time - date1.time
    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt()
}