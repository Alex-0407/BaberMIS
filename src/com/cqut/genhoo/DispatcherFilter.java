package com.cqut.genhoo;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.cqut.genhoo.annotation.RequestParam;
import com.cqut.genhoo.annotation.ResponseBody;
import com.cqut.genhoo.bean.ErrorPage;
import com.cqut.genhoo.bean.Page;
import com.cqut.genhoo.helper.ActionHelper;
import com.cqut.genhoo.helper.bean.ActionBean;
import com.cqut.genhoo.util.CastUtil;
import com.cqut.genhoo.util.WebUtil;

public class DispatcherFilter implements Filter {

    /**
     * Default constructor. 
     */
    public DispatcherFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		//设置编码
		httpServletRequest.setCharacterEncoding(FrameworkConstant.CHARSET_UTF);
		//获取请求方法
		String currentRequestMethod = httpServletRequest.getMethod();
		//获取请求路径
        String currentRequestPath = getRequestPath(httpServletRequest);
        
        //打印请求信息，方便调试
        System.out.println("-----------------请求信息----------------");
        System.out.println(currentRequestMethod+";"+currentRequestPath);
        System.out.println(""+JSON.toJSONString(request.getParameterMap(),true));
        System.out.println("--------------------------------------");
        
        
        //静态资源直接放行
        if(isStaticResource(currentRequestPath)){
        	//System.out.println(" is mathced ok!");
        	chain.doFilter(request, response);
        } else {
        	//执行action 
        	//创建请求字符串  get:/user/list
    		String requestPath = currentRequestMethod.toLowerCase()+":"+currentRequestPath;
        	boolean isMapped = actionHandler(httpServletRequest,httpServletResponse,chain,requestPath);//默认为映射失败
        	//如果映射失败就执行默认的操作
        	if(!isMapped){
        		chain.doFilter(request, response);
        	}
        }
	}

	//处理action请求
	private boolean actionHandler(HttpServletRequest request, HttpServletResponse response, FilterChain chain,String requestPath){
		
		// 获取并遍历 Action 映射
    	//Map<String, ActionBean> actionMap = ActionHelper.getActionMap();
    	
		//请求方法和请求的action方法
    	ActionBean actionBean = ActionHelper.getActionMap().get(requestPath);
    	if(actionBean!=null){
    		Class<?> actionClass = actionBean.getActionClass();
            Method actionMethod = actionBean.getActionMethod();
            
            //设置可访问性
            actionMethod.setAccessible(true);
    		try {
				Object instance = actionClass.newInstance();
				System.out.println("调用方法:"+actionMethod.toGenericString());
				//创建参数列表
				Object[] actionMethodParams = createActionMethodParamList(request,response,actionMethod);
				//调用方法
				Object actionMethodResult = actionMethod.invoke(instance, actionMethodParams);
				//是否返回的数据为JSON格式的数据
				boolean isResponseBody = actionMethod.isAnnotationPresent(ResponseBody.class);
				//处理放回结果
				handleActionMethodReturn(request, response,chain, actionMethodResult,isResponseBody);
				return true;
			
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
    		
    	} else {
    		System.err.println(requestPath+"不存在");
    		return false;
    	}
	}
	
	private Object[] createActionMethodParamList(
			HttpServletRequest request, HttpServletResponse response,
			Method actionMethod) {
		// TODO Auto-generated method stub
		Class<?>[] parameterTypes = actionMethod.getParameterTypes();
		Annotation[][] parameterAnnotations = actionMethod.getParameterAnnotations();
		
		Object[] objs = new Object[parameterTypes.length];
		for(int i=0;i<parameterTypes.length;i++){
			Class<?> parameterType =  parameterTypes[i];
			Annotation[] parameterTypeAnnotations = parameterAnnotations[i];
			if(parameterTypeAnnotations.length>0){
				for(Annotation anno:parameterTypeAnnotations){
					if (anno instanceof RequestParam) {
						RequestParam requestParam = (RequestParam) anno;
						String parameterName = requestParam.value();
						String parameterValue = WebUtil.getParamter(request, parameterName);
						if (parameterType.equals(int.class) || parameterType.equals(Integer.class)) {
			                objs[i] = CastUtil.castInt(parameterValue);
			            } else if (parameterType.equals(long.class) || parameterType.equals(Long.class)) {
			                objs[i] = CastUtil.castLong(parameterValue);
			            } else if (parameterType.equals(double.class) || parameterType.equals(Double.class)) {
			                objs[i] = CastUtil.castDouble(parameterValue);
			            } else if (parameterType.equals(String.class)) {
			                objs[i] = parameterValue;
			            }
					}
				}
			} else if(parameterType.equals(HttpServletRequest.class)){
				objs[i] = request;
			}else if(parameterType.equals(HttpServletResponse.class)){
				objs[i] = response;
			}else if(isBaseType(parameterType)){
				if (parameterType.equals(int.class) || parameterType.equals(Integer.class)) {
	                objs[i] = 0;
	            } else if (parameterType.equals(long.class) || parameterType.equals(Long.class)) {
	                objs[i] = 0L;
	            } else if (parameterType.equals(double.class) || parameterType.equals(Double.class)) {
	                objs[i] = 0.0;
	            } else if (parameterType.equals(String.class)) {
	                objs[i] = null;
	            }
			} else { //其他的引用数据类型
				//如果是其他的引用数据类型就反射生产一个对象
				System.out.println("解析非基本数据类型");
				objs[i] = WebUtil.parseParametersWithBean(request, parameterType);
			}
		}
		return objs;
	}
	
	//判断是否为基本数据类型
	private boolean isBaseType(Class<?> clazz){
		return clazz.equals(int.class) || clazz.equals(Integer.class)
				|| clazz.equals(long.class) || clazz.equals(Long.class)
				|| clazz.equals(double.class) || clazz.equals(Double.class)
				|| clazz.equals(String.class);
	}
	
	
	

	private void handleActionMethodReturn(HttpServletRequest request,
			HttpServletResponse response,FilterChain chain, Object actionMethodResult,boolean isResponseBody) {
		// TODO Auto-generated method stub
		
		//返回JSON数据
		if(isResponseBody){
			WebUtil.writeJSON(response, actionMethodResult);
			return;
		}
		
		if(actionMethodResult instanceof Page){
			// 若为 Page 类型，则 转发 或 重定向 到相应的页面中
			Page page = (Page) actionMethodResult;
			if (page.getType().equals(Page.REDIRECT)) {
                // 获取路径
                String path = page.getPath();
                //让浏览器重定向到页面
                WebUtil.redirectRequest(path, request, response);
                
                /*//重定向到另外一个action
                if(page.isRedirect()){
                	actionHandler(request,response,chain,page.getAtionPath());
                }else{
                	// 重定向请求
                    WebUtil.redirectRequest(path, request, response);
                }*/
                
            }else {
            	// 获取路径
            	String jspPath = "";
                String path = jspPath + page.getPath();
                // 初始化请求属性
                Map<String, Object> data = page.getData();
                if (data!=null&&data.size()>0) {
                    for (Map.Entry<String, Object> entry : data.entrySet()) {
                        request.setAttribute(entry.getKey(), entry.getValue());
                    }
                }
                //重定向到另外一个action
                if(page.isRedirect()){
                	actionHandler(request,response,chain,page.getAtionPath());
                }else{
                	// 转发请求
                    WebUtil.forwardRequest(path, request, response);
                }
            }
		}else if(actionMethodResult instanceof ErrorPage){//错误页面的处理
			ErrorPage page = (ErrorPage) actionMethodResult;
			// 获取路径
        	String jspPath = "";
            String path = jspPath + "/WEB-INF/jsp/genhoo/genhooErrorPage.jsp";
            request.setAttribute("exception", page.getException());
            Map<String, Object> data = page.getData();
            if (data!=null&&data.size()>0) {
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
            }
            //跳转到错误页面
            WebUtil.forwardRequest(path, request, response);
        }
		
	}

	private void printActionMapping(Map<String, ActionBean> actionMapping) {
		// TODO Auto-generated method stub
		Set<Entry<String, ActionBean>>  set = actionMapping.entrySet();
		
		for (Entry<String, ActionBean> entry:set) {
			ActionBean actionBean = entry.getValue();
			System.out.println("path:"+entry.getKey()+";class:"+actionBean.getActionClass()+";method:"+actionBean.getActionMethod().getName());
		}
		
	}

	//判断是否为静态资源，.html,.jsp,.png,.gif,.jpg,.swf
	private boolean isStaticResource(String currentRequestPath) {
		// TODO Auto-generated method stub
		String staticResourceReg = ".+\\.((html)|(htm)|(jsp)|(png)|(gif)|(jpg)|(swf)|(css)|(js))$";
		Matcher requestPathMatcher = Pattern.compile(staticResourceReg).matcher(currentRequestPath);
		//int count = requestPathMatcher.groupCount();
		boolean ismatcher = requestPathMatcher.matches();
		
//		System.out.println("是否匹配:"+ismatcher);
//		System.err.println(count);
//		for(int i=0;i<count;i++){
//			if(ismatcher)
//			System.out.println(requestPathMatcher.group(i));
//		}
		
//		//得到匹配的静态资源类型
//		if(ismatcher){
//			String staticResourceType = requestPathMatcher.group(1);
//			System.out.println("静态资源类型为："+staticResourceType);
//		}
		 
		
		return ismatcher;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("actionMap 初始化:");
		Map<String, ActionBean> actionMap = ActionHelper.getActionMap();
		System.out.println("初始化完毕");
		//printActionMapping(actionMap);
	}
	
	// 得到请求路径
    public static String getRequestPath(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String pathInfo = StringUtils.defaultIfEmpty(request.getPathInfo(), "");
        return servletPath + pathInfo;
    }

}
