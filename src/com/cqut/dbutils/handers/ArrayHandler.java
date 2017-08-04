package com.cqut.dbutils.handers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cqut.dbutils.BasicRowProcessor;
import com.cqut.dbutils.ResultSetHandler;
import com.cqut.dbutils.RowProcessor;

public class ArrayHandler implements ResultSetHandler<Object[]> {

	
	 /**
     * Singleton processor instance that handlers share to save memory.  Notice
     * the default scoping to allow only classes in this package to use this
     * instance.
     */
    static final RowProcessor ROW_PROCESSOR = new BasicRowProcessor();

    /**
     * The RowProcessor implementation to use when converting rows
     * into arrays.
     */
    private final RowProcessor convert;

    /**
     * Creates a new instance of ArrayHandler using a
     * <code>BasicRowProcessor</code> for conversion.
     */
    public ArrayHandler() {
        this(ROW_PROCESSOR);
    }

    /**
     * Creates a new instance of ArrayHandler.
     *
     * @param convert The <code>RowProcessor</code> implementation
     * to use when converting rows into arrays.
     */
    public ArrayHandler(RowProcessor convert) {
        super();
        this.convert = convert;
    }

    /**
     * Places the column values from the first row in an <code>Object[]</code>.
     * @param rs <code>ResultSet</code> to process.
     * @return An Object[] or <code>null</code> if there are no rows in the
     * <code>ResultSet</code>.
     *
     * @throws SQLException if a database access error occurs
     * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
     */
    @Override
    public Object[] handle(ResultSet rs) throws SQLException {
        return rs.next() ? this.convert.toArray(rs) : null;
    }
	
}
