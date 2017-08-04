package com.cqut.test.transaction;

import java.sql.SQLException;

import com.cqut.genhoo.annotation.Transaction;
import com.cqut.genhoo.base.BaseService;

public class RoleTestService extends BaseService{

	@Transaction
	public void add() throws SQLException{
		String sql = "insert into Role(`name`,`remark`) values('test','123')";
		
		String sql2 = "insert into UserRole(`operator`,`role`) values(1,1)";
		
		this.update(sql);
		this.update(sql2);
		
		if(true){
			throw new RuntimeException();
		}
	}
}
