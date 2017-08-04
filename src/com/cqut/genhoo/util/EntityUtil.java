package com.cqut.genhoo.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.cqut.genhoo.annotation.ID;
//import com.cqut.genhoo.module.codeTable.entity.CodeTable;
//import com.cqut.genhoo.module.codeTable.entity.CodeType;

public class EntityUtil {

	private static Map<Class<?>,String> entityIDMap = new HashMap<Class<?>,String>();
	
	public static <T> String getID(Class<T> t){
		if(entityIDMap.containsKey(t)){
			System.out.println("find it");
			return entityIDMap.get(t);
		}else {
			System.out.println("scanner");
			Field[] fields = t.getDeclaredFields();
			String name = "";
			for(Field field:fields){
				if(field.isAnnotationPresent(ID.class)){
					name = field.getAnnotation(ID.class).name();
					if(name.equals("")){
						name = field.getName();
					}
					entityIDMap.put(t, name);
					break;
				}
			}
			return name;
		}
	}
	
	public static void main(String[] args) {
//		String name = EntityUtil.getID(CodeTable.class);
//		System.out.println(name);
//		String name1 = EntityUtil.getID(CodeTable.class);
//		System.out.println(name1);
//		String codeType = EntityUtil.getID(CodeType.class);
//		System.out.println(codeType);
	}
	
}
