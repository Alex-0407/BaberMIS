package com.cqut.dbutils;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
* 项目名称：recruitSimple
* 类名称：DbUtils
* 类描述：   A collection of JDBC helper methods.  This class is thread safe.
* 创建人：胡均
* 创建时间：2014-1-1 下午2:05:45
* 修改人：胡均
* 修改时间：2014-1-1 下午2:05:45
* 修改备注：   
* @version 1.0
* Copyright (c) 2014 ChongQing University Of Technology
 */
public final class DbUtils {
	
	/**
	* @Title: DbUtils 
	* @Description: 默认的构造函数
	 */
	public DbUtils() {
        // do nothing
    }
	
	/**
	 * 
	* @Title: close 
	* Close a <code>Connection</code>, avoid closing if null.
	* 关闭一个 <code>Connection</code>，并且避免Connection为 null
	* @param conn
	* @throws SQLException if a database access error occurs 
	* @return void
	 */
	public static void close(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
	
	/**
     * Close a <code>ResultSet</code>, avoid closing if null.
     * 关闭一个 <code>ResultSet</code>，并且避免 <code>ResultSet</code> 为 null
     * @param rs ResultSet to close.
     * @throws SQLException if a database access error occurs
     */
    public static void close(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }
    
    /**
     * Close a <code>Statement</code>, avoid closing if null.
     *
     * @param stmt Statement to close.
     * @throws SQLException if a database access error occurs
     */
    public static void close(Statement stmt) throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
    }
	
    
    /**
     * Close a <code>Connection</code>, avoid closing if null and hide
     * any SQLExceptions that occur.
     *
     * @param conn Connection to close.
     */
    public static void closeQuietly(Connection conn) {
        try {
            close(conn);
        } catch (SQLException e) { // NOPMD
            // quiet
        }
    }
    
    
    /**
     * Close a <code>Connection</code>, <code>Statement</code> and
     * <code>ResultSet</code>.  Avoid closing if null and hide any
     * SQLExceptions that occur.
     * 
     * @param conn Connection to close.
     * @param stmt Statement to close.
     * @param rs ResultSet to close.
     */
    public static void closeQuietly(Connection conn, Statement stmt,
            ResultSet rs) {

        try {
            closeQuietly(rs);
        } finally {
            try {
                closeQuietly(stmt);
            } finally {
                closeQuietly(conn);
            }
        }

    }

    /**
     * Close a <code>ResultSet</code>, avoid closing if null and hide any
     * SQLExceptions that occur.
     *
     * @param rs ResultSet to close.
     */
    public static void closeQuietly(ResultSet rs) {
        try {
            close(rs);
        } catch (SQLException e) { // NOPMD
            // quiet
        }
    }

    /**
     * Close a <code>Statement</code>, avoid closing if null and hide
     * any SQLExceptions that occur.
     *
     * @param stmt Statement to close.
     */
    public static void closeQuietly(Statement stmt) {
        try {
            close(stmt);
        } catch (SQLException e) { // NOPMD
            // quiet
        }
    }

    /**
     * Commits a <code>Connection</code> then closes it, avoid closing if null.
     * 在<code>Connection</code>不为null时，提交<code>Connection</code>后关闭它。
     * @param conn Connection to close.
     * @throws SQLException if a database access error occurs
     */
    public static void commitAndClose(Connection conn) throws SQLException {
        if (conn != null) {
            try {
                conn.commit();
            } finally {
                conn.close();
            }
        }
    }

    /**
     * Commits a <code>Connection</code> then closes it, avoid closing if null
     * and hide any SQLExceptions that occur.
     * 在<code>Connection</code>不为null时，提交<code>Connection</code>后关闭它。
     * 隐藏SQLExceptions
     * @param conn Connection to close.
     */
    public static void commitAndCloseQuietly(Connection conn) {
        try {
            commitAndClose(conn);
        } catch (SQLException e) { // NOPMD
            // quiet
        }
    }

    /**
     * Loads and registers a database driver class.
     * If this succeeds, it returns true, else it returns false.
     * 用默认的类加载器加载并注册一个数据库驱动类，如果成果返回true，否则返回false。
     * @param driverClassName of driver to load
     * @return boolean <code>true</code> if the driver was found, otherwise <code>false</code>
     */
    public static boolean loadDriver(String driverClassName) {
        return loadDriver(DbUtils.class.getClassLoader(), driverClassName);
    }

    /**
     * Loads and registers a database driver class.
     * If this succeeds, it returns true, else it returns false.
     * 加载并且注册一个数据库驱动类
     * 如果成果返回true，否则返回false
     * @param classLoader the class loader used to load the driver class
     * @param driverClassName of driver to load
     * @return boolean <code>true</code> if the driver was found, otherwise <code>false</code>
     */
    public static boolean loadDriver(ClassLoader classLoader, String driverClassName) {
        try {
            classLoader.loadClass(driverClassName).newInstance();
            return true;

        } catch (IllegalAccessException e) {
            // Constructor is private, OK for DriverManager contract
            return true;

        } catch (Exception e) {
        	e.printStackTrace();
            return false;

        }
    }

    /**
     * Print the stack trace for a SQLException to STDERR.
     * 用标准错误打印SQLException 的栈信息
     * @param e SQLException to print stack trace of
     */
    public static void printStackTrace(SQLException e) {
        printStackTrace(e, new PrintWriter(System.err));
    }

    /**
     * Print the stack trace for a SQLException to a
     * specified PrintWriter.
     *
     * @param e SQLException to print stack trace of
     * @param pw PrintWriter to print to
     */
    public static void printStackTrace(SQLException e, PrintWriter pw) {

        SQLException next = e;
        while (next != null) {
            next.printStackTrace(pw);
            next = next.getNextException();
            if (next != null) {
                pw.println("Next SQLException:");
            }
        }
    }

    /**
     * Print warnings on a Connection to STDERR.
     * 
     * @param conn Connection to print warnings from
     */
    public static void printWarnings(Connection conn) {
        printWarnings(conn, new PrintWriter(System.err));
    }

    /**
     * Print warnings on a Connection to a specified PrintWriter.
     * 打印在Connection中警告信息通过一个指定的<code>PrintWriter</code>
     * @param conn Connection to print warnings from
     * @param pw PrintWriter to print to
     */
    public static void printWarnings(Connection conn, PrintWriter pw) {
        if (conn != null) {
            try {
                printStackTrace(conn.getWarnings(), pw);
            } catch (SQLException e) {
                printStackTrace(e, pw);
            }
        }
    }

    /**
     * Rollback any changes made on the given connection.
     * 事务回滚
     * @param conn Connection to rollback.  A null value is legal.
     * @throws SQLException if a database access error occurs
     */
    public static void rollback(Connection conn) throws SQLException {
        if (conn != null) {
            conn.rollback();
        }
    }

    /**
     * Performs a rollback on the <code>Connection</code> then closes it,
     * avoid closing if null.
     * 回滚并且关闭事物
     * @param conn Connection to rollback.  A null value is legal.
     * @throws SQLException if a database access error occurs
     */
    public static void rollbackAndClose(Connection conn) throws SQLException {
        if (conn != null) {
            try {
                conn.rollback();
            } finally {
                conn.close();
            }
        }
    }

    /**
     * Performs a rollback on the <code>Connection</code> then closes it,
     * avoid closing if null and hide any SQLExceptions that occur.
     * 回滚并关闭事务，隐藏SQLException
     * @param conn Connection to rollback.  A null value is legal.
     */
    public static void rollbackAndCloseQuietly(Connection conn) {
        try {
            rollbackAndClose(conn);
        } catch (SQLException e) { // NOPMD
            // quiet
        }
    }
	
}
