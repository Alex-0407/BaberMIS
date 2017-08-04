package com.cqut.baber.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

import com.cqut.baber.dao.AdminDAO;
import com.cqut.genhoo.Page;
import com.cqut.genhoo.base.BaseService;

public class AdminService extends BaseService {
	private AdminDAO dao = new AdminDAO();
	
	public Page<Map<String,Object>> queryOnePageAdmins(int pageIndex, int pageSize,String realName) throws SQLException, UnsupportedEncodingException
	{
		return this.dao.queryOnePageAdmins(pageIndex, pageSize,realName);
	}	 
}
