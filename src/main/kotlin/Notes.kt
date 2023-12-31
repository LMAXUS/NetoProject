class Note(
    override val id: Int,
    override val date: Long,
    override val fromId: Int,
    val title: String,
    override var text: String,
    override var deleted: Boolean = false,
    val comments: MessageService<Comment> = MessageService.init<Comment>(),
): MessageInterface {


    override fun copy(newId: Int ?, newDate: Long ?): MessageInterface {
        return Note(
            newId ?: this.id,
            newDate ?: this.date,
            this.fromId,
            this.title,
            this.text,
            this.deleted,
            MessageService.init<Comment>(this.comments),
        ) as MessageInterface
    }
}