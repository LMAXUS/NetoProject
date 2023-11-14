class Note(
    override val id: Int,
    override val date: Long,
    override val fromId: Int,
    val title: String,
    override var text: String,
    override var deleted: Boolean = false,
    override val comments: MutableList<Comment> = mutableListOf(),
): MessageInterface {
    override fun copy(message: MessageInterface, newId: Int ?, newDate: Long ?): MessageInterface {
        val postToCopy = message as Note
        return Note(
            newId ?: message.id,
            newDate ?: message.date,
            postToCopy.fromId,
            postToCopy.title,
            postToCopy.text,
            postToCopy.deleted,
            postToCopy.comments
        ) as MessageInterface
    }
}