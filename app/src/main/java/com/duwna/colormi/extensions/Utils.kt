package com.duwna.colormi.extensions

import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import java.text.SimpleDateFormat
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

fun daysLeftToString(daysLeft: Int) =
    if (daysLeft in 11..14) "Осталось\n$daysLeft дней"
    else when (daysLeft % 10) {
        1 -> "Остался\n$daysLeft день"
        in 2..4 -> "Осталось\n$daysLeft дня"
        else -> "Осталось\n$daysLeft дней"
    }

fun Date.format(pattern: String = "HH:mm dd.MM"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

