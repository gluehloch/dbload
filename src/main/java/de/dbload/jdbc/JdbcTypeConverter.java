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

import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;

import com.mysql.jdbc.PreparedStatement;

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

    public JdbcTypeConverter() {
        decimalFormat = NumberUtils.createDecimalFormatter(Locale.getDefault());
    }

    public JdbcTypeConverter(Locale _locale) {
        decimalFormat = NumberUtils.createDecimalFormatter(_locale);
    }

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
        case NUMBER:
            if (value != null) {
                returnValue = NumberUtils.toNumber(value, decimalFormat);
            }
            break;
        case DATE:
            if (value != null) {
                DateTime jodaDateTime = DateTimeUtils.toJodaDateTime(value);
                Date date = jodaDateTime.toDate();
                long dateAsLong = date.getTime();
                returnValue = new java.sql.Date(dateAsLong);
            }
            break;
        default:
            returnValue = value;
        }
        return returnValue;
    }

    public void setTypedValue(PreparedStatement stmt, ColumnMetaData columnMetaData, Object value) {
        switch (columnMetaData.getColumnType()) {
        case STRING:
            stmt.setString(columnMetaData.getColumnName(), value.toString());
        }
    }

}
