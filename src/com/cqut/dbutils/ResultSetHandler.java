package com.cqut.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
* 项目名称：recruitSimple <br/>
* 类名称：ResultSetHandler <br/>
* 类描述：<br/>
* 把结果集转换为 T 类型 <br/>
* T - the target type the input ResultSet will be converted to.<br/>
* 在项目中可以实现这个接口将结果集转换为相应的对象。<br/>
* Implementations of this interface convert ResultSets into other objects.   <br/>
* 创建人：胡均<br/>
* 创建时间：2013-12-31 下午8:45:31<br/>
* 修改人：胡均<br/>
* 修改时间：2013-12-31 下午8:45:31<br/>
* 修改备注：   <br/>
* @version 1.0
* Copyright (c) 2013 ChongQing University Of Technology
 */
public interface ResultSetHandler<T> {
	
	/**
	* @Title: handle 
	* @Description: Turn the ResultSet into an Object.
	* @param rs
	* @return 
	* @return T
	* @throws SQLException 
	*/
	T handle(ResultSet rs) throws SQLException;
}
