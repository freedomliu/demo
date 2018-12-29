package com.example.demo.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

/**
 * Excel导入注解
 * <p>Title: ImportIndex</p>  
 * <p>Description: </p>  
 * @author liuxiangtao90  
 * @date 2018年4月27日 下午3:26:55
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ImportIndex 
{
	// 列索引
	int index();
	// 列名
	String name();
	// 是否必填
	boolean required() default true;
	// 类型
	String type() default ValidateType.TEXT;
	// 长度限制
	short length() default 50;
}
