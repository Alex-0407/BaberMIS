package com.cqut.baber.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

import com.cqut.baber.dao.BusinessDAO;
import com.cqut.genhoo.Page;
import com.cqut.genhoo.base.BaseService;

public class BusinessService extends BaseService {
	private BusinessDAO dao = new BusinessDAO();
	
	public Page<Map<String,Object>> queryOnePageBusiness(int pageIndex, int pageSize,String realName) throws SQLException, UnsupportedEncodingException
	{
		return this.dao.queryOnePageBusiness(pageIndex, pageSize,realName);
	}	 
}
