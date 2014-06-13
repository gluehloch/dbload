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

package de.dbload.jdbc;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;

import de.dbload.meta.ColumnMetaData;
import de.dbload.misc.DateTimeUtils;
import de.dbload.misc.NumberUtils;

/**
 * Convert a Java type to the associated JDBC type. Don´t share this converter
 * between different threads (DecimalFormat).
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class JdbcTypeConverter {

    private DecimalFormat decimalFormat;

    /**
     * Creates a type converter with the default locale.
     */
    public JdbcTypeConverter() {
        decimalFormat = NumberUtils.createDecimalFormatter(Locale.getDefault());
    }

    /**
     * Creates a type converter with the specified locale.
     *
     * @param _locale a locale
     */
    public JdbcTypeConverter(Locale _locale) {
        decimalFormat = NumberUtils.createDecimalFormatter(_locale);
    }

    /**
     * Creates a type converter. But here you define your own {@link DecimalFormat}.
     *
     * @param _decimalFormat a decimal formatter
     */
    public JdbcTypeConverter(DecimalFormat _decimalFormat) {
        decimalFormat = _decimalFormat;
    }

    /**
     * Convert a String value to the associated column type.
     *
     * @param columnMetaData meta data of the database column
     * @param value the String value to convert
     * @return the converted value (String, Number, Date)
     */
    public Object convert(ColumnMetaData columnMetaData, String value) {
        Object returnValue = null;
        switch (columnMetaData.getColumnType()) {
        case STRING:
            if (value != null) {
                returnValue = value;
            }
            break;
        case NUMBER_INTEGER:
            if (value != null) {
                returnValue = NumberUtils.toNumber(value, decimalFormat);
            }
            break;
        case NUMBER_DECIMAL:
            if (value != null) {
                returnValue = NumberUtils.toNumber(value, decimalFormat);
            }
            break;
        case DATE:
            if (value != null) {
                DateTime jodaDateTime = DateTimeUtils.toJodaDateTime(value);
                Date date = jodaDateTime.toDate();
                long dateAsLong = date.getTime();
                returnValue = new java.sql.Timestamp(dateAsLong);
            }
            break;
        default:
            returnValue = value;
        }
        return returnValue;
    }

    public void setTypedValue(PreparedStatement stmt, int index,
            ColumnMetaData columnMetaData, Object value) throws SQLException {

        switch (columnMetaData.getColumnType()) {
        case STRING:
            stmt.setString(index, value.toString());
            break;
        case NUMBER_INTEGER:
            if (value instanceof Integer) {
                stmt.setInt(index, (Integer) value);
            } else if (value instanceof Long) {
                stmt.setLong(index, (Long) value);
            } else if (value instanceof Short) {
                stmt.setShort(index, (Short) value);
            } else if (value instanceof Byte) {
                stmt.setByte(index, (Byte) value);
            } else {
                throw new IllegalStateException("Unknown number type "
                        + columnMetaData.getColumnType());
            }
            break;
        case NUMBER_DECIMAL:
            if (value instanceof Float) {
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

        default:
            stmt.setObject(index, value);
            break;
        }
    }

}
