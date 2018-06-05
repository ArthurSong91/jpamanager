package com.jpa.utils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/** 
 * 逻辑条件表达式 用于复杂条件时使用，如单属性多对应值的OR查询等
 */
public class LogicalExpression implements Criterion {

	/**
	 * 逻辑表达式中包含的表达式
	 */
	private Criterion[] criterion;

	/**
	 * 计算符
	 */
	private Operator operator;
	private String col;
	private Object exp1;
	private Object exp2;

	LogicalExpression(Criterion[] criterions, Operator operator) {
		this.criterion = criterions;
		this.operator = operator;
	}

	/**
	 * between用构造方法
	 *
	 * @param col      属性
	 * @param exp1     字段1
	 * @param exp2     字段2
	 * @param operator 运算符
	 */
	LogicalExpression(String col, Object exp1, Object exp2, Operator operator) {
		this.col = col;
		this.exp1 = exp1;
		this.exp2 = exp2;
		this.operator = operator;
		this.criterion = null;
	}

	public Predicate toPredicate(Root<?> root, CriteriaQuery<?> query,
								 CriteriaBuilder builder) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (!(criterion == null || criterion.length == 0))
			for (Criterion aCriterion : this.criterion) {
				predicates.add(aCriterion.toPredicate(root, query, builder));
			}
		switch (operator) {
			case OR:
				return builder.or(predicates.toArray(new Predicate[predicates.size()]));
			case AND:
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			case BETWEEN:
				Expression expression = root.get(col);
				return builder.between(expression, (Comparable) exp1, (Comparable) exp2);
			default:
				return null;
		}
	}

	public String getCol() {
		return col;
	}

	public Object getExp1() {
		return exp1;
	}

	public Object getExp2() {
		return exp2;
	}
}
