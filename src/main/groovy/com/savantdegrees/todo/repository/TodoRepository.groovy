package com.savantdegrees.todo.repository

import com.savantdegrees.todo.entity.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryCustom {
}