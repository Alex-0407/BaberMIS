package com.cqut.genhoo.helper;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.cqut.genhoo.annotation.Action;
import com.cqut.genhoo.annotation.RequestMapping;
import com.cqut.genhoo.helper.bean.ActionBean;

public class ActionHelper {
	
	//private static final Map<RequestBean, ActionBean> actionMap = new LinkedHashMap<RequestBean, ActionBean>();
	private static final Map<String,ActionBean> actionMap = new HashMap<String,ActionBean>();
	static {
		List<Class<?>> actionClassList = ClassHelper.getClassListByAnnotation(Action.class);
		
		//System.out.println(Arrays.deepToString(actionClassList.toArray()));
		
		if(CollectionUtils.isNotEmpty(actionClassList)){
			Map<String,ActionBean> actionPathMap = new HashMap<String,ActionBean>();
			// 遍历 Action 类
			for (Class<?> actionClass : actionClassList) {
				// 获取并遍历该 Action 类中所有的方法（不包括父类中的方法）
                Method[] actionMethods = actionClass.getDeclaredMethods();
                
                //System.out.println(Arrays.deepToString(actionMethods));
                
                if(!ArrayUtils.isEmpty(actionMethods)){
                	for (Method actionMethod : actionMethods) {
                		//判断当前方法是否有RequestMapping注解标识
                		if(actionMethod.isAnnotationPresent(RequestMapping.class)){
                			
                			String requestMethod = actionMethod.getAnnotation(RequestMapping.class).method();
                            String requestPath = actionMethod.getAnnotation(RequestMapping.class).value();
                            ActionBean actionBean = new ActionBean(actionClass, actionMethod);
                            //如果没有指定 请求的方式，默认可以响应 get 和 post 方式
                            String classRequestMappingStr = "";
                            if(actionClass.isAnnotationPresent(RequestMapping.class)){
                            	RequestMapping rm = actionClass.getAnnotation(RequestMapping.class);
                            	String value = rm.value();
                            	if(!value.equals("/")){
                            		classRequestMappingStr = value;
                            	}
                            }
                            if(requestMethod.equals("")){
                            	actionPathMap.put("get:" + classRequestMappingStr + requestPath, actionBean);
                            	actionPathMap.put("post:" + classRequestMappingStr +  requestPath, actionBean);
                            }else{
                            	actionPathMap.put(requestMethod.toLowerCase()+":"+ classRequestMappingStr +requestPath, actionBean);
                            }
                		}
                	}
                }
			}
			actionMap.putAll(actionPathMap);
		}
	}
	
	public static Map<String, ActionBean> getActionMap() {
        return actionMap;
    }
	
}
