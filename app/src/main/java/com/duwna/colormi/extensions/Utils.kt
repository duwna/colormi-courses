package com.duwna.colormi.extensions

inline fun <T> tryOrNull(block: () -> T): T? = try {
    block()
} catch (t: Throwable) {
    null
}

fun String.truncate(value: Int): String = if (value < this.trim().length) {
    this.substring(0, value).trim() + "..."
} else {
    this
}