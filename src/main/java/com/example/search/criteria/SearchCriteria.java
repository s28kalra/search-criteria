package com.example.search.criteria;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sagar.kalra
 */
@Getter
@Setter
public class SearchCriteria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5641394827240103641L;

	public enum SearchOperation {
		GREATER_THAN, LESS_THAN, GREATER_THAN_EQUAL, LESS_THAN_EQUAL, NOT_EQUAL, EQUAL, MATCH, MATCH_START, MATCH_END,
		IN, NOT_IN, CONTAINS, ENDS_WITH, START_WITH, EQUAL_IGNORE_CASE, MATCH_IGNORE_CASE;

		public static final String OR_PREDICATE_FLAG = "'";
		public static final String ZERO_OR_MORE_REGEX = "*";
		public static final String OR_OPERATOR = "OR";
		public static final String AND_OPERATOR = "AND";
		public static final String LEFT_PARANTHESIS = "(";
		public static final String RIGHT_PARANTHESIS = ")";
	}

	private String key;
	private SearchOperation operation;
	private Object value;
	private boolean orPredicate;

	public SearchCriteria() {

	}

	public SearchCriteria(final String key, final Object value, final SearchOperation operation) {
		super();
		this.key = key;
		this.operation = operation;
		this.value = value;
	}

	public SearchCriteria(final boolean orPredicate, final String key, final Object value,
			final SearchOperation operation) {
		super();
		this.orPredicate = orPredicate;
		this.key = key;
		this.operation = operation;
		this.value = value;
	}

}
