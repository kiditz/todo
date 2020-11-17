package com.savantdegrees.todo.repository


import com.savantdegrees.todo.entity.Todo
import org.springframework.stereotype.Repository

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

@Repository
class TodoRepositoryCustomImpl implements TodoRepositoryCustom {
	@PersistenceContext
	EntityManager em

	@Override
	List<Todo> findByMap(Map params) {
		CriteriaBuilder builder = em.getCriteriaBuilder()
		CriteriaQuery<Todo> criteria = builder.createQuery(Todo.class)
		Root<Todo> root = criteria.from(Todo.class)
		List<Predicate> predicates =[]
		criteria.select(root).where(predicates.toArray(new Predicate[predicates.size()]) as Predicate)
		TypedQuery<Todo> query = em.createQuery(criteria)
		return query.getResultList()
	}
}
