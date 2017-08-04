package com.cqut.baber.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

import com.cqut.baber.dao.TypeDAO;
import com.cqut.genhoo.Page;
import com.cqut.genhoo.base.BaseService;

public class TypeService extends BaseService {
	private TypeDAO dao = new TypeDAO();
	
	public Page<Map<String,Object>> queryOnePageTypes(int pageIndex, int pageSize,String name) throws SQLException, UnsupportedEncodingException
	{
		return this.dao.queryOnePageTypes(pageIndex, pageSize,name);
	}	 
}
