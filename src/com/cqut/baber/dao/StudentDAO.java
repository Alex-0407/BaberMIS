package com.cqut.baber.dao;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.cqut.genhoo.Page;
import com.cqut.genhoo.base.BaseDao;

public class StudentDAO extends BaseDao
{
	public Page<Map<String,Object>> queryOnePageStudents(int pageIndex, int pageSize) throws SQLException
	{
		int startIndex = (pageIndex-1)*pageSize;
		Page<Map<String,Object>> page = this.queryWithLimit("select count(*) from student","select * from Student",new HashMap<String,Object>(), startIndex, pageSize);
		return page;
	}
	
	public Page<Map<String,Object>> searchOnePageStudents(int pageIndex, int pageSize,String stuname) throws SQLException, UnsupportedEncodingException
	{
		int startIndex = (pageIndex-1)*pageSize;
		String condition="";
		if (stuname != ""&&stuname !=null)
		{
			String strQuery = new String(stuname.getBytes("ISO-8859-1"), "UTF-8");
		    condition = " where name like '%" + strQuery + "%'";
		    System.out.println("-------stuname---"+stuname);
		}
		Page<Map<String,Object>> page = this.queryWithLimit("select count(*) from student"+condition,"select * from Student"+condition,new HashMap<String,Object>(), startIndex, pageSize);
		return page;
	}
}