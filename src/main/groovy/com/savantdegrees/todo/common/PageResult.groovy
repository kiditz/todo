package com.savantdegrees.todo.common

import org.springframework.data.domain.Page

class PageResult<T> {
	String status = "OK"
	int totalPage
	int currentPage
	long totalItems
	List<T> data

	PageResult(Page<T> page) {
		totalPage = page.totalPages
		totalItems = page.totalElements
		currentPage = page.number
		data = page.content
	}
}
