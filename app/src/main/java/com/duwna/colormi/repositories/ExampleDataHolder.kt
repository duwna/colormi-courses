package com.duwna.colormi.repositories

import com.duwna.colormi.models.SearchCourseItem
import com.duwna.colormi.models.database.Course
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


val imgUrls = listOf(
    "https://ru.haip.info/wp-content/uploads/2020/04/28/bad-haircut-730x400.jpg",
    "https://hairgood.ru/images/dyn_pic/normal/chto-doljen-znat-nachinayuschiy-master-parikmaher.jpg",
    "https://avatars.mds.yandex.net/get-pdb/2368538/20bb8c51-935a-4c5a-b6cd-c324d2029ac9/s1200?webp=false",
    "http://the-baby.ru/wp-content/uploads/2012/05/modnaya-professia-dlya-devushki-parikmaher-1-638x368.jpg",
    "https://intercollege.su/netcat_files/212/49/h_fec0cf5a0836a72b7227544aaaf02453"
)
const val lorem =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Posuere lorem ipsum dolor sit amet consectetur adipiscing elit. Vel turpis nunc eget lorem dolor sed viverra ipsum. Fames ac turpis egestas integer eget aliquet. Libero justo laoreet sit amet cursus sit amet. Diam donec adipiscing tristique risus. Est pellentesque elit ullamcorper dignissim cras tincidunt lobortis feugiat. Nunc pulvinar sapien et ligula ullamcorper malesuada proin libero nunc. Tortor pretium viverra suspendisse potenti nullam ac tortor vitae. Pharetra vel turpis nunc eget lorem dolor. Tincidunt ornare massa eget egestas purus viverra accumsan in nisl. Suscipit tellus mauris a diam maecenas sed enim. Etiam sit amet nisl purus in mollis nunc. Sed egestas egestas fringilla phasellus faucibus. Ut ornare lectus sit amet est placerat. Pellentesque habitant morbi tristique senectus et netus et malesuada fames. Consectetur adipiscing elit pellentesque habitant morbi tristique senectus et netus. Malesuada bibendum arcu vitae elementum curabitur vitae nunc sed velit. Dictumst vestibulum rhoncus est pellentesque elit ullamcorper. Dictum sit amet justo donec.\n" +
            "\n" +
            "Nullam ac tortor vitae purus faucibus. Lorem ipsum dolor sit amet consectetur adipiscing. Nunc sed blandit libero volutpat sed cras ornare. Semper viverra nam libero justo laoreet sit amet. Lectus arcu bibendum at varius vel pharetra. Morbi tristique senectus et netus et malesuada. Risus at ultrices mi tempus imperdiet nulla malesuada. Sed faucibus turpis in eu mi bibendum neque egestas congue. Commodo quis imperdiet massa tincidunt nunc. Elementum facilisis leo vel fringilla. Aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant. Habitant morbi tristique senectus et netus et malesuada fames ac. Fringilla est ullamcorper eget nulla facilisi. Varius quam quisque id diam vel quam elementum. Quam quisque id diam vel quam elementum. Vitae semper quis lectus nulla at volutpat diam ut venenatis.\n" +
            "\n" +
            "Massa sed elementum tempus egestas. Nunc congue nisi vitae suscipit tellus mauris a diam. Viverra ipsum nunc aliquet bibendum enim facilisis gravida neque. Nulla pellentesque dignissim enim sit amet venenatis urna. Ultrices in iaculis nunc sed augue. Arcu dui vivamus arcu felis bibendum ut tristique et egestas. A iaculis at erat pellentesque adipiscing commodo elit at imperdiet. Sed vulputate mi sit amet mauris commodo quis imperdiet massa. Duis tristique sollicitudin nibh sit amet commodo nulla. Viverra nam libero justo laoreet sit amet cursus sit. Purus sit amet volutpat consequat. Luctus accumsan tortor posuere ac ut consequat semper viverra nam. Massa sapien faucibus et molestie ac feugiat sed lectus vestibulum. Varius duis at consectetur lorem donec massa. Rhoncus mattis rhoncus urna neque viverra justo nec ultrices."

fun generateCourseItem(): SearchCourseItem {
    val descriptionSize = (100..lorem.length / 4).random()
    val start = (0..lorem.length / 4).random()
    return SearchCourseItem(
        idCourse = (0..Int.MAX_VALUE).random().toString(),
        title = courseTitles[(courseTitles.indices).random()],
        type = courseTypes[(courseTypes.indices).random()],
        description = lorem.subSequence(start, start.plus(descriptionSize)).toString(),
        isBought = Random.nextBoolean(),
        isBookmarked = Random.nextBoolean(),
        price = if (Random.nextBoolean()) (200..5000).random() else 0
    )
}

fun generateCourse(): Course {
    val descriptionSize = (100..lorem.length / 4).random()
    val start = (0..lorem.length / 4).random()
    return Course(
        idCourse = null,
        title = courseTitles[(courseTitles.indices).random()],
        type = courseTypes[(courseTypes.indices).random()],
        description = lorem.subSequence(start, start.plus(descriptionSize)).toString(),
        price = if (Random.nextBoolean()) (200..5000).random() else 0,
        imgUrl = imgUrls[imgUrls.indices.random()],
        rating = (3 + Math.random() * 2).toFloat()
    )
}

