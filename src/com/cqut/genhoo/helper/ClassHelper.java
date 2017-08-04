package com.cqut.genhoo.helper;

import java.lang.annotation.Annotation;
import java.util.List;

import com.cqut.genhoo.util.ClassUtil;

public class ClassHelper {

	private static final String packageName = "com.cqut";//ConfigHelper.getConfigString("app.package");
	
	public static List<Class<?>> getClassListByAnnotation(Class<? extends Annotation> annotationClass) {
		return ClassUtil.getClassListByAnnotation(packageName, annotationClass);
	}

}
