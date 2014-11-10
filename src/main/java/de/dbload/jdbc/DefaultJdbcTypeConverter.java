/*
 * Copyright 2014 Andre Winkler
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package de.dbload.jdbc;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;

import de.dbload.JdbcTypeConverter;
import de.dbload.meta.ColumnMetaData;
import de.dbload.misc.DateTimeUtils;
import de.dbload.misc.NumberUtils;

/**
 * Convert a Java type to the associated JDBC type. Don´t share this converter
 * between different threads (DecimalFormat).
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DefaultJdbcTypeConverter implements JdbcTypeConverter {

    private DecimalFormat decimalFormat;

    /**
     * Creates a type converter with the default locale.
     */
    public DefaultJdbcTypeConverter() {
        decimalFormat = NumberUtils.createDecimalFormatter(Locale.getDefault());
    }

    /**
     * Creates a type converter with the specified locale.
     *
     * @param locale
     *            a locale
     */
    public DefaultJdbcTypeConverter(Locale locale) {
        decimalFormat = NumberUtils.createDecimalFormatter(locale);
    }

    /**
     * Creates a type converter. But here you define your own
     * {@link DecimalFormat}.
     *
     * @param _decimalFormat
     *            a decimal formatter
     */
    public DefaultJdbcTypeConverter(DecimalFormat _decimalFormat) {
        decimalFormat = _decimalFormat;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.dbload.jdbc.JdbcTypeConverter#convert(de.dbload.meta.ColumnMetaData,
     * java.lang.String)
     */
    @Override
    public Object convert(ColumnMetaData columnMetaData, String value) {
        Object returnValue = null;
        switch (columnMetaData.getColumnType()) {
        case VARCHAR:
            returnValue = value;
            break;
        case BIT:
            System.out.println("Find a bit!!!" + columnMetaData + " " + value);
            // TODO BIT?
            break;

        case TIME:
        case DATE:
        case DATE_TIME:
            // MYSQL: str_to_date('1971-03-24 06:41:11', '%Y-%m-%d %h:%i:%s')
            if (value != null) {
                DateTime jodaDateTime = DateTimeUtils.toJodaDateTime(value);
                Date date = jodaDateTime.toDate();
                long dateAsLong = date.getTime();
                returnValue = new java.sql.Timestamp(dateAsLong);
            }
            break;

        case DECIMAL:
        case INTEGER:
            if (value != null) {
                returnValue = NumberUtils.toNumber(value, decimalFormat);
            }
            break;
        case LONG:
            if (value != null) {
                returnValue = NumberUtils.toNumber(value, decimalFormat);
            }
            break;

        default:
            returnValue = value;
        }
        return returnValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.dbload.jdbc.JdbcTypeConverter#setTypedValue(java.sql.PreparedStatement
     * , int, de.dbload.meta.ColumnMetaData, java.lang.Object)
     */
    @Override
    public void setTypedValue(PreparedStatement stmt, int index,
            ColumnMetaData columnMetaData, Object value) throws SQLException {

        switch (columnMetaData.getColumnType()) {
        case VARCHAR:
            if (value == null) {
                stmt.setNull(index, java.sql.Types.VARCHAR);
            } else {
                stmt.setString(index, value.toString());
            }
            break;
        case BIT:
            System.out.println("Find a bit!!!" + columnMetaData + " " + value);
            break;

        case DECIMAL:
            if (value == null) {
                stmt.setNull(index, java.sql.Types.DECIMAL);
            } else if (value instanceof Float) {
                stmt.setFloat(index, ((Float) value));
            } else if (value instanceof Double) {
                stmt.setDouble(index, ((Double) value));
            } else if (value instanceof BigDecimal) {
                stmt.setBigDecimal(index, ((BigDecimal) value));
            } else {
                throw new IllegalStateException("Unknown decimal type "
                        + columnMetaData.getColumnName());
            }
            break;
        case INTEGER:
        case LONG:
            if (value == null) {
                stmt.setNull(index, java.sql.Types.BIGINT);
            } else if (value instanceof Integer) {
                stmt.setInt(index, (Integer) value);
            } else if (value instanceof Long) {
                stmt.setLong(index, (Long) value);
            } else if (value instanceof Short) {
                stmt.setShort(index, (Short) value);
            } else if (value instanceof Byte) {
                stmt.setByte(index, (Byte) value);
            } else {
                throw new IllegalStateException("Unknown number type "
                        + columnMetaData.getColumnType() + " for object "
                        + value);
            }
            break;

        case DATE:
        case TIME:
        case DATE_TIME:
            if (value == null) {
                stmt.setNull(index, java.sql.Types.TIMESTAMP);
            } else if (value instanceof String) {
                Date date = DateTimeUtils.toJodaDateTime((String) value)
                        .toDate();
                java.sql.Timestamp sqlDate = new java.sql.Timestamp(
                        date.getTime());
                stmt.setTimestamp(index, sqlDate);
            } else if (value instanceof Date) {
                // TODO This code was created from a drunken train passenger.
                // It is a good idea to check this tomorrow!
                java.sql.Date sqlDate = new java.sql.Date(
                        ((Date) value).getTime());
                Timestamp timestamp = new Timestamp(sqlDate.getTime());
                stmt.setTimestamp(index, timestamp);
            } else {
                throw new IllegalStateException("Unknown decimal type "
                        + columnMetaData.getColumnName());
            }
            // MYSQL str_to_date('1971-03-24 06:41:11', '%Y-%m-%d %h:%i:%s')
            break;

        default:
            if (value == null) {
                // TODO Is this ok? I don´t know. Remove this todo if everything works.
                stmt.setNull(index, java.sql.Types.NULL);
            } else {
                stmt.setObject(index, value);
            }
            break;
        }
    }

}
