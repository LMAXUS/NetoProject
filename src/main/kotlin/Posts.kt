class Post(
    override val id: Int,
    override val date: Long,
    override val fromId: Int,
    override var text: String,
    override var deleted: Boolean = false,
    override val comments: MutableList<Comment> = mutableListOf(),
    var attachments: Array<Attachment> = emptyArray<Attachment>(), // Вложения разного рода
    val ownProp1: String = "Post",
    val ownProp2: String = "",
    val ownProp3: String = "",
): MessageInterface {
    override fun copy(message: MessageInterface, newId: Int ?, newDate: Long ?): MessageInterface {
        val postToCopy = message as Post
        return Post(
            newId ?: message.id,
            newDate ?: message.date,
            postToCopy.fromId,
            postToCopy.text,
            postToCopy.deleted,
            postToCopy.comments,
            postToCopy.attachments.copyOf(),
            postToCopy.ownProp1,
            postToCopy.ownProp2,
            postToCopy.ownProp3
        ) as MessageInterface
    }
}

sealed class Attachment(val type: String, val id: Int = 0)

data class Photo(
    val ownerId: Int = 0,
    val photo130: String = "",
    val photo604: String = ""
) : Attachment("photo")

data class Graffiti(
    val ownerId: Int = 0,
    val photo130: String = "",
    val photo604: String = ""
) : Attachment("graffiti")

data class App(
    val ownerId: Int = 0,
    val photo130: String = "",
    val photo604: String = ""
) : Attachment("app")

data class Page(
    val groupId: Int = 0,
    val title: String = ""
) : Attachment("page")

data class Event(
    val time: Long = 0,
    val memberStatus: Int = 0,
    val text: String = ""
) : Attachment("event")