package com.cqut.baber.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

import com.cqut.baber.dao.StudentDAO;
import com.cqut.genhoo.Page;
import com.cqut.genhoo.base.BaseService;

public class StudentService extends BaseService {
	private StudentDAO dao = new StudentDAO();
	
	public Page<Map<String,Object>> queryOnePageStudents(int pageIndex, int pageSize) throws SQLException
	{
		return this.dao.queryOnePageStudents(pageIndex, pageSize);
	}
	
	public Page<Map<String,Object>> searchOnePageStudents(int pageIndex, int pageSize,String stuname) throws SQLException, UnsupportedEncodingException
	{
		return this.dao.searchOnePageStudents(pageIndex, pageSize,stuname);
	}
	 
}
