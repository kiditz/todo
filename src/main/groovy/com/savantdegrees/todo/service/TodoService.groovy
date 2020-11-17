package com.savantdegrees.todo.service

import com.savantdegrees.todo.entity.Todo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TodoService {
	Page<Todo> findAll(Pageable pageable)

	Todo findById(Long todoId)

	Todo saveTodo(Todo todo)

	void deleteTodo(Long todoId)
}