package com.savantdegrees.todo.repository

import com.savantdegrees.todo.entity.Todo

interface TodoRepositoryCustom {
	List<Todo> findByMap(Map params)
}