package com.savantdegrees.todo.domain

import com.savantdegrees.todo.common.ErrorConstant
import com.savantdegrees.todo.entity.Todo
import com.savantdegrees.todo.exception.RecordNotFoundException
import com.savantdegrees.todo.repository.TodoRepository
import com.savantdegrees.todo.service.TodoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

import javax.transaction.Transactional
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@Service
class DomainTodoService implements TodoService {
	@Autowired
	TodoRepository todoRepository

	@Override
	Page<Todo> findAll(@NotBlank Pageable pageable) {
		return todoRepository.findAll(pageable)
	}

	@Override
	Todo findById(Long todoId) {
		return todoRepository.findById(todoId).orElseThrow({ -> new RecordNotFoundException(ErrorConstant.TODO_NOT_FOUND) })
	}

	@Transactional
	@Override
	Todo saveTodo(@Valid Todo todo) {
		return todoRepository.save(todo)
	}

	@Transactional
	@Override
	void deleteTodo(Long todoId) {
		todoRepository.deleteById(todoId)
	}
}