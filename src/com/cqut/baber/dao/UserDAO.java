package com.cqut.baber.dao;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.cqut.genhoo.Page;
import com.cqut.genhoo.base.BaseDao;

public class UserDAO extends BaseDao
{
	public Page<Map<String,Object>> queryOnePageUsers(int pageIndex, int pageSize,String userName) throws SQLException, UnsupportedEncodingException
	{
		int startIndex = (pageIndex-1)*pageSize;
		String condition="";
		if (userName != ""&&userName !=null)
		{
			String strQuery = new String(userName.getBytes("ISO-8859-1"), "UTF-8");
		    condition = " where userName like '%" + strQuery + "%'";
		    System.out.println("-------usrname---"+userName);
		}
		Page<Map<String,Object>> page = this.queryWithLimit("select count(*) from t_user"+condition,"select * from t_user"+condition,new HashMap<String,Object>(), startIndex, pageSize);
		return page;
	}
}