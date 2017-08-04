package com.cqut.genhoo.bean;

import java.util.HashMap;
import java.util.Map;

public class Page {

	//request.getRequestDispatcher(path).forward(request, response);
	public static final String FORWARD = "forward";//转发请求
	//response.sendRedirect(request.getContextPath() + path);
	public static final String REDIRECT = "redirect";//重定向请求
	
	private String path;              // 页面路径
    private Map<String, Object> data; // 相关数据
    
    private String type = Page.FORWARD;
    
    //转发请求的时候的默认方法是get，也可以自己指定
    private String method = "get";
    
    public Page(){
    	data = new HashMap<String, Object>();
    }
    
    public Page(String path) {
        this.path = path;
        data = new HashMap<String, Object>();
    }
    
    
    public String getMethod() {
		return method;
	}

	public Page setMethod(String method) {
		this.method = method;
		return this;
	}

	public Page(String path,String type) {
        this.path = path;
        data = new HashMap<String, Object>();
        this.type = type;
    }

    public Page data(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public boolean isRedirect() {
    	System.err.println(path+":"+!path.contains("."));
    	return !path.contains(".");
       
    }

    public String getType() {
		return type;
	}

	public Page setType(String type) {
		this.type = type;
		return this;
	}

	public String getPath() {
        return path;
    }

    public Page setPath(String path) {
        this.path = path;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Page setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }
    
    public String getAtionPath(){
    	return this.method+":"+this.path;
    }
    
}
