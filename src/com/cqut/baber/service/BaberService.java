package com.cqut.baber.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

import com.cqut.baber.dao.BaberDAO;
import com.cqut.genhoo.Page;
import com.cqut.genhoo.base.BaseService;

public class BaberService extends BaseService {
	private BaberDAO dao = new BaberDAO();
	
	public Page<Map<String,Object>> queryOnePageBabers(int pageIndex, int pageSize,String realName) throws SQLException, UnsupportedEncodingException
	{
		return this.dao.queryOnePageBabers(pageIndex, pageSize,realName);
	}	 
}
