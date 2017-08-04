package com.cqut.dbutils.handers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cqut.dbutils.ResultSetHandler;
import com.cqut.dbutils.RowProcessor;


public class BeanHandler<T> implements ResultSetHandler<T> {

	
	private final Class<T> type;
	
	private final RowProcessor convert;
	
	public BeanHandler(Class<T> type) {
        this(type, ArrayHandler.ROW_PROCESSOR);
    }
	
	public BeanHandler(Class<T> type, RowProcessor convert) {
        this.type = type;
        this.convert = convert;
    }
	
	@Override
	public T handle(ResultSet rs) throws SQLException {
		return rs.next() ? this.convert.toBean(rs, this.type) : null;
	}

}
