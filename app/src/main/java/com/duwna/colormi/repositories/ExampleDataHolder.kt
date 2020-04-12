package com.duwna.colormi.repositories

import com.duwna.colormi.models.CourseItem
import kotlin.random.Random

val courseTitles = listOf(
    "Окрашивание",
    "Укладка"
)

val courseTypes = listOf(
    "Базовый",
    "Повышение квалификации",
    "Мастер-класс"
)

const val lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
        "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis " +
        "nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute " +
        "irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla " +
        "pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia " +
        "deserunt mollit anim id est laborum."

fun generateCourseItem(): CourseItem {
    val descriptionSize = (30..lorem.length / 4).random()
    val start = (0..lorem.length / 4).random()
    return CourseItem(
        id = (0..Int.MAX_VALUE).random().toString(),
        title = courseTitles[(courseTitles.indices).random()],
        type = courseTypes[(courseTypes.indices).random()],
        description = lorem.subSequence(start, start.plus(descriptionSize)).toString(),
        isBought = Random.nextBoolean(),
        isBookmarked = Random.nextBoolean(),
        price = if (Random.nextBoolean()) (200..5000).random() else 0
    )
}

