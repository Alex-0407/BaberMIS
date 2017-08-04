package com.cqut.genhoo.base;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.cqut.dbutils.Query;
import com.cqut.dbutils.ResultSetHandler;
import com.cqut.dbutils.handers.BeanListHandler;
import com.cqut.dbutils.handers.MapListHandler;
import com.cqut.dbutils.handers.ScalarHandler;
import com.cqut.genhoo.GenHooDataSource;
import com.cqut.genhoo.Page;
import com.cqut.genhoo.SysConstant;
import com.cqut.genhoo.util.EntityUtil;

public class BaseDao {
	
	public static DataSource dataSource = GenHooDataSource.instance();
	
	private Query query = new Query(dataSource);
	
	//定义一个局部线程（使得每个线程都有自己的连接）
	private static ThreadLocal<Connection> connContainer = new ThreadLocal<Connection>();
	
	public BaseDao() {
		
	}
	
	public Query getQuery(){
		return query;
	}
	
	/**
	* @Title: getConnetion 
	* @Description: 得到数据库连接
	* @return 
	* @return Connection
	 * @throws SQLException 
	 */
	public Connection getConnetion() throws SQLException{
		return dataSource.getConnection();
	}
	
	
	public DataSource getDataSource(){
		return dataSource;
	}

	//对于查询是不需要做事务处理的，因此，在查询语句里面没有加入事务处理的逻辑
	
	public List<Map<String, Object>> query(String sql) throws SQLException {
		return query.query(sql, new MapListHandler());
	}

	public List<Map<String, Object>> query(String sql, Object[] params) throws SQLException {
		return query.query(sql, new MapListHandler(),params);
	}
	
	public List<Map<String, Object>> query(String sql, Map<String, Object> params) throws SQLException {
		// TODO Auto-generated method stub
		return query.query(sql, new MapListHandler(), params);
		
	}

	public <T> List<T> query(String sql, Class<T> clazz) throws SQLException {
		return query.query(sql, new BeanListHandler<T>(clazz));
	}

	public <T> List<T> query(String sql, Object[] params, Class<T> clazz) throws SQLException {
		return query.query(sql, new BeanListHandler<T>(clazz),params);
	}
	
	public <T> List<T> query(String sql, Map<String, Object> params,Class<T> clazz) throws SQLException {
		return query.query(sql, new BeanListHandler<T>(clazz), params);
	}

	
	public <T> T query(String sql,ResultSetHandler<T> rsh,Map<String,Object> params) throws SQLException{
		return query.query(sql, rsh, params);
	}
	
	public <T> T query(String sql,ResultSetHandler<T> rsh,Object[] params) throws SQLException{
		return query.query(sql, rsh, params);
	}

	public <T> T queryWithScalar(String sql, Map<String, Object> params,Class<T> clazz) throws SQLException {
		return query.query(sql, new ScalarHandler<T>(), params);
	}


	public <T> T queryWithScalar(String sql, Map<String, Object> params,Class<T> clazz, int columnIndex) throws SQLException {
		return query.query(sql, new ScalarHandler<T>(columnIndex), params);
	}


	public <T> T queryWithScalar(String sql, Map<String, Object> params,Class<T> clazz, String columnName) throws SQLException {
		return query.query(sql, new ScalarHandler<T>(columnName), params);
	}


	public <T> T queryWithScalarArrayPrarms(String sql, Object[] params,Class<T> clazz) throws SQLException {
		return query.query(sql, new ScalarHandler<T>(), params);
	}


	public <T> T queryWithScalarArrayPrarms(String sql, Object[] params,Class<T> clazz, int columnIndex) throws SQLException {
		return query.query(sql, new ScalarHandler<T>(columnIndex), params);
	}


	public <T> T queryWithScalarArrayPrarms(String sql, Object[] params,
			Class<T> clazz, String columnName) throws SQLException {
		return query.query(sql, new ScalarHandler<T>(columnName), params);
	}
	
	/*考虑到要做事务的处理，传递connetion 对象给 update方法*/
	
	public int update(String sql) throws SQLException {
		Connection conn = connContainer.get();//在做更新操作的时候有事务处理的逻辑
		if(conn == null){
			return query.update(sql);
		}else {
			return query.update(conn, sql);
		}
	}

	public int update(String sql, Object[] params) throws SQLException {
		Connection conn = connContainer.get();//在做更新操作的时候有事务处理的逻辑
		if(conn == null){
			return query.update(sql,params);
		}else {
			return query.update(conn, sql,params);
		}
	}

	public int update(String sql, Object param) throws SQLException {
		Connection conn = connContainer.get();//在做更新操作的时候有事务处理的逻辑
		if(conn == null){
			return query.update(sql,param);
		} else {
			return query.update(conn,sql,param);
		}
		
	}
	
	public int update(String sql, Map<String, Object> params) throws SQLException {
		Connection conn = connContainer.get(); //在做更新操作的时候有事务处理的逻辑
		if(conn == null){
			return query.update(sql, params);
		} else {
			return query.update(conn,sql, params);
		}
	}

	public int[] batch(String sql, Object[][] params) throws SQLException {
		Connection conn = connContainer.get(); //在做更新操作的时候有事务处理的逻辑
		if(conn == null){
			return query.batch(sql, params);
		} else {
			return query.batch(conn,sql, params);
		}
		
	}
	
	/**
	 * 
	 * 对上面的结果Dao 做简单的封装
	 * @throws SQLException 
	 * */
	
	//默认插入全部属性
	public <T> int insert(T t) throws SQLException{
		List<String> paramName = new ArrayList<String>();
		List<Object> paramValue = new ArrayList<Object>();
		
		Class<?> clazz = t.getClass();
		String simpleName = clazz.getSimpleName();
		String id = getId(clazz);
		
		Field[] fields = clazz.getDeclaredFields();
		
		for (Field field : fields) {
			if(!field.getName().equals(id) && !field.getName().equalsIgnoreCase("serialVersionUID")){
				field.setAccessible(true);
				paramName.add(field.getName());
				try {
					paramValue.add(field.get(t));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		
		String sql = createInsertSqlWithTableName(simpleName,paramName.toArray(new String[]{}));
		
		int count = this.update(sql, paramValue.toArray());
		return count;
	}
	
	//插入该对象的指定属性到数据库
	public <T> int insert(T t,String[] fields) throws SQLException{
		String sql = createInsertSql(t,fields);
		
		System.out.println(sql);
		
		Object[] params = getObjects(t,fields);
		
		return this.update(sql, params);
	}
	
	private static <T> String createInsertSql(T t,String[] fields){
		Class<?> clazz = t.getClass();
		StringBuffer sql = new StringBuffer("insert into "+clazz.getSimpleName()+"(");
		
		String fildsStr = Arrays.toString(fields);
		fildsStr = fildsStr.replace("[", "");
		fildsStr = fildsStr.replace("]", "");
		
		sql.append(fildsStr);
		sql.append(") values(");
		sql.append(createQuestionMark(fields.length));
		sql.append(")");
		
		return sql.toString();
		
	}
	
	private static String createInsertSqlWithTableName(String tableName,String[] fields){
		StringBuffer sql = new StringBuffer("insert into "+tableName+"(");
		
		String fildsStr = Arrays.toString(fields);
		fildsStr = fildsStr.replace("[", "");
		fildsStr = fildsStr.replace("]", "");
		
		sql.append(fildsStr);
		sql.append(") values(");
		sql.append(createQuestionMark(fields.length));
		sql.append(")");
		
		return sql.toString();
	}
	
	//拼接 length 个问号，每个问号用户','分割
	private static String createQuestionMark(int length){
		StringBuffer buffer = new StringBuffer();
		for(int i=0;i<length;i++){
			buffer.append("?,");
		}
		String str = buffer.toString();
		return str.substring(0, str.length()-1);
	}
	
	//得到一个对象里面的给定 field 的属性值，把这个值返回放在一个Object[] 中
	private <T> Object[] getObjects(T t,String[] fields){
		Class<?> clazz = t.getClass();
		List<Object> list = new ArrayList<Object>();
		
		try {
			for (int i = 0; i < fields.length; i++) {
				Field field = clazz.getDeclaredField(fields[i]);
				field.setAccessible(true);
				Object o = field.get(t);
				list.add(o);
			}
			
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch(SecurityException e){
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return list.toArray();
		
	}
	
	//更新t对象指定的属性字段。
	public <T> int modify(T t,String[] fields) throws SQLException{
		
		Class<?> clazz = t.getClass();
		String simpleName = clazz.getSimpleName();
		
		String id = getId(clazz);
		StringBuffer sql = new StringBuffer();
		
		sql.append("update " +simpleName +" set ");
		sql.append(createSetStr(fields));
		
		try {
			Field idField = clazz.getDeclaredField(id);
			idField.setAccessible(true);
			sql.append(" where "+id+"="+idField.get(t));
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		Object[] params = getObjects(t,fields);
		return this.update(sql.toString(), params);
	}
	
	//拼接命名查询时候的参数 
	private static String createSetStr(String[] fields){
		String setStr = Arrays.toString(fields);
		setStr = setStr.replace("[", "");
		setStr = setStr.replace("]", "");
		setStr = setStr.replace(",", "=?,");
		setStr+="=?";
		return setStr;
	}
	
	/**
	* @Title: queryWithList 
	* @Description: 分页查询一个对象 
	* @param clazz
	* @param index
	* @param limit
	* @return 
	* @return Page<T>
	 * @throws SQLException 
	 */
	public <T> Page<T> queryWithLimt(Class<T> clazz,int index, int limit) throws SQLException{
		
		if(index<1){
			index = 1;
		}
		
		if(limit<=0){
			limit = SysConstant.DEFAULT_LIMT_SIZE;
		}
		int mysqlIndex = (index-1)*limit;
		
		String sizeSql = "select count('"+getId(clazz)+"') from "+clazz.getSimpleName(); 
		
		Long count = this.queryWithScalar(sizeSql, new HashMap<String,Object>(), Long.class);
		
		//System.out.println(count+":::count");
		
		String sql = "select * from "+clazz.getSimpleName()+" limit ?,?";
		
		List<T> list = this.query(sql, new Object[]{mysqlIndex,limit}, clazz);
		
		Page<T> page = new Page<T>();
		page.setDataList(list);
		page.setIndex(index);
		page.setTotal(Integer.parseInt(count.toString()));
		return page;
		
	}
	
	public <T> Page<T> queryWithLimit(Class<T> clazz,String sizeSql,String sql,Object[] params,int index, int limit) throws SQLException{
		if(index<1){
			index = 1;
		}
		
		if(limit<=0){
			limit = SysConstant.DEFAULT_LIMT_SIZE;
		}
		int mysqlIndex = (index-1)*limit;
		Long count = this.queryWithScalarArrayPrarms(sizeSql, params, Long.class);
		
		sql = sql +" limit "+mysqlIndex+","+limit;
		List<T> list = this.query(sql, params, clazz);
		
		Page<T> page = new Page<T>();
		page.setDataList(list);
		page.setIndex(index);
		page.setTotal(Integer.parseInt(count.toString()));
		return page;
	}
	
	public <T> Page<T> queryWithLimit(Class<T> clazz,String sizeSql,String sql,Map<String,Object> params,int index, int limit) throws SQLException{
		if(index<1){
			index = 1;
		}
		
		if(limit<=0){
			limit = SysConstant.DEFAULT_LIMT_SIZE;
		}
		int mysqlIndex = (index-1)*limit;
		Long count = this.queryWithScalar(sizeSql, params, Long.class);
		
		sql = sql +" limit "+mysqlIndex+","+limit;
		List<T> list = this.query(sql, params, clazz);
		
		Page<T> page = new Page<T>();
		page.setDataList(list);
		page.setIndex(index);
		page.setTotal(Integer.parseInt(count.toString()));
		return page;
	}
	
	public Page<Map<String,Object>> queryWithLimit(String sizeSql,String sql,Object[] params,int index, int limit) throws SQLException{
		
		if(index<1){
			index = 1;
		}
		
		if(limit<=0){
			limit = SysConstant.DEFAULT_LIMT_SIZE;
		}
		int mysqlIndex = (index-1)*limit;
		Long count = this.queryWithScalarArrayPrarms(sizeSql, params, Long.class);
		sql = sql +" limit "+mysqlIndex+","+limit;
		
		List<Map<String,Object>> listMap = this.query(sql, params);
		
		Page<Map<String,Object>> page = new Page<Map<String,Object>>();
		page.setDataList(listMap);
		page.setIndex(index);
		page.setTotal(Integer.parseInt(count.toString()));
		return page;
		
	}
	
	
	public Page<Map<String,Object>> queryWithLimit(String sizeSql,String sql,Map<String,Object> params,int index, int limit) throws SQLException{
		
		if(index<1){
			index = 1;
		}
		
		if(limit<=0){
			limit = SysConstant.DEFAULT_LIMT_SIZE;
		}
		int mysqlIndex = (index-1)*limit;
		Long count = this.queryWithScalar(sizeSql, params, Long.class);
		sql = sql +" limit "+mysqlIndex+","+limit;
		
		List<Map<String,Object>> listMap = this.query(sql, params);
		
		Page<Map<String,Object>> page = new Page<Map<String,Object>>();
		page.setDataList(listMap);
		page.setIndex(index);
		page.setTotal(Integer.parseInt(count.toString()));
		return page;
		
	}
	
	/**楼上的代码好丑  ，怎么重构呢？？？**/
	
	public static String getId(Class<?> clazz){
		return EntityUtil.getID(clazz);
	}
	
	
	/**
	* @Title: isExist 
	* @Description: 检测  T 类型的数据在数据库里面是否重复了 
	* @param t
	* @param column
	* @param value
	* @return 
	* @return boolean
	 * @throws SQLException 
	 */
	public boolean isExist(Class<?> t,String column,Object value) throws SQLException{
		
		String sizeSql = "select count(`"+getId(t)+"`) from "+t.getSimpleName() +" where `"+column+"`=?"; 
		Long size = this.queryWithScalarArrayPrarms(sizeSql, new Object[]{value}, Long.class);
		
		return size>0;
		
	}
	
	
	/**
	* @Title: isExistEx 
	* @Description: 判断是否存在 除了给定的ID的 数据 
	* @param t
	* @param column
	* @param value
	* @param id
	* @return 
	* @return boolean
	 * @throws SQLException 
	 */
	public boolean isExist(Class<?> t,String column,Object value, Object id) throws SQLException{
		
		String idName=  getId(t);
		
		String sizeSql = "select count(`"+idName+"`) from "+t.getSimpleName() +" where `"+column+"`=? and `"+idName+"`!=?"; 
		Long size = this.queryWithScalarArrayPrarms(sizeSql, new Object[]{value,id}, Long.class);
		
		return size>0;
		
	}
	
	
	
	
	/**
	* @Title: getEntityByID 
	* @Description: 根据ID查询一个对象 
	* @param t 需要查询的对象
	* @param id  主键的值
	* @return 
	* @return T
	 * @throws SQLException 
	 */
	public <T> T getEntityByID(Class<T> t,Object id) throws SQLException{
		String sql = "select * from "+t.getSimpleName() + " where `"+getId(t)+"`=?";
		List<T> list = this.query(sql, new Object[]{id}, t);
		return list.size()>0 ? list.get(0) : null;
	}
	
	
	/**
	* @Title: deleteByID 
	* @Description: 根据 ID 删除数据 ，返回删除的数据条数
	* @param t
	* @param id
	* @return 
	* @return int
	 * @throws SQLException 
	 */
	public <T> int deleteByID(Class<T> t,Object id) throws SQLException{
		String sql = "delete from "+t.getSimpleName() + " where `"+getId(t)+"`=?";
		return this.update(sql, new Object[]{id});
	}
	
	/**
	 * 开启事物处理
	 * 该方法在需要做事务处理的时候调用
	 */
	public static void beginTransaction(){
		Connection conn = connContainer.get();
		if(conn == null){
			try {
				conn = dataSource.getConnection();
				conn.setAutoCommit(false);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				connContainer.set(conn);
			}
		}
	}
	
	/**
	 * 提交事务
	 */
	public static void commitTransaction(){
		Connection conn = connContainer.get();
		if(conn != null){
			try {
				conn.commit();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				connContainer.remove();
			}
		}
	}
	
	/**
	 * 回滚事务
	 */
	public static void rollbackTransaction(){
		Connection conn = connContainer.get();
		if(conn != null){
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				connContainer.remove();
			}
		}
	}
	
}
