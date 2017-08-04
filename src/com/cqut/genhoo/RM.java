package com.cqut.genhoo;

import java.util.HashMap;
import java.util.Map;

/**
 * RM: response message
 * @author Jungoo
 *
 */
public class RM {

	public static final String Login_SUCCESS = "登录成功";
	public static final String Login_ERROR = "登录失败";
	
	public static final String ADD_SUCCESS = "新增成功";
	public static final String ADD_ERROR = "新增失败";
	
	public static final String UPDATE_SUCCESS = "更新成功";
	public static final String UPDATE_ERROR = "更新失败";
	
	public static final String DELETE_SUCCESS = "删除成功";
	public static final String DELETE_ERROR = "删除失败";
	
	public static Map<String,Object> createResponseMesage(boolean ok,String message){
		Map<String,Object> rmap = new HashMap<String,Object>();
		rmap.put("OK", ok);
		rmap.put("message", message);
		return rmap;
	}
	
}
