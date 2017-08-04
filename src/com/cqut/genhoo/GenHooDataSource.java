package com.cqut.genhoo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.cqut.dbutils.DbUtils;
import com.cqut.dbutils.Loader;

/**
* 项目名称：recruitSimple
* 类名称：GenHooDataSource
* 类描述：这里为了得到数据库连接，只是简单的实现了DataSource接口   
* 创建人：胡均
* 创建时间：2014-1-2 下午6:12:42
* 修改人：胡均
* 修改时间：2014-1-2 下午6:12:42
* 修改备注：   
* @version 1.0
* Copyright (c) 2014 ChongQing University Of Technology
 */
public class GenHooDataSource implements DataSource {

	private static String username;
	private static String password;
	private static String url;
	private static String driverClassName;
	private static String jdbcConfig = "jdbc.properties";
	
	private static final GenHooDataSource instance = new GenHooDataSource();
	
	private GenHooDataSource(){
		try {
			Map<String,String> mapPropterties = Loader.instance().load(jdbcConfig);
			username = mapPropterties.get("username");
			password = mapPropterties.get("password");
			url=  mapPropterties.get("url");
			driverClassName = mapPropterties.get("driverClassName");
			//Class.forName(arg0)
			boolean loadDriverOK = DbUtils.loadDriver(driverClassName);
			if(loadDriverOK){
				System.out.println("initial Driver :"+driverClassName +" successfully");
			}else{
				System.out.println("initial Driver :"+driverClassName +" error");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static GenHooDataSource instance(){
		return instance;
	}
	
	
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	@Override
	public void setLogWriter(PrintWriter arg0) throws SQLException {

	}

	@Override
	public void setLoginTimeout(int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		return null;
	}

	@Override
	public Connection getConnection() throws SQLException {
		Connection con = DriverManager.getConnection(url, username, password);
		return con;
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

}
