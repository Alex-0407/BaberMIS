package com.cqut.genhoo.base;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cqut.genhoo.util.EntityUtil;

/**
 * BaseService 封装了基本的操作 增加，删除，编辑，查询
 */
public class BaseService {
	protected BaseDao dao = new BaseDao();
	
	//得到DAO
	public BaseDao getDao() {
		return dao;
	}
	

	//默认插入全部属性
	public <T> int add(T t) throws SQLException{
		return dao.insert(t);
	}
	
	//插入该对象的指定属性到数据库
	public <T> int add(T t,String[] fields) throws SQLException{
		return dao.insert(t, fields);
	}
	
	//更新t对象指定的属性字段。
	public <T> int modify(T t,String[] fields) throws SQLException{
		return dao.modify(t, fields);
	}
	
	//得到实体的ID
	public static String getId(Class<?> clazz){
		return EntityUtil.getID(clazz);
	}
	
	//检测  T 类型的数据在数据库里面是否重复了 
	public boolean isExist(Class<?> t,String column,Object value) throws SQLException{
		return dao.isExist(t, column, value);
	}
	
	//判断是否存在 除了给定的ID的 数据 
	public boolean isExist(Class<?> t,String column,Object value, Object id) throws SQLException{
		return dao.isExist(t, column, value, id);
	}
	
	/**
	* @Title: getEntityByID 
	* @Description: 根据ID查询一个对象 
	* @param t 需要查询的对象
	* @param id  主键的值
	* @return 
	* @return T
	 * @throws SQLException 
	 */
	public <T> T getEntityByID(Class<T> t,Object id) throws SQLException{
		return dao.getEntityByID(t, id);
	}
	
	/**
	 * @Description: 根据 ID 删除数据 ，返回删除的数据条数
	 * @param t
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	public <T> int deleteByID(Class<T> t,Object id) throws SQLException{
		return dao.deleteByID(t, id);
	}
	
	/***将查询结果封装为List<Map<String,Object>>
	 * @throws SQLException *****************/
	
	public List<Map<String, Object>> query(String sql) throws SQLException{
		return dao.query(sql);
	}
	
	public List<Map<String, Object>> query(String sql, Object[] params) throws SQLException{
		return dao.query(sql, params);
	}
	
	public List<Map<String, Object>> query(String sql, Map<String, Object> params) throws SQLException{
		return dao.query(sql, params);
	}
	
	/***将查询结果封装为List<T>
	 * @throws SQLException *****************************/
	
	public <T> List<T> query(String sql, Class<T> clazz) throws SQLException{
		return dao.query(sql, clazz);
	}
	
	public <T> List<T> query(String sql, Object[] params, Class<T> clazz) throws SQLException{
		return dao.query(sql, params, clazz);
	}
	
	public <T> List<T> query(String sql, Map<String, Object> params,Class<T> clazz) throws SQLException{
		return dao.query(sql, params, clazz);
	}
	
	/***update
	 * @throws SQLException **/
	public int update(String sql) throws SQLException{
		return dao.update(sql);
	}
	
	public int update(String sql, Object[] params) throws SQLException{
		return dao.update(sql, params);
	}
	
	public int update(String sql, Object param) throws SQLException{
		return dao.update(sql, param);
	}
	
	public int update(String sql, Map<String, Object> params) throws SQLException{
		return dao.update(sql, params);
	}
	
	public int[] batch(String sql, Object[][] params) throws SQLException{
		return dao.batch(sql, params);
	}
	
	
	/**
	 * 创建树形编码
	 * @throws SQLException 
	 */
	
	public <T> String createTreeCode(Class<T> clazz,String codeClumn,String parentClumn,Object parentID) throws SQLException{
		
		int preLevelLength = 4;
		int step = 1;
		
		String maxSql = "select MAX("+codeClumn+") from "+clazz.getSimpleName()+" where "+parentClumn+"=?";
		String maxCode = this.dao.queryWithScalarArrayPrarms(maxSql, new Object[]{parentID}, String.class);
		
		if(maxCode!=null && !maxCode.equals("")){
			return createCodeWithMaxCode(maxCode,preLevelLength,step);
		}
		
		//得到父亲code
		String pSql = "select `"+codeClumn+"` from "+clazz.getSimpleName()+" where "+EntityUtil.getID(clazz)+"=?";
		String parentCode = this.dao.queryWithScalarArrayPrarms(pSql, new Object[]{parentID}, String.class);
		
		//在现有的等级下面新建一个等级
		if(parentCode!=null && !parentCode.equals("")){
			return parentCode+String.format("%0"+preLevelLength+"d", 0+step);
		}
		//系统里面还没有等级的时候
		else {
			return String.format("%0"+preLevelLength+"d", 0+step);
		}
		
	}
	
	
	public String createCodeWithMaxCode(String maxCode,int preLevelLength,int step){
		int size = maxCode.length();
		int level = size/preLevelLength;
		String preStr =  maxCode.substring(0,(level-1)*preLevelLength);
		String needAddStr = maxCode.substring((level-1)*preLevelLength);
		int toint = Integer.parseInt(needAddStr);
		String addResult = String.format("%0"+preLevelLength+"d", toint+step);
		return preStr+addResult;
	}
	
	/**
	 * 为了适配easyui的数据结构
	 * 
	 * @param page 当前第几页
	 * @param rows 每页显示数量
	 * @param sqlParamters 查询参数
	 * @param margeSqlParamters 查询总的数据条数的参数
	 * @return Map<String,Object>
	 * @throws SQLException 
	 */
	public Map<String,Object> loadPage(int page,int rows,String sql,String sizeSql,Object []sqlParamters,Object []sizeSqlParamters) throws SQLException{
		
		//针对mysql数据库的分页
		int index = (page-1)*rows;
		
		String limitSql = sql + " limit ?,?";
		
		Map<String,Object> easyUiData = new HashMap<String,Object>();
		
		//分页查询
		List<Map<String,Object>> dataList = this.dao.query(limitSql, margeSqlParamters(sqlParamters,index,rows));
		
		//查询总共条数
		Long count = this.dao.queryWithScalarArrayPrarms(sizeSql, sizeSqlParamters, Long.class);
		
		easyUiData.put("total", count);
		easyUiData.put("rows", dataList);
		
		return easyUiData;
	}
	
	
	private Object[] margeSqlParamters(Object []sqlParamters,int index,int rows){
		List<Object> paramList = new ArrayList<Object>();
		for(Object obj:sqlParamters){
			paramList.add(obj);
		}
		paramList.add(index);
		paramList.add(rows);
		return paramList.toArray();
		
	}
	
	/**
	 * 
	 * @param sql
	 * @param params
	 * @param expect  期望影响的行数
	 * @param successMessage  更新成功时候返回的数据
	 * @param unExpectMessage 与期望数据不一样时反悔的信息
	 * @param errorMessage    发生错误的时候反悔的信息
	 * @param ignoreCount     是否忽略影响的行数
	 * @return
	 */
	public Map<String,Object> exeUpdateSql(String sql,Object[] params,int expect,String successMessage,String unExpectMessage,String errorMessage,boolean ignoreCount){
		
		Map<String,Object> rm = new HashMap<String,Object>();
		
		try {
			
			int count = this.update(sql, params);
			
			if(ignoreCount || count == expect){
				rm.put("OK", true);
				rm.put("message", successMessage);
			} else {
				rm.put("OK", false);
				rm.put("message", unExpectMessage);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			rm.put("OK", false);
			if(errorMessage!=null){
				rm.put("message", errorMessage);
			}else{
				rm.put("message", e.getMessage());
			}
		}
		return rm;
	}
	
}
