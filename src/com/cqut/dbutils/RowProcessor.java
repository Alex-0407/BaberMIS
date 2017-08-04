package com.cqut.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 
* 项目名称：recruitSimple
* 类名称：RowProcessor
* 类描述：   
* 创建人：胡均
* 创建时间：2014-1-1 下午4:13:56
* 修改人：胡均
* 修改时间：2014-1-1 下午4:13:56
* 修改备注：   
* @version 1.0
* Copyright (c) 2014 ChongQing University Of Technology
 */
public interface RowProcessor {
	
	/**
	 * 
	* @Title: toArray 
	* @Description: 
	* 把结果集合的第一行转换为 Object[] 
	* @param rs
	* @return
	* @throws SQLException 
	* @return Object[]
	 */
	Object[] toArray(ResultSet rs) throws SQLException;
	
	/**
	* @Title: toBean 
	* @Description:
	* 	把结果集合的第一行转换为 T 
	* @param rs
	* 	需要被处理的结果集ResultSet
	* @param type
	* 	需要被转换成的类型
	* @return
	* @throws SQLException 
	* @return T
	 */
	<T> T toBean(ResultSet rs, Class<T> type) throws SQLException;
	
	
	/**
	* @Title: toBeanList 
	* @Description: 
	* 	将记过集里面的说有行转为List<T>
	* @param rs
	* @param type
	* @return
	* @throws SQLException 
	* @return List<T>
	 */
	<T> List<T> toBeanList(ResultSet rs, Class<T> type) throws SQLException;
	
	/**
	 * 
	* @Title: toMap 
	* @Description: 
	* Create a <code>Map</code> from the column values in one
    * <code>ResultSet</code> row.  
	* @param rs
	* @return
	* @throws SQLException 
	* @return Map<String,Object>
	 */
	Map<String, Object> toMap(ResultSet rs) throws SQLException;
}
