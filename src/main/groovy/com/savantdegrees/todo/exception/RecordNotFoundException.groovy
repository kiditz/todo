package com.savantdegrees.todo.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class RecordNotFoundException extends RuntimeException {
	RecordNotFoundException(String exception) {
		super(exception)
	}
}