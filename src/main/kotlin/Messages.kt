import java.lang.RuntimeException

class MessageNotFoundException(message: String): RuntimeException(message)
class MessageDeletedException(message: String): RuntimeException(message)

interface MessageInterface {
    val id: Int // Идентификатор сообщения
    val date: Long // Дата публикации в формате unixtime
    val fromId: Int // Идентификатор автора сообщения
    var text: String // Текст сообщения
    var deleted: Boolean // Сообщение удалено

    //Функция возвращает копию объекта сообщения,
    // при необходимости (если переданы параметры) меняя id и дату создания
    fun copy(newId: Int ? = null, newDate: Long ? = null): MessageInterface
}

open class MessageService <T: MessageInterface> (prototype: MessageService <T> ? = null, serviceName: String? = null){

    companion object{
        inline fun <reified T: MessageInterface> init(prototype: MessageService <T> ? = null): MessageService<T> {
            return MessageService<T>(prototype, T::class.simpleName)
        }
    }

    private var serviceName: String = "MessageInterface"
    private var messages: MutableList<T> = mutableListOf()
    private var messageCounter: Int = 0

    init {
        if(serviceName != null) this.serviceName = serviceName
        if(prototype != null) {
            prototype.get().forEach{
                messages.add(it.copy() as T)
            }
            messageCounter = prototype.last()
        }
    }

    private fun message(id: Int): T{
        val message = messages.find { it.id == id }
        return message ?: throw MessageNotFoundException("Ошибка сервиса \"$serviceName\": попытка обратиться к сообщению с несуществующим id = $id")
    }

    fun last(): Int{
        return messageCounter
    }

    fun getById(id: Int): T{
        return message(id)
    }

    fun get(): MutableList<T>{
        return messages.toMutableList()
    }

    fun add(message: T): Int{
        val newId = messageCounter++
        messages.add(message.copy(newId, System.currentTimeMillis()) as T)
        return newId
    }

    fun edit(id: Int, text: String): Boolean{
        val message = message(id)
        if(message.deleted) throw MessageDeletedException("Ошибка сервиса \"$serviceName\": попытка редактирования удаленного сообщения с id = $id")
        message(id).text = text
        return true
    }

    fun delete(id: Int): Boolean{
        val message = message(id)
        message.deleted = true
        return message.deleted
    }

    fun restore(id: Int): Boolean{
        val message = message(id)
        message.deleted = false
        return message.deleted
    }
}

