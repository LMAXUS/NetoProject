data class Comment(
    override val id: Int,
    override val date: Long,
    override val fromId: Int,
    override var text: String,
    override var deleted: Boolean = false,
): MessageInterface {
    override fun copy(newId: Int ?, newDate: Long ?): MessageInterface {
        return Comment(
            newId ?: this.id,
            newDate ?: this.date,
            this.fromId,
            this.text,
            this.deleted,
        ) as MessageInterface
    }
}