package com.cqut.genhoo.annotation;

/**
 * 该注解用于在生成实体的时候使用，用于标注实体的属性
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CQUTColumn {

	public String name() default "";
	
	public String label() default "";
	
	//对应的java.sql.Types
	public SqlType type();
	
	public int length() default 0;
	
	public boolean nullable() default true;
	
	public boolean isReference() default false;
	
	public String refer() default "";
	
	//索引类型
	public IndexType indexType() default IndexType.NoIndex;
	
	public String comments() default "";
	
	//关联的码表 not 表示不关联
	public String codeType() default "false";
	
}
