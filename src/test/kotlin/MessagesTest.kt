import org.junit.Assert.*
import org.junit.Test

class MessagesTest {

    //Posts

    @Test
    fun addAndGetPosts() {
        val service = MessageService.init<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        service.add(Post(0, 0, 1, "Пост №2"))
        service.add(Post(0, 0, 1, "Пост №3"))
        assertEquals(0, service.getById(0).id)
        assertEquals("Пост №1", service.getById(0).text)
        assertEquals(1, service.getById(1).id)
        assertEquals("Пост №2", service.getById(1).text)
        assertEquals(2, service.getById(2).id)
        assertEquals("Пост №3", service.getById(2).text)
    }

    @Test(expected = MessageNotFoundException::class)
    fun getPostException() {
        val service = MessageService.init<Post>()
        service.getById(0)
    }

    @Test
    fun deleteRestorePost() {
        val service = MessageService.init<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        assertEquals(false, service.getById(0).deleted)
        assertEquals(true, service.delete(0))
        assertEquals(false, service.restore(0))
    }

    @Test(expected = MessageNotFoundException::class)
    fun deletePostException() {
        val service = MessageService.init<Post>()
        service.delete(0)
    }

    @Test(expected = MessageNotFoundException::class)
    fun restorePostException() {
        val service = MessageService.init<Post>()
        service.restore(0)
    }

    @Test
    fun editPost() {
        val service = MessageService.init<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        service.edit(0, "Пост №1 Изменен")
        assertEquals("Пост №1 Изменен", service.getById(0).text)
    }

    @Test(expected = MessageNotFoundException::class)
    fun editPostException() {
        val service = MessageService.init<Post>()
        service.edit(0, "Меняем текст несуществующего поста")
    }

    @Test(expected = MessageDeletedException::class)
    fun editDeletedPostException() {
        val service = MessageService.init<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        service.delete(0)
        service.edit(0, "Меняем текст удаленного поста")
    }

    @Test
    fun addAndGetPostComments() {
        val service = MessageService.init<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        service.getById(0).comments.add(Comment(0,0,0, "Пост №1, Комментарий №1"))
        service.getById(0).comments.add(Comment(0,0,0, "Пост №1, Комментарий №2"))
        assertEquals(0, service.getById(0).comments.getById(0).id)
        assertEquals("Пост №1, Комментарий №1", service.getById(0).comments.getById(0).text)
        assertEquals(1, service.getById(0).comments.getById(1).id)
        assertEquals("Пост №1, Комментарий №2", service.getById(0).comments.getById(1).text)
    }

    @Test(expected = MessageNotFoundException::class)
    fun getPostCommentException() {
        val service = MessageService.init<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        service.getById(0).comments.getById(0)
    }

    @Test
    fun deleteRestorePostComment() {
        val service = MessageService.init<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        service.getById(0).comments.add(Comment(1,0,0, "Пост №1, Комментарий №1"))
        service.getById(0).comments.add(Comment(1,0,0, "Пост №1, Комментарий №2"))
        assertEquals(false, service.getById(0).comments.getById(0).deleted)
        assertEquals(true, service.getById(0).comments.delete(0))
        assertEquals(false, service.getById(0).comments.restore(0))
    }

    @Test(expected = MessageNotFoundException::class)
    fun deletePostCommentException() {
        val service = MessageService.init<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        service.getById(0).comments.delete(0)
    }


    @Test(expected = MessageNotFoundException::class)
    fun restorePostCommentException() {
        val service = MessageService.init<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        service.getById(0).comments.restore(0)
    }

    @Test
    fun editPostComment() {
        val service = MessageService.init<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        service.getById(0).comments.add(Comment(0, 0, 0, "Пост №1, Комментарий №1"))
        service.getById(0).comments.edit(0, "Пост №1, Комментарий №1 Изменен")
        assertEquals("Пост №1, Комментарий №1 Изменен", service.getById(0).comments.getById(0).text)
    }

    @Test(expected = MessageNotFoundException::class)
    fun editPostCommentException() {
        val service = MessageService.init<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        service.getById(0).comments.edit(0, "Изменить несуществующий комментарий")
    }

    @Test(expected = MessageDeletedException::class)
    fun editDeletedPostCommentException() {
        val service = MessageService.init<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        service.getById(0).comments.add(Comment(1,0,0, "Пост №1, Комментарий №1"))
        service.getById(0).comments.delete(0)
        service.getById(0).comments.edit(0, "Изменить удаленный комментарий")
    }

    @Test
    fun copyPost() {
        val service = MessageService.init<Post>()
        service.add(Post(0, 0, 2, "Пост №1"))
        service.getById(0).comments.add(Comment(3, 0, 1, "Комментарий #1 к Посту №1"))
        service.getById(0).comments.add(Comment(3, 0, 1, "Комментарий #2 к Посту №1"))

        service.add(service.getById(0))
        service.edit(1, "Пост №2")
        service.getById(1).comments.edit(0, "Комментарий #1 к Посту №2")
        service.getById(1).comments.edit(1, "Комментарий #2 к Посту №2")

        assertEquals("Пост №1", service.getById(0).text)
        assertEquals("Комментарий #1 к Посту №1", service.getById(0).comments.getById(0).text)
        assertEquals("Комментарий #2 к Посту №1", service.getById(0).comments.getById(1).text)

        assertEquals("Пост №2", service.getById(1).text)
        assertEquals("Комментарий #1 к Посту №2", service.getById(1).comments.getById(0).text)
        assertEquals("Комментарий #2 к Посту №2", service.getById(1).comments.getById(1).text)
    }

    //Notes

    @Test
    fun addAndGetNotes() {
        val service = MessageService.init<Note>()
        service.add(Note(0, 0, 1, "Заметка №1", "Текст Заметка №1"))
        service.add(Note(0, 0, 1, "Заметка №2", "Текст Заметка №2"))
        service.add(Note(0, 0, 1, "Заметка №3", "Текст Заметка №3"))
        assertEquals(0, service.getById(0).id)
        assertEquals("Текст Заметка №1", service.getById(0).text)
        assertEquals(1, service.getById(1).id)
        assertEquals("Текст Заметка №2", service.getById(1).text)
        assertEquals(2, service.getById(2).id)
        assertEquals("Текст Заметка №3", service.getById(2).text)
    }

    @Test(expected = MessageNotFoundException::class)
    fun getNoteException() {
        val service = MessageService.init<Note>()
        service.getById(0)
    }

    @Test
    fun deleteRestoreNote() {
        val service = MessageService.init<Note>()
        service.add(Note(0, 0, 1, "Заметка №1", "Текст Заметка №1"))
        assertEquals(false, service.getById(0).deleted)
        assertEquals(true, service.delete(0))
        assertEquals(false, service.restore(0))
    }

    @Test(expected = MessageNotFoundException::class)
    fun deleteNoteException() {
        val service = MessageService.init<Note>()
        service.delete(0)
    }

    @Test(expected = MessageNotFoundException::class)
    fun restoreNoteException() {
        val service = MessageService.init<Note>()
        service.restore(0)
    }

    @Test
    fun editNote() {
        val service = MessageService.init<Note>()
        service.add(Note(0, 0, 1, "Заметка №1", "Текст Заметка №1"))
        service.edit(0, "Текст Заметка №1 Изменен")
        assertEquals("Текст Заметка №1 Изменен", service.getById(0).text)
    }

    @Test(expected = MessageNotFoundException::class)
    fun editNoteException() {
        val service = MessageService.init<Note>()
        service.edit(0, "Меняем текст несуществующей заметки")
    }

    @Test(expected = MessageDeletedException::class)
    fun editDeletedNoteException() {
        val service = MessageService.init<Note>()
        service.add(Note(0, 0, 1, "Заметка №1", "Текст Заметка №1"))
        service.delete(0)
        service.edit(0, "Меняем текст удаленной заметки")
    }

    @Test
    fun addAndGetNoteComments() {
        val service = MessageService.init<Note>()
        service.add(Note(0, 0, 1, "Заметка №1", "Текст Заметка №1"))
        service.getById(0).comments.add(Comment(0,0,0, "Заметка №1, Комментарий №1"))
        service.getById(0).comments.add(Comment(0,0,0, "Заметка №1, Комментарий №2"))
        assertEquals(0, service.getById(0).comments.getById(0).id)
        assertEquals("Заметка №1, Комментарий №1", service.getById(0).comments.getById(0).text)
        assertEquals(1, service.getById(0).comments.getById(1).id)
        assertEquals("Заметка №1, Комментарий №2", service.getById(0).comments.getById(1).text)
    }

    @Test(expected = MessageNotFoundException::class)
    fun getNoteCommentException() {
        val service = MessageService.init<Note>()
        service.add(Note(0, 0, 1, "Заметка №1", "Текст Заметка №1"))
        service.getById(0).comments.getById(0)
    }

    @Test
    fun deleteRestoreNoteComment() {
        val service = MessageService.init<Note>()
        service.add(Note(0, 0, 1, "Заметка №1", "Текст Заметка №1"))
        service.getById(0).comments.add(Comment(1,0,0, "Заметка №1, Комментарий №1"))
        service.getById(0).comments.add(Comment(1,0,0, "Заметка №1, Комментарий №2"))
        assertEquals(false, service.getById(0).comments.getById(0).deleted)
        assertEquals(true, service.getById(0).comments.delete(0))
        assertEquals(false, service.getById(0).comments.restore(0))
    }

    @Test(expected = MessageNotFoundException::class)
    fun deleteNoteCommentException() {
        val service = MessageService.init<Note>()
        service.add(Note(0, 0, 1, "Заметка №1", "Текст Заметка №1"))
        service.getById(0).comments.delete(0)
    }


    @Test(expected = MessageNotFoundException::class)
    fun restoreNoteCommentException() {
        val service = MessageService.init<Note>()
        service.add(Note(0, 0, 1, "Заметка №1", "Текст Заметка №1"))
        service.getById(0).comments.restore(0)
    }

    @Test
    fun editNoteComment() {
        val service = MessageService.init<Note>()
        service.add(Note(0, 0, 1, "Заметка №1", "Текст Заметка №1"))
        service.getById(0).comments.add(Comment(0, 0, 0, "Заметка №1, Комментарий №1"))
        service.getById(0).comments.edit(0, "Заметка №1, Комментарий №1 Изменен")
        assertEquals("Заметка №1, Комментарий №1 Изменен", service.getById(0).comments.getById(0).text)
    }

    @Test(expected = MessageNotFoundException::class)
    fun editNoteCommentException() {
        val service = MessageService.init<Note>()
        service.add(Note(0, 0, 1, "Заметка №1", "Текст Заметка №1"))
        service.getById(0).comments.edit(0, "Изменить несуществующий комментарий")
    }

    @Test(expected = MessageDeletedException::class)
    fun editDeletedNoteCommentException() {
        val service = MessageService.init<Note>()
        service.add(Note(0, 0, 1, "Заметка №1", "Текст Заметка №1"))
        service.getById(0).comments.add(Comment(1,0,0, "Заметка №1, Комментарий №1"))
        service.getById(0).comments.delete(0)
        service.getById(0).comments.edit(0, "Изменить удаленный комментарий")
    }

}