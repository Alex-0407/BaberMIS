package com.cqut.baber.dao;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.cqut.genhoo.Page;
import com.cqut.genhoo.base.BaseDao;

public class TypeDAO extends BaseDao
{
	public Page<Map<String,Object>> queryOnePageTypes(int pageIndex, int pageSize,String name) throws SQLException, UnsupportedEncodingException
	{
		int startIndex = (pageIndex-1)*pageSize;
		String condition="";
		if (name != ""&&name !=null)
		{
			String strQuery = new String(name.getBytes("ISO-8859-1"), "UTF-8");
		    condition = " where name like '%" + strQuery + "%'";
		    System.out.println("-------typename---"+name);
		}
		Page<Map<String,Object>> page = this.queryWithLimit("select count(*) from t_type"+condition,"select * from t_type"+condition,new HashMap<String,Object>(), startIndex, pageSize);
		return page;
	}
}