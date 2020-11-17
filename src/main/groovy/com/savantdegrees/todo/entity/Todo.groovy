package com.savantdegrees.todo.entity

import com.savantdegrees.todo.common.Auditable

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
class Todo extends Auditable<String> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id

	@NotBlank
	@Column
	String task
	@Column
	@NotBlank
	String note
	@NotBlank
	@Column
	Boolean completed
}
