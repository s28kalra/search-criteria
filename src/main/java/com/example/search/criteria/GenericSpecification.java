package com.example.search.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

/**
 * @author sagar.kalra
 */
public class GenericSpecification<T> implements Specification<T> {

	private static final long serialVersionUID = 1L;
	private SearchCriteria criteria;

	public GenericSpecification(final SearchCriteria criteria) {
		super();
		this.criteria = criteria;
	}

	public SearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		GenericCriteriaBuilder<T> genericCriteriaBuilder = new GenericCriteriaBuilder<>();
		return genericCriteriaBuilder.getCriteriaBuilder(criteria, root, query, builder);
	}

	@Override
	public Specification<T> and(Specification<T> other) {
		return Specification.super.and(other);
	}

	@Override
	public Specification<T> or(Specification<T> other) {
		return Specification.super.or(other);
	}

}