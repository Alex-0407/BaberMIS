package com.cqut.genhoo.annotation;

/**
 * 
 * 用户放在Service上面的注解，做依赖注入。
 *	
 */
public @interface Service {
	
	public String id() default "";
	

}
