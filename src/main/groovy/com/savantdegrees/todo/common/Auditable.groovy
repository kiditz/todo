package com.savantdegrees.todo.common

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass
import javax.persistence.Temporal
import javax.persistence.TemporalType

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
abstract class Auditable<U> {
	@CreatedBy
	protected U createdBy
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createdTime
	@LastModifiedBy
	protected U lastModifiedBy
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastModifiedDate
}