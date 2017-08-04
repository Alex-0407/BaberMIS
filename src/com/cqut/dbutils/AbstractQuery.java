package com.cqut.dbutils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

/**
* 项目名称：recruitSimple
* 类名称：AbstractQuery
* 类描述：   The base class for Query. This class is thread safe.
* 创建人：胡均
* 创建时间：2014-1-1 下午3:31:02
* 修改人：胡均
* 修改时间：2014-1-1 下午3:31:02
* 修改备注：   
* @version 1.0
* Copyright (c) 2014 ChongQing University Of Technology
 */
public abstract class AbstractQuery {
	
	protected final DataSource ds;
	

	/**
	 * 有些数据库驱动在调用
	 * {@link ParameterMetaData#getParameterType(int) }时会抛出异常(不支持这样的调用)
	 * 我们默认指定不会抛出异常
	 */
	private volatile boolean pmdKnownBroken = false;
	
	
	public AbstractQuery() {
        ds = null;
    }
	
	public AbstractQuery(boolean pmdKnownBroken) {
        this.pmdKnownBroken = pmdKnownBroken;
        ds = null;
    }
	
	public AbstractQuery(DataSource ds) {
        this.ds = ds;
    }
	
	public AbstractQuery(DataSource ds, boolean pmdKnownBroken) {
        this.pmdKnownBroken = pmdKnownBroken;
        this.ds = ds;
    }
	
	/**
	* @Title: 
	* @Description: 
	*  有些数据库驱动不支持{@link ParameterMetaData#getParameterType(int) }; 如果
     * <code>pmdKnownBroken</code> 设置为  true, 我们将不在尝试使用它; 如果设置为false,
     * 我们将尝试它，如果它打破(捕获到异常)，我们会记得不要再使用它。
	* @return 
	* @return boolean
	 */
	public boolean isPmdKnownBroken() {
        return pmdKnownBroken;
    }
	
	/**
     * Returns the <code>DataSource</code> this runner is using.
     * <code>QueryRunner</code> methods always call this method to get the
     * <code>DataSource</code> so subclasses can provide specialized behavior.
     *
     * @return DataSource the runner is using
     */
	public DataSource getDataSource() {
        return this.ds;
    }
	
	
	/**
	* @Title: prepareConnection 
	* @Description: 
	* Factory method that creates and initializes a <code>Connection</code>
	* object. <code>Query</code> methods always call this method to
	* retrieve connections from its DataSource. Subclasses can override this
	* method to provide special <code>Connection</code> configuration if
	* needed. This implementation simply calls <code>ds.getConnection()</code>.
	* @return
	* @throws SQLException 
	* @return Connection
	 */
	protected Connection prepareConnection() throws SQLException {
        if (this.getDataSource() == null) {
            throw new SQLException(
                    "QueryRunner requires a DataSource to be "
                    + "invoked in this way, or a Connection should be passed in");
        }
        return this.getDataSource().getConnection();
    }
	
	/**
	* @Title: prepareStatement 
	* @Description: 
	* Factory method that creates and initializes a
    * <code>PreparedStatement</code> object for the given SQL.
    * <code>QueryRunner</code> methods always call this method to prepare
    * statements for them. Subclasses can override this method to provide
    * special PreparedStatement configuration if needed. This implementation
    * simply calls <code>conn.prepareStatement(sql)</code>.
	* @param conn
	* @param sql
	* @return
	* @throws SQLException 
	* @return PreparedStatement
	 */
	protected PreparedStatement prepareStatement(Connection conn, String sql)
		    throws SQLException {
		        return conn.prepareStatement(sql);
	}
	
	
	/**
	* @Title: fillStatement 
	* @Description: 
	* Fill the <code>PreparedStatement</code> replacement parameters with the
    * given objects.
    * 填充<code>PreparedStatement</code>的替代参数，通过给定的对象。
	* @param stmt
	* 			PreparedStatement to fill
	* @param params
	* 			Query replacement parameters; <code>null</code> is a valid value to pass in.
	* @throws SQLException 
	* 			if a database access error occurs
	* @return void
	 */
	public void fillStatement(PreparedStatement stmt, Object... params)
			throws SQLException {

		// check the parameter count, if we can
		ParameterMetaData pmd = null;
		if (!pmdKnownBroken) {
			pmd = stmt.getParameterMetaData();
			int stmtCount = pmd.getParameterCount();
			int paramsCount = params == null ? 0 : params.length;

			if (stmtCount != paramsCount) {
				throw new SQLException("Wrong number of parameters: expected "
						+ stmtCount + ", was given " + paramsCount);
			}
		}

		// nothing to do here
		if (params == null) {
			return;
		}

		for (int i = 0; i < params.length; i++) {
			if (params[i] != null) {
				stmt.setObject(i + 1, params[i]);
			} else {
				// VARCHAR works with many drivers regardless
				// of the actual column type. Oddly, NULL and
				// OTHER don't work with Oracle's drivers.
				int sqlType = Types.VARCHAR;
				if (!pmdKnownBroken) {
					try {
						/*
						 * It's not possible for pmdKnownBroken to change from
						 * true to false, (once true, always true) so pmd cannot
						 * be null here.
						 */
						sqlType = pmd.getParameterType(i + 1);
					} catch (SQLException e) {
						pmdKnownBroken = true;
					}
				}
				stmt.setNull(i + 1, sqlType);
			}
		}
	}
	
	/**
	* @Title: fillStatementWithBean 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
    * Fill the <code>PreparedStatement</code> replacement parameters with the
    * given object's bean property values.
    * 用给定的Object的属性填充 <code>PreparedStatement</code> 的替代参数
	* @param stmt
	* @param bean
	* @param properties
	* @throws SQLException 
	* @return void
	 */
	public void fillStatementWithBean(PreparedStatement stmt, Object bean,
            PropertyDescriptor[] properties) throws SQLException {
        Object[] params = new Object[properties.length];
        for (int i = 0; i < properties.length; i++) {
            PropertyDescriptor property = properties[i];
            Object value = null;
            Method method = property.getReadMethod();
            if (method == null) {
                throw new RuntimeException("No read method for bean property "
                        + bean.getClass() + " " + property.getName());
            }
            try {
                value = method.invoke(bean, new Object[0]);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Couldn't invoke method: " + method,
                        e);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(
                        "Couldn't invoke method with 0 arguments: " + method, e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Couldn't invoke method: " + method,
                        e);
            }
            params[i] = value;
        }
        fillStatement(stmt, params);
    }
	
	/**
	* @Title: fillStatementWithBean 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* 用给定的Object的属性填充 <code>PreparedStatement</code> 的替代参数
	* @param stmt
	* 			PreparedStatement to fill
	* @param bean
	* 			A JavaBean object
	* @param propertyNames
	* 			An ordered array of property names (these should match the
    *           getters/setters); this gives the order to insert values in the
    *           statement
    *           给定的bean必须有getter和setter方法,数组中参数的顺序必须和想要填充的<code>PreparedStatement</code>
    *           的替代参数的顺序一致。
	* @throws SQLException 
	* @return void
	 */
	public void fillStatementWithBean(PreparedStatement stmt, Object bean,
            String... propertyNames) throws SQLException {
        PropertyDescriptor[] descriptors;
        try {
            descriptors = Introspector.getBeanInfo(bean.getClass())
            .getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new RuntimeException("Couldn't introspect bean "
                    + bean.getClass().toString(), e);
        }
        PropertyDescriptor[] sorted = new PropertyDescriptor[propertyNames.length];
        for (int i = 0; i < propertyNames.length; i++) {
            String propertyName = propertyNames[i];
            if (propertyName == null) {
                throw new NullPointerException("propertyName can't be null: "
                        + i);
            }
            boolean found = false;
            for (int j = 0; j < descriptors.length; j++) {
                PropertyDescriptor descriptor = descriptors[j];
                if (propertyName.equals(descriptor.getName())) {
                    sorted[i] = descriptor;
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new RuntimeException("Couldn't find bean property: "
                        + bean.getClass() + " " + propertyName);
            }
        }
        fillStatementWithBean(stmt, bean, sorted);
    }
	
	
	protected void rethrow(SQLException cause, String sql, Object... params)
			throws SQLException {

		String causeMessage = cause.getMessage();
		if (causeMessage == null) {
			causeMessage = "";
		}
		StringBuffer msg = new StringBuffer(causeMessage);

		msg.append(" Query: ");
		msg.append(sql);
		msg.append(" Parameters: ");

		if (params == null) {
			msg.append("[]");
		} else {
			msg.append(Arrays.deepToString(params));
		}

		SQLException e = new SQLException(msg.toString(), cause.getSQLState(),
				cause.getErrorCode());
		e.setNextException(cause);

		throw e;
	}
	
	
	protected void rethrowMapParams(SQLException cause, String sql, Map<String,Object> params) throws SQLException {
		String causeMessage = cause.getMessage();
		if (causeMessage == null) {
			causeMessage = "";
		}
		StringBuffer msg = new StringBuffer(causeMessage);

		msg.append(" Query: ");
		msg.append(sql);
		msg.append(" Parameters: ");

		if (params == null) {
			msg.append("[]");
		} else {
			msg.append(params.toString());
		}

		SQLException e = new SQLException(msg.toString(), cause.getSQLState(),
				cause.getErrorCode());
		e.setNextException(cause);

		throw e;
	}
	
	
	protected ResultSet wrap(ResultSet rs) {
        return rs;
    }
	
	protected void close(Connection conn) throws SQLException {
        DbUtils.close(conn);
    }
	
	protected void close(Statement stmt) throws SQLException {
        DbUtils.close(stmt);
    }
	
	protected void close(ResultSet rs) throws SQLException {
        DbUtils.close(rs);
    }
	
	public class NamedParamSql{
		private Map<Integer,String> paramsMap=new HashMap<Integer,String>();
		public Map<Integer,String> getParamsMap(){
	        return paramsMap;
	    }
	    public void emptyMap(){
	        paramsMap.clear();
	    }
	    
	    public String parseSql(String sql) {
	 	   
	        String regex = "(:(\\w+))";
	        Pattern p = Pattern.compile(regex);
	        Matcher m = p.matcher(sql);
	        emptyMap();
	        int idx=1;
	        while (m.find()) {
	            //参数名称可能有重复，使用序号来做Key
	            paramsMap.put(new Integer(idx++), m.group(2));
	            
	            //System.out.println(m.group(2));
	        }
	        String result = sql.replaceAll(regex, "?");
	       // log.debug("分析前："+sql);
	        //System.out.println("分析前："+sql);
	        //System.out.println("分析后："+result);
	        return result;
	    }
	    
	    //填充参数
	    public void fillParameters(PreparedStatement stmt,Map<String,Object> pMap) throws SQLException{
	    	// check the parameter count, if we can
			ParameterMetaData pmd = null;
			if (!pmdKnownBroken) {
				pmd = stmt.getParameterMetaData();
				int stmtCount = pmd.getParameterCount();
				int paramsCount = pMap == null ? 0 : pMap.size();

				if (stmtCount != paramsCount) {
					throw new SQLException("Wrong number of parameters: expected "
							+ stmtCount + ", was given " + paramsCount);
				}
			}

			// nothing to do here
			if (pMap == null) {
				return;
			}
	    	
	    	
	    	//boolean result=true;
	        String paramName=null;
	        Object paramValue=null;
	        int idx=1;
			
			for (Iterator<Entry<Integer, String>> itr = paramsMap.entrySet().iterator(); itr.hasNext();) {
				Entry<Integer, String> entry = itr.next();
				paramName = entry.getValue();
				idx = entry.getKey().intValue();
				// 不包含会返回null
				paramValue = pMap.get(paramName);
				if (pMap.containsKey(paramName) && null != paramValue) {
					stmt.setObject(idx, paramValue);
				}else{
					int sqlType = Types.VARCHAR;
					if (!pmdKnownBroken) {
						try {
							sqlType = pmd.getParameterType(idx);
						} catch (SQLException e) {
							pmdKnownBroken = true;
						}
					}
					stmt.setNull(idx, sqlType);
				}

			}
	    }
	    
	}
	
	
	protected void printSql(String sql,Object[] params){
		StringBuffer msg = new StringBuffer(currentDateString() + " 打印SQL:\n");

		msg.append("SQL: ");
		msg.append(sql);
		msg.append("\nParameters: ");

		if (params == null) {
			msg.append("[]");
		} else {
			msg.append(Arrays.deepToString(params));
		}
		
		System.out.println(msg.toString()+"\n");
	}
	
	protected void printSql(String sql,Map<String,Object> params) {
		StringBuffer msg = new StringBuffer(currentDateString() + " 打印SQL:\n");

		msg.append("SQL: ");
		msg.append(sql);
		msg.append("\nParameters: ");

		if (params == null) {
			msg.append("[]");
		} else {
			msg.append(params.toString());
		}
		
		System.out.println(msg.toString()+"\n");
	}
	
	
	private static String currentDateString(){
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format1.format(new Date()); 
	}
	
	
	
}
