import org.junit.Assert.*
import org.junit.Test

class MessagesTest {

    //Посты

    @Test
    fun addAndGetPosts() {
        val service = MessageService<Post>()
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
        val service = MessageService<Post>()
        service.getById(0)
    }

    @Test
    fun deleteRestorePost() {
        val service = MessageService<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        assertEquals(false, service.getById(0).deleted)
        assertEquals(true, service.delete(0))
        assertEquals(false, service.restore(0))
    }

    @Test(expected = MessageNotFoundException::class)
    fun deletePostException() {
        val service = MessageService<Post>()
        service.delete(0)
    }

    @Test(expected = MessageNotFoundException::class)
    fun restorePostException() {
        val service = MessageService<Post>()
        service.restore(0)
    }

    @Test
    fun editPost() {
        val service = MessageService<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        service.edit(0, "Пост №1 Изменен")
        assertEquals("Пост №1 Изменен", service.getById(0).text)
    }

    @Test(expected = MessageNotFoundException::class)
    fun editPostException() {
        val service = MessageService<Post>()
        service.edit(0, "Меняем текст несуществующего поста")
    }

    @Test
    fun addPostComments() {
        val service = MessageService<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        service.addComment(0, 1,"Пост №1, Комментарий №1")
        service.addComment(0, 1,"Пост №1, Комментарий №2")
        assertEquals("Пост №1, Комментарий №1", service.getCommentById(0, 0).text)
        assertEquals("Пост №1, Комментарий №2", service.getCommentById(0, 1).text)
    }

    @Test(expected = MessageNotFoundException::class)
    fun getPostCommentWithNoPostException() {
        val service = MessageService<Post>()
        service.getCommentById(0, 0)
    }

    @Test(expected = CommentNotFoundException::class)
    fun getPostCommentException() {
        val service = MessageService<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        service.getCommentById(0, 0)
    }

    @Test
    fun deleteRestorePostComment() {
        val service = MessageService<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        service.addComment(0, 1,"Пост №1, Комментарий №1")
        service.addComment(0, 1,"Пост №1, Комментарий №2")
        assertEquals(false, service.getCommentById(0, 1).deleted)
        assertEquals(true, service.deleteComment(0, 1))
        assertEquals(false, service.restoreComment(0, 1))
    }

    @Test(expected = MessageNotFoundException::class)
    fun deletePostCommentWithNoPostException() {
        val service = MessageService<Post>()
        service.deleteComment(0, 0)
    }

    @Test(expected = CommentNotFoundException::class)
    fun deletePostCommentException() {
        val service = MessageService<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        service.deleteComment(0, 0)
    }

    @Test(expected = MessageNotFoundException::class)
    fun restorePostCommentWithNoPostException() {
        val service = MessageService<Post>()
        service.restoreComment(0, 0)
    }

    @Test(expected = CommentNotFoundException::class)
    fun restorePostCommentException() {
        val service = MessageService<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        service.restoreComment(0, 0)
    }

    @Test
    fun editPostComment() {
        val service = MessageService<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        service.addComment(0, 1,"Пост №1, Комментарий №1")
        service.editComment(0, 0,"Пост №1, Комментарий №1 Изменен")
        assertEquals("Пост №1, Комментарий №1 Изменен", service.getCommentById(0, 0).text)
    }

    @Test(expected = MessageNotFoundException::class)
    fun editPostCommentWithNoPostException() {
        val service = MessageService<Post>()
        service.editComment(0, 0, "Изменить комментарий к несуществующему посту")
    }

    @Test(expected = CommentNotFoundException::class)
    fun editPostCommentException() {
        val service = MessageService<Post>()
        service.add(Post(0, 0, 1, "Пост №1"))
        service.editComment(0, 0, "Изменить несуществующий комментарий")
    }
    
    //Заметки

    @Test
    fun addAndGetNotes() {
        val service = MessageService<Note>()
        service.add(Note(0, 0, 1, "Заметка №1 Заголовок", "Заметка №1"))
        service.add(Note(0, 0, 1, "Заметка №2 Заголовок", "Заметка №2"))
        service.add(Note(0, 0, 1, "Заметка №3 Заголовок", "Заметка №3"))
        assertEquals(0, service.getById(0).id)
        assertEquals("Заметка №1", service.getById(0).text)
        assertEquals(1, service.getById(1).id)
        assertEquals("Заметка №2", service.getById(1).text)
        assertEquals(2, service.getById(2).id)
        assertEquals("Заметка №3", service.getById(2).text)
    }

    @Test(expected = MessageNotFoundException::class)
    fun getNoteException() {
        val service = MessageService<Note>()
        service.getById(0)
    }

    @Test
    fun deleteRestoreNote() {
        val service = MessageService<Note>()
        service.add(Note(0, 0, 1, "Заметка №1 Заголовок", "Заметка №1"))
        assertEquals(false, service.getById(0).deleted)
        assertEquals(true, service.delete(0))
        assertEquals(false, service.restore(0))
    }

    @Test(expected = MessageNotFoundException::class)
    fun deleteNoteException() {
        val service = MessageService<Note>()
        service.delete(0)
    }

    @Test(expected = MessageNotFoundException::class)
    fun restoreNoteException() {
        val service = MessageService<Note>()
        service.restore(0)
    }

    @Test
    fun editNote() {
        val service = MessageService<Note>()
        service.add(Note(0, 0, 1, "Заметка №1 Заголовок", "Заметка №1"))
        service.edit(0, "Заметка №1 Изменен")
        assertEquals("Заметка №1 Изменен", service.getById(0).text)
    }

    @Test(expected = MessageNotFoundException::class)
    fun editNoteException() {
        val service = MessageService<Note>()
        service.edit(0, "Меняем текст несуществующего Заметкаа")
    }

    @Test
    fun addNoteComments() {
        val service = MessageService<Note>()
        service.add(Note(0, 0, 1, "Заметка №1 Заголовок", "Заметка №1"))
        service.addComment(0, 1,"Заметка №1, Комментарий №1")
        service.addComment(0, 1,"Заметка №1, Комментарий №2")
        assertEquals("Заметка №1, Комментарий №1", service.getCommentById(0, 0).text)
        assertEquals("Заметка №1, Комментарий №2", service.getCommentById(0, 1).text)
    }

    @Test(expected = MessageNotFoundException::class)
    fun getNoteCommentWithNoNoteException() {
        val service = MessageService<Note>()
        service.getCommentById(0, 0)
    }

    @Test(expected = CommentNotFoundException::class)
    fun getNoteCommentException() {
        val service = MessageService<Note>()
        service.add(Note(0, 0, 1, "Заметка №1 Заголовок", "Заметка №1"))
        service.getCommentById(0, 0)
    }

    @Test
    fun deleteRestoreNoteComment() {
        val service = MessageService<Note>()
        service.add(Note(0, 0, 1, "Заметка №1 Заголовок", "Заметка №1"))
        service.addComment(0, 1,"Заметка №1, Комментарий №1")
        service.addComment(0, 1,"Заметка №1, Комментарий №2")
        assertEquals(false, service.getCommentById(0, 1).deleted)
        assertEquals(true, service.deleteComment(0, 1))
        assertEquals(false, service.restoreComment(0, 1))
    }

    @Test(expected = MessageNotFoundException::class)
    fun deleteNoteCommentWithNoNoteException() {
        val service = MessageService<Note>()
        service.deleteComment(0, 0)
    }

    @Test(expected = CommentNotFoundException::class)
    fun deleteNoteCommentException() {
        val service = MessageService<Note>()
        service.add(Note(0, 0, 1, "Заметка №1 Заголовок", "Заметка №1"))
        service.deleteComment(0, 0)
    }

    @Test(expected = MessageNotFoundException::class)
    fun restoreNoteCommentWithNoNoteException() {
        val service = MessageService<Note>()
        service.restoreComment(0, 0)
    }

    @Test(expected = CommentNotFoundException::class)
    fun restoreNoteCommentException() {
        val service = MessageService<Note>()
        service.add(Note(0, 0, 1, "Заметка №1 Заголовок", "Заметка №1"))
        service.restoreComment(0, 0)
    }

    @Test
    fun editNoteComment() {
        val service = MessageService<Note>()
        service.add(Note(0, 0, 1, "Заметка №1 Заголовок", "Заметка №1"))
        service.addComment(0, 1,"Заметка №1, Комментарий №1")
        service.editComment(0, 0,"Заметка №1, Комментарий №1 Изменен")
        assertEquals("Заметка №1, Комментарий №1 Изменен", service.getCommentById(0, 0).text)
    }

    @Test(expected = MessageNotFoundException::class)
    fun editNoteCommentWithNoNoteException() {
        val service = MessageService<Note>()
        service.editComment(0, 0, "Изменить комментарий к несуществующему Заметкау")
    }

    @Test(expected = CommentNotFoundException::class)
    fun editNoteCommentException() {
        val service = MessageService<Note>()
        service.add(Note(0, 0, 1, "Заметка №1 Заголовок", "Заметка №1"))
        service.editComment(0, 0, "Изменить несуществующий комментарий")
    }

}