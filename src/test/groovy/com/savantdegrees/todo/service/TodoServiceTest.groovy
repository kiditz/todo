package com.savantdegrees.todo.service

import com.savantdegrees.todo.Application
import com.savantdegrees.todo.common.ErrorConstant
import com.savantdegrees.todo.exception.RecordNotFoundException
import com.savantdegrees.todo.repository.TodoRepository
import com.savantdegrees.todo.entity.Todo
import org.aspectj.lang.annotation.Before
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.record.Record
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles

import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.validation.ValidationException

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.anyLong
import static org.mockito.Mockito.doNothing
import static org.mockito.Mockito.times
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

@SpringBootTest(classes = Application.class)
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class])
@ActiveProfiles("test")
class TodoServiceTest {
	@MockBean
	EntityManager entityManager
	@MockBean
	EntityManagerFactory entityManagerFactory
	@MockBean
	TodoRepository todoRepository
	@Autowired
	TodoService todoService
	@Before
	void setup() {
		when(entityManagerFactory.createEntityManager()).thenReturn(entityManager)
	}
	@Test
	void findAll() {
		List<Todo> todoList = [
				[id : 1, task : 'New Task', isCompleted : true],
				[id : 2, task : 'New Task 1', isCompleted : true],
		] as List<Todo>
		when(todoRepository.findAll(PageRequest.of(0, 10))).thenReturn(new PageImpl<Todo>(todoList))
		def res = todoService.findAll(PageRequest.of(0, 10))
		assertNotNull(res)
		assertEquals(res.size, 2)
	}

	@Test
	void findById() {
		Todo todo = new Todo(id : 1L, task : 'New Task', completed : true)
		when(todoRepository.findById(anyLong())).thenReturn(Optional.of(todo))
		def res = todoService.findById(1)
		assertNotNull(res)
		assertEquals(res.task, 'New Task')
		assertEquals(res.completed, true)
	}

	@Test
	void findByIdTodoNotFound() {
		try {
			when(todoRepository.findById(anyLong())).thenReturn(Optional.empty())
			todoService.findById(1)
		}catch(RecordNotFoundException e){
			assertEquals(e.message, ErrorConstant.TODO_NOT_FOUND)
		}
	}

	@Test
	void saveTodo() {
		Todo todo = new Todo(id : 1, task : 'New Task', completed : false)
		when(todoRepository.save(any(Todo.class))).thenReturn(todo)
		def res = todoService.saveTodo todo
		assertNotNull(res)
		assertEquals(res.task, 'New Task')
		assertEquals(res.completed, false)
	}

	@Test
	void deleteTodo() {
		doNothing().when(todoRepository).deleteById(anyLong())
		todoService.deleteTodo(1)
		verify(todoRepository, times(1)).deleteById(anyLong())
	}

}