package com.cqut.dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

/**
* 项目名称：recruitSimple<br/>
* 类名称：QueryRunner
* 类描述：用插拔式的方式处理SQL查询和处理ResultSet  
* 创建人：胡均
* 创建时间：2013-12-31 下午8:38:39
* 修改人：胡均
* 修改时间：2013-12-31 下午8:38:39
* 修改备注：   
* @version 1.0
* Copyright (c) 2013 ChongQing University Of Technology
 */
public class Query extends AbstractQuery{
	
	public Query() {
        super();
    }
	
	private boolean printSql = true;
	
	
	
	public boolean isPrintSql() {
		return printSql;
	}


	public void setPrintSql(boolean printSql) {
		this.printSql = printSql;
	}


	public Query(boolean pmdKnownBroken) {
        super(pmdKnownBroken);
    }
	
	
	public Query(DataSource ds) {
        super(ds);
    }
	
	
	public Query(DataSource ds, boolean pmdKnownBroken) {
        super(ds, pmdKnownBroken);
    }
	
	
	public int[] batch(Connection conn, String sql, Object[][] params) throws SQLException {
        return this.batch(conn, false, sql, params);
    }
	
	/**
	* @Title: batch 
	* @Description: 
	* 批量执行SQL INSERT,UPDATE or DELETE.
	* Execute a batch of SQL INSERT, UPDATE, or DELETE queries.
	* @param sql
	* @param params
	* @return 
	* @return int[]
	 */
	public int[] batch(String sql, Object[][] params) throws SQLException {
        Connection conn = this.prepareConnection();
        return this.batch(conn, true, sql, params);
    }
	
	/**
	* @Title: batch 
	* @Description: 
	* Calls update after checking the parameters to ensure nothing is null.
	* 在检查了参数不为空后，执行update
	* @param conn
	* @param closeConn 是否需要关闭数据库连接
	* @param sql
	* @param params
	* @return
	* @throws SQLException 
	* @return int[]
	 */
	private int[] batch(Connection conn, boolean closeConn, String sql, Object[][] params) throws SQLException {
        if (conn == null) {
            throw new SQLException("Null connection");
        }

        if (sql == null) {
            if (closeConn) {
                close(conn);
            }
            throw new SQLException("Null SQL statement");
        }

        if (params == null) {
            if (closeConn) {
                close(conn);
            }
            throw new SQLException("Null parameters. If parameters aren't need, pass an empty array.");
        }

        PreparedStatement stmt = null;
        int[] rows = null;
        try {
            stmt = this.prepareStatement(conn, sql);

            for (int i = 0; i < params.length; i++) {
                this.fillStatement(stmt, params[i]);
                stmt.addBatch();
            }
            rows = stmt.executeBatch();

        } catch (SQLException e) {
            this.rethrow(e, sql, (Object[])params);
        } finally {
            close(stmt);
            if (closeConn) {
                close(conn);
            }
        }

        return rows;
    }
	
	/**
	 * 根据命名参数查询
	 * @param conn  数据库连接
	 * @param closeConn 执行完毕后,是否需要关闭数据库连接
	 * @param sql 数据库操作Sql
	 * @param rsh 结果集处理器
	 * @param params 参数，按照map的形式传递
	 * @return
	 * @throws SQLException
	 */
	private <T> T queryMapParams(Connection conn, boolean closeConn, String sql, ResultSetHandler<T> rsh, Map<String,Object> params)
            throws SQLException {
        if (conn == null) {
            throw new SQLException("Null connection");
        }

        if (sql == null) {
            if (closeConn) {
                close(conn);
            }
            throw new SQLException("Null SQL statement");
        }

        if (rsh == null) {
            if (closeConn) {
                close(conn);
            }
            throw new SQLException("Null ResultSetHandler");
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;
        T result = null;

        try {
        	AbstractQuery.NamedParamSql namedParamSql = new NamedParamSql();
        	String normalSql = namedParamSql.parseSql(sql);
        	
            stmt = this.prepareStatement(conn, normalSql);
            
            namedParamSql.fillParameters(stmt, params);
            
            if(printSql){
            	printSql(sql, params);
            	System.out.println("normalSql:"+normalSql);
            }
            rs = this.wrap(stmt.executeQuery());
            result = rsh.handle(rs);

        } catch (SQLException e) {
            //this.rethrow(e, sql, params);
        	this.rethrowMapParams(e, sql, params);

        } finally {
            try {
                close(rs);
            } finally {
                close(stmt);
                if (closeConn) {
                    close(conn);
                }
            }
        }

        return result;
    }
	
	
	/**
	* @Title: query 
	* @Description: Calls query after checking the parameters to ensure nothing is null.
	* @param conn
	* 	The connection to use for the query call.
	* @param closeConn
	* 	True if the connection should be closed, false otherwise.
	* @param sql
	* 	The SQL statement to execute.
	* @param rsh
	* 	处理结果集的Hander
	* @param params
	* 	An array of query replacement parameters.  Each row in
    * 	this array is one set of batch replacement values.
	* @return
	* @throws SQLException 
	* @return T
	 */
	private <T> T query(Connection conn, boolean closeConn, String sql, ResultSetHandler<T> rsh, Object[] params)
            throws SQLException {
        if (conn == null) {
            throw new SQLException("Null connection");
        }

        if (sql == null) {
            if (closeConn) {
                close(conn);
            }
            throw new SQLException("Null SQL statement");
        }
        

        if (rsh == null) {
            if (closeConn) {
                close(conn);
            }
            throw new SQLException("Null ResultSetHandler");
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;
        T result = null;

        try {
            stmt = this.prepareStatement(conn, sql);
            this.fillStatement(stmt, params);
            if(printSql)
            	printSql(sql, params);
            rs = this.wrap(stmt.executeQuery());
            result = rsh.handle(rs);

        } catch (SQLException e) {
            this.rethrow(e, sql, params);

        } finally {
            try {
                close(rs);
            } finally {
                close(stmt);
                if (closeConn) {
                    close(conn);
                }
            }
        }

        return result;
    }
	
	/**
	* @Title: query 
	* @Description: Execute an SQL SELECT query without any replacement parameters.
	* @param conn
	* @param sql
	* @param rsh
	* @return 
	* @return T
	 */
	public <T> T query(Connection conn, String sql, ResultSetHandler<T> rsh) throws SQLException {
        return this.<T>query(conn, false, sql, rsh, (Object[]) null);
    }
	
	////执行给定参数的SQL，并且将结果集封装成T类型
	public <T> T query(String sql, ResultSetHandler<T> rsh, Object[] params) throws SQLException {
        Connection conn = this.prepareConnection();
        return this.<T>query(conn, true, sql, rsh, params);
    }
	
	//
	public <T> T query(String sql, ResultSetHandler<T> rsh, Map<String,Object> params) throws SQLException {
        Connection conn = this.prepareConnection();
        return this.<T>queryMapParams(conn, true, sql, rsh, params);
    }
	
	//执行没有参数的SQL查询，并且将结果集封装成T。
	public <T> T query(String sql, ResultSetHandler<T> rsh) throws SQLException {
        Connection conn = this.prepareConnection();
        return this.<T>query(conn, true, sql, rsh, (Object[]) null);
    }
	
	//执行给定参数的SQL，并且将结果集封装成T类型
	public <T> T query(Connection conn, String sql, ResultSetHandler<T> rsh, Object[] params) throws SQLException {
        return this.<T>query(conn, false, sql, rsh, params);
    }
	
	
	public <T> T query(Connection conn, String sql, ResultSetHandler<T> rsh, Map<String,Object> params) throws SQLException {
        return this.<T>queryMapParams(conn, false, sql, rsh, params);
    }
	
	
	
	/**
	* @Title: update 
	* @Description: 
	* 	Calls update after checking the parameters to ensure nothing is null.
	* @param conn
	* 	The connection to use for the update call.
	* @param closeConn
	* 	True if the connection should be closed, false otherwise.
	* @param sql
	* 	SQL statement to execute.
	* @param params
	* 	params An array of update replacement parameters.  Each row in
    *   this array is one set of update replacement values.
	* @return
	* @throws SQLException 
	* @return int
	 */
	private int update(Connection conn, boolean closeConn, String sql, Object[] params) throws SQLException {
        if (conn == null) {
            throw new SQLException("Null connection");
        }

        if (sql == null) {
            if (closeConn) {
                close(conn);
            }
            throw new SQLException("Null SQL statement");
        }

        PreparedStatement stmt = null;
        int rows = 0;

        try {
            stmt = this.prepareStatement(conn, sql);
            if(printSql){
            	printSql(sql, params);
            }
            	
            this.fillStatement(stmt, params);
            rows = stmt.executeUpdate();

        } catch (SQLException e) {
            this.rethrow(e, sql, params);

        } finally {
            close(stmt);
            if (closeConn) {
                close(conn);
            }
        }

        return rows;
    }
	
	
	
	/**
	* @Title: updateMapParames 
	* @Description: 
	* 	传递命名参数的形式，执行更细
	* @param conn
	* @param closeConn
	* @param sql
	* @param params
	* @return
	* @throws SQLException 
	* @return int
	 */
	private int updateMapParames(Connection conn, boolean closeConn, String sql, Map<String,Object> params) throws SQLException {
        if (conn == null) {
            throw new SQLException("Null connection");
        }

        if (sql == null) {
            if (closeConn) {
                close(conn);
            }
            throw new SQLException("Null SQL statement");
        }

        PreparedStatement stmt = null;
        int rows = 0;

        try {
        	
        	AbstractQuery.NamedParamSql namedParamSql = new NamedParamSql();
        	//解析命名参数
        	String normalSql = namedParamSql.parseSql(sql);
        	
            stmt = this.prepareStatement(conn, normalSql);
            
            namedParamSql.fillParameters(stmt, params);
            if(printSql){
            	printSql(sql, params);
            	System.out.println("normalSql:"+normalSql);
            }
            rows = stmt.executeUpdate();

        } catch (SQLException e) {
            this.rethrowMapParams(e, sql, params);

        } finally {
            close(stmt);
            if (closeConn) {
                close(conn);
            }
        }

        return rows;
    }
	
	
	
	
	//执行没有参数的SQL INSERT, UPDATE, or DELETE 查询.
	public int update(Connection conn, String sql) throws SQLException {
        return this.update(conn, false, sql, (Object[]) null);
    }
	
	//通过给定的参数执行SQL INSERT, UPDATE, or DELETE 查询.
	public int update(Connection conn, String sql, Object[] params) throws SQLException {
        return update(conn, false, sql, params);
    }
	
	public int update(Connection conn, String sql, Map<String,Object> params) throws SQLException {
        return updateMapParames(conn, false, sql, params);
    }
	
	//只有一个给定的参数执行SQL INSERT, UPDATE, or DELETE 查询.
	public int update(Connection conn, String sql, Object param) throws SQLException {
        return this.update(conn, false, sql, new Object[]{param});
    }
	
	//没有参数，执行SQL INSERT, UPDATE, or DELETE 查询.
	public int update(String sql) throws SQLException {
        Connection conn = this.prepareConnection();
        return this.update(conn, true, sql, (Object[]) null);
    }
	
	//通过给定的参数执行SQL INSERT, UPDATE, or DELETE 查询.
	public int update(String sql, Object[] params) throws SQLException {
        Connection conn = this.prepareConnection();
        return this.update(conn, true, sql, params);
    }
	
	/**
	* @Title: update 
	* @Description: 执行更新操作，传递的参数是按照命名参数的形式
	* @param sql
	* @param params
	* @return
	* @throws SQLException 
	* @return int
	 */
	public int update(String sql, Map<String,Object> params) throws SQLException {
        Connection conn = this.prepareConnection();
        return this.updateMapParames(conn, true, sql, params);
    }
	
	//只有一个给定的参数执行SQL INSERT, UPDATE, or DELETE 查询.
	public int update(String sql, Object param) throws SQLException {
        Connection conn = this.prepareConnection();
        return this.update(conn, true, sql, new Object[]{param});
    }
	
}
