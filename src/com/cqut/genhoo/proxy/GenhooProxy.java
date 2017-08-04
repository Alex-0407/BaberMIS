package com.cqut.genhoo.proxy;

import java.lang.reflect.Method;

import com.cqut.genhoo.annotation.Transaction;
import com.cqut.genhoo.base.BaseDao;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 通过cglib创建代理对象
 * 该类是一个工厂，实现了一个单例模式的工厂
 */
public class GenhooProxy implements MethodInterceptor{

	private static GenhooProxy instance = new GenhooProxy();
	
	private GenhooProxy(){
		
	}
	
	public static GenhooProxy getInstance(){
		return instance;
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> T getProxy(Class<T> cls){
		return (T) Enhancer.create(cls, this);
	}
	
	/**
	 * 在这里处理代理的逻辑
	 */
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		
		Object result;
		//处理 事务逻辑
		if(method.isAnnotationPresent(Transaction.class)){
			try {
				// 开启事务
				System.out.println("begin transaction--");
				BaseDao.beginTransaction();
				
				// 执行操作
				method.setAccessible(true);
				result = proxy.invokeSuper(obj, args);
				
				// 提交事务
				BaseDao.commitTransaction();
				System.out.println("commit transaction--");
			} catch (Exception e) {
				// 回滚事务
				BaseDao.rollbackTransaction();
				System.out.println("rollback transaction--");
				e.printStackTrace();
				throw new RuntimeException();
			}
			
		} else {
			result = proxy.invokeSuper(obj, args);
		}
		
		return result;
	}
	
}
