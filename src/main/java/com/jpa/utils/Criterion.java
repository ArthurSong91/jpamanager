package com.jpa.utils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/** 
 * 条件接口 
 * 用户提供条件表达式接口 
 */
public interface Criterion {

	enum Operator {
		EQ, NE, LIKE, GT, LT, GTE, LTE, AND, OR, BETWEEN
	}

	enum MatchMode {
		START, END, ANYWHERE
	}

	enum Projection {
		MAX, MIN, AVG, LENGTH, SUM, COUNT
	}

	Predicate toPredicate(Root<?> root, CriteriaQuery<?> query,
						  CriteriaBuilder builder);
}
