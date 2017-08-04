package com.cqut.test;

import java.sql.SQLException;

import com.cqut.genhoo.proxy.GenhooProxy;
import com.cqut.test.transaction.RoleTestService;

public class TransactionTest {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		RoleTestService service = GenhooProxy.getInstance().getProxy(RoleTestService.class);
		service.add();
		System.out.println("完成"); 
	}

}
