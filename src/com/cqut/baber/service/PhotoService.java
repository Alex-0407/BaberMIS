package com.cqut.baber.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

import com.cqut.baber.dao.PhotoDAO;
import com.cqut.genhoo.Page;
import com.cqut.genhoo.base.BaseService;

public class PhotoService extends BaseService {
	private PhotoDAO dao = new PhotoDAO();
	
	public Page<Map<String,Object>> queryOnePagePhotos(int pageIndex, int pageSize,String realName) throws SQLException, UnsupportedEncodingException
	{
		return this.dao.queryOnePagePhotos(pageIndex, pageSize,realName);
	}	 
}

