package com.savantdegrees.todo.api.v1

import com.savantdegrees.todo.common.PageResult
import com.savantdegrees.todo.common.Result
import com.savantdegrees.todo.entity.Todo
import com.savantdegrees.todo.service.TodoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping('/api/v1')
class TodoController {

	@Autowired
	TodoService todoService

	@GetMapping("/todo")
	PageResult<Todo> getAllTodoList(Pageable pageable) {
		return new PageResult<Todo>(todoService.findAll(pageable))
	}

	@PutMapping("/todo")
	Result<Todo> saveTodo(@RequestBody Todo todo) {
		def data = todoService.saveTodo(todo)
		new Result<Todo>(data : data)
	}

	@DeleteMapping('/todo/{todoId}')
	deleteTodo(@PathVariable Integer todoId) {
		def data = todoService.deleteTodo(todoId)
		new Result<Todo>(data : data)
	}

	@GetMapping('/todo/{todoId}')
	Result<Todo> getTodoById(@PathVariable Long todoId) {
		def data = todoService.findById(todoId)
		new Result<Todo>(data : data)
	}
}