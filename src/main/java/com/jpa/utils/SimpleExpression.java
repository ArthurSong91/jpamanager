package com.jpa.utils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 简单条件表达式
 */
public class SimpleExpression implements Criterion {

    /**
     * 属性名
     */
    private String fieldName;

    /**
     * 对应值
     */
    private Object value;

    /**
     * 计算符
     */
    private Operator operator;

    /**
     * like匹配方式
     */
    private MatchMode matchMode;

    SimpleExpression(String fieldName, Object value, Operator operator) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    SimpleExpression(String fieldName, Object value, MatchMode matchMode, Operator operator) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
        this.matchMode = matchMode;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getValue() {
        return value;
    }

    public Operator getOperator() {
        return operator;
    }

    public MatchMode getMatchMode() {
        return matchMode;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public Predicate toPredicate(Root <?> root, CriteriaQuery <?> query,
                                 CriteriaBuilder builder) {
        Path expression;
        if (fieldName.contains( "." )) {
            String[] names = fieldName.split( "\\." );
            expression = root.get( names[0] );
            for (int i = 1; i < names.length; i++) {
                expression = expression.get( names[i] );
            }
        } else {
            expression = root.get( fieldName );
        }

        switch (operator) {
            case EQ:
                return builder.equal( expression, value );
            case NE:
                return builder.notEqual( expression, value );
            case LIKE:
                switch (matchMode) {
                    case START:
                        return builder.like( (Expression <String>)expression, value + "%" );
                    case END:
                        return builder.like( (Expression <String>)expression, "%" + value );
                    case ANYWHERE:
                        return builder.like( (Expression <String>)expression, "%" + value + "%" );
                    default:
                        return builder.like( (Expression <String>)expression, "%" + value + "%" );
                }
            case LT:
                return builder.lessThan( expression, (Comparable)value );
            case GT:
                return builder.greaterThan( expression, (Comparable)value );
            case LTE:
                return builder.lessThanOrEqualTo( expression, (Comparable)value );
            case GTE:
                return builder.greaterThanOrEqualTo( expression, (Comparable)value );
            default:
                return null;
        }
    }

}
