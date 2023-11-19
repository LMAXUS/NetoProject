class Post(
    override val id: Int,
    override val date: Long,
    override val fromId: Int,
    override var text: String,
    override var deleted: Boolean = false,
    val comments: MessageService<Comment> = MessageService.init<Comment>(),
    var attachments: Array<Attachment> = emptyArray<Attachment>(), // Вложения разного рода
    val ownProp1: String = "Post",
    val ownProp2: String = "",
    val ownProp3: String = "",
): MessageInterface {

    override fun copy(newId: Int ?, newDate: Long ?): MessageInterface {
        return Post(
            newId ?: this.id,
            newDate ?: this.date,
            this.fromId,
            this.text,
            this.deleted,
            MessageService.init<Comment>(this.comments),
            this.attachments.copyOf(),
            this.ownProp1,
            this.ownProp2,
            this.ownProp3
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