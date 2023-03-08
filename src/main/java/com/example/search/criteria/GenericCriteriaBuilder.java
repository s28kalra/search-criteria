package com.example.search.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

/**
 * @author sagar.kalra
 */
public class GenericCriteriaBuilder<T> {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Predicate getCriteriaBuilder(SearchCriteria criteria, Root<T> root, CriteriaQuery<?> query,
			CriteriaBuilder builder) {
		Class<? extends T> x = root.getJavaType();
		Class y = null;
		try {
			y = x.getDeclaredField(criteria.getKey()).getType();
		} catch (Exception e) {

		}
		if (y != null && y.isEnum()) {
			if (criteria.getValue() instanceof List) {
				List enums = new ArrayList<>();
				for (Object e : (List) criteria.getValue()) {
					e = Enum.valueOf(y, String.valueOf(e));
					enums.add(e);
				}
				criteria.setValue(enums);
			} else {
				criteria.setValue(Enum.valueOf(y, String.valueOf(criteria.getValue())));
			}
		}
		switch (criteria.getOperation()) {
		case GREATER_THAN:
			return builder.greaterThan(getExpression(criteria.getKey(), root), (Comparable) criteria.getValue());
		case LESS_THAN:
			return builder.lessThan(getExpression(criteria.getKey(), root), (Comparable) criteria.getValue());
		case GREATER_THAN_EQUAL:
			return builder.greaterThanOrEqualTo(getExpression(criteria.getKey(), root),
					(Comparable) criteria.getValue());
		case LESS_THAN_EQUAL:
			return builder.lessThanOrEqualTo(getExpression(criteria.getKey(), root), (Comparable) criteria.getValue());
		case NOT_EQUAL:
			return builder.notEqual(getExpression(criteria.getKey(), root), criteria.getValue());
		case EQUAL:
			return builder.equal(getExpression(criteria.getKey(), root), criteria.getValue());
		case MATCH:
			return builder.like(getExpression(criteria.getKey(), root).as(String.class),
					"%" + criteria.getValue().toString() + "%");
		case MATCH_END:
			return builder.like(getExpression(criteria.getKey(), root).as(String.class),
					criteria.getValue().toString() + "%");
		case MATCH_START:
			return builder.like(getExpression(criteria.getKey(), root).as(String.class),
					"%" + criteria.getValue().toString());
		case IN:
			return getInCriteria(criteria, builder, root);
		case NOT_IN:
			return builder.not(getExpression(criteria.getKey(), root)).in(criteria.getValue());
		case EQUAL_IGNORE_CASE:
			return builder.equal(builder.lower(getExpression(criteria.getKey(), root).as(String.class)),
					criteria.getValue().toString().toLowerCase());
		case MATCH_IGNORE_CASE:
			return builder.like(builder.lower(getExpression(criteria.getKey(), root).as(String.class)),
					"%" + criteria.getValue().toString().toLowerCase() + "%");

		default:
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Predicate getInCriteria(SearchCriteria criteria, CriteriaBuilder builder, Root<T> root) {
		Object criteriaValue = criteria.getValue();
		if (!(criteriaValue instanceof List))
			return null;
		List values = (List) criteriaValue;
		In in = builder.in(getExpression(criteria.getKey(), root));
		for (Object value : values) {
			in = in.value(value);
		}
		return in;
	}

	@SuppressWarnings({ "rawtypes" })
	private Path getExpression(String key, Root<T> root) {
		Path expression = null;
		if (key.contains(".")) {
			String[] names = StringUtils.split(key, ".");
			expression = root.get(names[0]);
			for (int i = 1; i < names.length; i++) {
				expression = expression.get(names[i]);
			}
		} else {
			expression = root.get(key);
		}
		return expression;
	}
}
