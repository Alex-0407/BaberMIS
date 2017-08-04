package com.cqut.baber.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

import com.cqut.baber.dao.UserDAO;
import com.cqut.genhoo.Page;
import com.cqut.genhoo.base.BaseService;

public class UserService extends BaseService {
	private UserDAO dao = new UserDAO();
	
	public Page<Map<String,Object>> queryOnePageUsers(int pageIndex, int pageSize,String userName) throws SQLException, UnsupportedEncodingException
	{
		return this.dao.queryOnePageUsers(pageIndex, pageSize,userName);
	}	 
}
