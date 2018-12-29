package com.example.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel导出注解
 * <p>Title: ExportIndex</p>  
 * <p>Description: </p>  
 * @author liuxiangtao90  
 * @date 2018年4月27日 下午3:25:58
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportIndex 
{
	// 列索引
	int index();
	// 列名
	String name() default"";
	// 列类型
	String type() default ValidateType.TEXT;
}
