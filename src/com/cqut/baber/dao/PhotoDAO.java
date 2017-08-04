package com.cqut.baber.dao;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.cqut.genhoo.Page;
import com.cqut.genhoo.base.BaseDao;

public class PhotoDAO extends BaseDao
{
	public Page<Map<String,Object>> queryOnePagePhotos(int pageIndex, int pageSize,String realName) throws SQLException, UnsupportedEncodingException
	{
		int startIndex = (pageIndex-1)*pageSize;
		String condition="";
		if (realName != ""&&realName !=null)
		{
			String strQuery = new String(realName.getBytes("ISO-8859-1"), "UTF-8");
		    condition = " where realName like '%" + strQuery + "%'";
		    System.out.println("-------Photoname---"+realName);
		}
		Page<Map<String,Object>> page = this.queryWithLimit("select count(*) from t_photo"+condition,"select * from t_photo"+condition,new HashMap<String,Object>(), startIndex, pageSize);
		return page;
	}
}