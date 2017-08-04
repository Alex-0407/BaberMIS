package com.cqut.dbutils.handers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cqut.dbutils.RowProcessor;

/**
 * <code>ResultSetHandler</code> implementation that converts the
 * <code>ResultSet</code> into a <code>List</code> of <code>Object[]</code>s.
 * This class is thread safe.
 *
 * @see org.apache.commons.dbutils.ResultSetHandler
 */
public class ArrayListHandler extends AbstractListHandler<Object[]> {

    /**
     * The RowProcessor implementation to use when converting rows
     * into Object[]s.
     */
    private final RowProcessor convert;

    /**
     * Creates a new instance of ArrayListHandler using a
     * <code>BasicRowProcessor</code> for conversions.
     */
    public ArrayListHandler() {
    	this(ArrayHandler.ROW_PROCESSOR);
    }

    /**
     * Creates a new instance of ArrayListHandler.
     *
     * @param convert The <code>RowProcessor</code> implementation
     * to use when converting rows into Object[]s.
     */
    public ArrayListHandler(RowProcessor convert) {
        super();
        this.convert = convert;
    }


    /**
     * Convert row's columns into an <code>Object[]</code>.
     * @param rs <code>ResultSet</code> to process.
     * @return <code>Object[]</code>, never <code>null</code>.
     *
     * @throws SQLException if a database access error occurs
     * @see org.apache.commons.dbutils.handlers.AbstractListHandler#handle(ResultSet)
     */
    @Override
    protected Object[] handleRow(ResultSet rs) throws SQLException {
        return this.convert.toArray(rs);
    }

}
