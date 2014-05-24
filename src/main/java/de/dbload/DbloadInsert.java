/*
 * Copyright 2014 Andre Winkler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.dbload;

import java.io.Closeable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;

import de.dbload.jdbc.JdbcUtils;
import de.dbload.jdbc.SqlInsertStatement;
import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.meta.TableMetaData;
import de.dbload.misc.DateTimeUtils;

/**
 * Load data.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DbloadInsert implements Closeable {

    private SqlInsertStatement sqlStatement;
    private PreparedStatement stmt;
    private TableMetaData tableMetaData;

    public DbloadInsert(DbloadContext _context, TableMetaData _tableMetaData)
	    throws SQLException {

	tableMetaData = _tableMetaData;
	stmt = prepareStatement(_context, _tableMetaData);
    }

    private PreparedStatement prepareStatement(DbloadContext _context,
	    TableMetaData tableMetaData) throws SQLException {

	sqlStatement = new SqlInsertStatement(tableMetaData);
	return _context.getConnection().prepareStatement(sqlStatement.getSql());
    }

    public void insert(DataRow data) throws SQLException {
	applyParams(data, tableMetaData, stmt);
	stmt.execute();
    }

    private Number toNumber(final String value) {
        Number number;
        try {
            number = parseNumber(value);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        return number;
    }

    private Number parseNumber(final String value) throws ParseException {
        DecimalFormat decimalFormat = createNumberFormatter();
        return (decimalFormat.parse(value));
    }

    /** Das default-mäßige Format. */
    public static final String DEFAULT_DECIMAL_FORMAT = "###,###.##";

    /** Das Format für Währungsanzeigen. */
    public static final String DEFAULT_ZERO_FORMAT = "##,##0.00";

    /** the empty string (placeholder) */
    private static final String EMPTY = "";

    /** Das Default-Locale. */
    private Locale locale = Locale.getDefault();

    /** Das Default-Pattern. */
    private String pattern = DEFAULT_DECIMAL_FORMAT;

    private DecimalFormat createNumberFormatter() {
        DecimalFormat decimalFormat =
                (DecimalFormat) NumberFormat.getNumberInstance(locale);
        decimalFormat.applyPattern(pattern);
        return decimalFormat;
    }

    private void applyParams(DataRow data, TableMetaData tableMetaData,
	    PreparedStatement stmt) throws SQLException {

	int index = 0;
	for (ColumnMetaData columnMetaData : tableMetaData.getColumns()) {
	    String value = data.get(columnMetaData.getColumnName());

	    switch (columnMetaData.getColumnType()) {
	    case STRING:
		if (value == null) {
		    stmt.setNull(index, java.sql.Types.VARCHAR);
		} else {
		    stmt.setString(index, value);
		}
		break;
	    case NUMBER:
		if (value == null) {
		    BigDecimal number = BigDecimal.
		    stmt.setBigDecimal(index, value);
		}
	    default:
		stmt.setObject(index, value);
	    }
	}

	stmt.setString(1, "Hallo");

	DateTime jodaDateTime = DateTimeUtils.toJodaDateTime("20140324060500");
	Date date = jodaDateTime.toDate();

	java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(date.getTime());

	stmt.setInt(1, 1);
	stmt.setString(2, "winkler");
	stmt.setString(3, "andre");
	stmt.setInt(4, 43);
	stmt.setInt(5, 0);
	stmt.setTimestamp(6, sqlTimestamp);
    }

    public void close() {
	JdbcUtils.close(stmt);
    }

}
