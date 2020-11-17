package com.savantdegrees.todo.api.v1

import com.fasterxml.jackson.databind.ObjectMapper
import com.savantdegrees.todo.common.ErrorConstant
import com.savantdegrees.todo.entity.Todo
import com.savantdegrees.todo.exception.RecordNotFoundException
import com.savantdegrees.todo.service.TodoService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc

import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.nullValue
import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.anyLong
import static org.mockito.Mockito.doNothing
import static org.mockito.Mockito.when
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(TodoController.class)
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class])
@ActiveProfiles('test')
class TodoControllerTest {
	@MockBean
	private TodoService todoService
	@Autowired
	private ObjectMapper mapper
	@Autowired
	private MockMvc mockMvc

	@Test
	void getAllTodoList() {
		List<Todo> todoList = [
				[id : 1, task : 'New Task', note : 'My Task', isCompleted : true],
				[id : 2, task : 'New Task2', note : 'My Task', isCompleted : true],
		] as List<Todo>
		def pageable = PageRequest.of(0, 10)

		when(todoService.findAll(pageable)).thenReturn(new PageImpl<Todo>(todoList))

		mockMvc.perform(get('/api/v1/todo')
				.param('page', '0')
				.param('size', '10')
		)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath('$.status', is('OK')))
				.andExpect(jsonPath('$.totalPage', is(1)))
				.andExpect(jsonPath('$.totalItems', is(2)))
				.andExpect(jsonPath('$.data', hasSize(2)))
				.andExpect(jsonPath('$.data[0].id', is(1)))
				.andExpect(jsonPath('$.data[1].id', is(2)))
				.andExpect(jsonPath('$.data[0].task', is('New Task')))
				.andExpect(jsonPath('$.data[1].task', is('New Task2')))
	}

	@Test
	void saveTodo() {
		Todo todo = new Todo(id : 1, task : 'New Task', note : 'My Task', completed : false)
		when(todoService.saveTodo(any(Todo.class))).thenReturn(todo)
		mockMvc.perform(put('/api/v1/todo')
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(todo))
		)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath('$.data.id', is(1)))
				.andExpect(jsonPath('$.data.task', is('New Task')))
				.andExpect(jsonPath('$.data.note', is('My Task')))
				.andExpect(jsonPath('$.data.completed', is(false)))

	}

	@Test
	void saveTodoWithEmptyInput() {
		Todo todo = new Todo(id : 1, task : 'New Task', note : 'My Task', completed : false)
		when(todoService.saveTodo(any(Todo.class))).thenReturn(todo)

		mockMvc.perform(put('/api/v1/todo')
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE)
				.content("{}")
		)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath('$.data.id', is(1)))
				.andExpect(jsonPath('$.data.task', is('New Task')))
				.andExpect(jsonPath('$.data.note', is('My Task')))
				.andExpect(jsonPath('$.data.completed', is(false)))

	}

	@Test
	void deleteTodo() {
		doNothing().when(todoService).deleteTodo(anyLong())
		mockMvc.perform(delete("/api/v1/todo/{todoId}", 1))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath('$.data', nullValue()))
	}

	@Test
	void getTodoById() {
		Todo todo = new Todo(id : 1, task : 'New Task', note : 'My Task', completed : true)
		when(todoService.findById(anyLong())).thenReturn(todo)
		mockMvc.perform(get("/api/v1/todo/{todoId}", 1))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath('$.data.id', is(1)))
				.andExpect(jsonPath('$.data.task', is('New Task')))
				.andExpect(jsonPath('$.data.note', is('My Task')))
				.andExpect(jsonPath('$.data.completed', is(true)))
	}

	@Test
	void getTodoByIdRecordNotFound() {
		when(todoService.findById(anyLong())).thenThrow(new RecordNotFoundException(ErrorConstant.TODO_NOT_FOUND))
		mockMvc.perform(get("/api/v1/todo/{todoId}", 1))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath('$.error', is(ErrorConstant.TODO_NOT_FOUND)))

	}
}