package com.savantdegrees.todo.api.v1

import com.savantdegrees.todo.common.Result
import com.savantdegrees.todo.exception.RecordNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {
	@ExceptionHandler(RecordNotFoundException.class)
	ResponseEntity<Result> handleRecordNotFoundException(RecordNotFoundException ex) {
		Result result = new Result()
		result.status = HttpStatus.NOT_FOUND.value()
		result.error = ex.message
		return new ResponseEntity(result, HttpStatus.NOT_FOUND)
	}
}
