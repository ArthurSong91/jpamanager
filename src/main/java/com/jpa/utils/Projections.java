package com.jpa.utils;

/**
 * 计算用类
 */
public class Projections {
	
	public static Projection max(String col){
		return new Projection(col,  Criterion.Projection.MAX);
	}
	
	public static Projection length(String col){
		return new Projection(col, Criterion.Projection.LENGTH);
	}
	
	public static Projection min(String col){
		return new Projection(col, Criterion.Projection.MIN);
	}
	
	public static Projection sum(String col){
		return new Projection(col, Criterion.Projection.SUM);
	}
}
