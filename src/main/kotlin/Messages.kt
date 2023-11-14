import java.lang.RuntimeException

class MessageNotFoundException(message: String): RuntimeException(message)
class CommentNotFoundException(message: String): RuntimeException(message)
//class InvalidReportReasonException(message: String): RuntimeException(message)

data class Comment(
    val id: Int, // Идентификатор комментария
    val date: Long, // Дата публикации в формате unixtime
    val messageId: Int, //Идентификатор сообщения, к которому относится комментарий
    val fromId: Int, // Идентификатор автора сообщения
    var text: String, // Текст сообщения
    val replyToComment: Int ? = null, //Идентификатор комментария, к которому относится комментарий
    var deleted: Boolean = false, // Сообщение удалено
)

interface MessageInterface {
    val id: Int // Идентификатор сообщения
    val date: Long // Дата публикации в формате unixtime
    val fromId: Int // Идентификатор автора сообщения
    var text: String // Текст сообщения
    var deleted: Boolean // Сообщение удалено
    val comments: MutableList<Comment>

    //Функция возвращает копию объекта сообщения,
    // при необходимости (если переданы параметры) меняя id и дату создания
    fun copy(message: MessageInterface, newId: Int ? = null, newDate: Long ? = null): MessageInterface
}

class MessageService <T: MessageInterface> {

    private val messages: MutableList<T> = mutableListOf()

    private fun messageExistsException(id: Int){
        try{
            messages[id]
        } catch (e: IndexOutOfBoundsException){
            throw MessageNotFoundException("Попытка обратиться к сообщению с несуществующим id = $id")
        }
    }

    private fun messageDeletedException(id: Int){
        if(messages[id].deleted == true) throw MessageNotFoundException("Попытка обратиться к удаленному сообщению")
    }

    fun getById(id: Int): T{
        messageExistsException(id)
        return messages[id]
    }

    fun get(): MutableList<T>{
        return messages.toMutableList()
    }

    fun add(message: T): Int{
        val newId = messages.size
        messages.add(newId, message.copy(message, newId, System.currentTimeMillis()) as T)
        return newId
    }

    fun edit(id: Int, text: String): Boolean{
        messageExistsException(id)
        messages[id].text = text
        return true
    }

    fun delete(id: Int): Boolean{
        messageExistsException(id)
        messages[id].deleted = true
        return messages[id].deleted
    }

    fun restore(id: Int): Boolean{
        messageExistsException(id)
        messages[id].deleted = false
        return messages[id].deleted
    }

    private fun commentExistsException(messageId: Int, commentId: Int){
        messageExistsException(messageId)
        try{
            messages[messageId].comments[commentId]
        } catch (e: IndexOutOfBoundsException){
            throw CommentNotFoundException("Попытка обратиться к комментарию с несуществующим id = $commentId")
        }
    }

    fun getCommentById(messageId: Int, commentId: Int): Comment{
        messageExistsException(messageId)
        messageDeletedException(messageId)
        commentExistsException(messageId, commentId)
        return messages[messageId].comments[commentId]
    }

    fun getComments(messageId: Int): MutableList<Comment>{
        messageExistsException(messageId)
        messageDeletedException(messageId)
        return messages[messageId].comments.toMutableList()
    }

    fun addComment(messageId: Int, fromId: Int, text: String, replyToComment: Int ? = null): Int{
        messageExistsException(messageId)
        messageDeletedException(messageId)
        val newId = messages[messageId].comments.size
        messages[messageId].comments.add(
            Comment(
                newId,
                System.currentTimeMillis(),
                messageId,
                fromId,
                text,
                replyToComment
            )
        )
        return newId
    }

    fun editComment(messageId: Int, commentId: Int, text: String): Boolean{
        messageExistsException(messageId)
        messageDeletedException(messageId)
        commentExistsException(messageId, commentId)
        messages[messageId].comments[commentId].text = text
        return true
    }

    fun deleteComment(messageId: Int, commentId: Int): Boolean{
        messageExistsException(messageId)
        messageDeletedException(messageId)
        commentExistsException(messageId, commentId)
        messages[messageId].comments[commentId].deleted = true
        return messages[messageId].comments[commentId].deleted
    }

    fun restoreComment(messageId: Int, commentId: Int): Boolean{
        messageExistsException(messageId)
        messageDeletedException(messageId)
        commentExistsException(messageId, commentId)
        messages[messageId].comments[commentId].deleted = false
        return messages[messageId].comments[commentId].deleted
    }

}