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
import java.sql.Types;
import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import de.dbload.JdbcTypeConverter;
import de.dbload.meta.ColumnMetaData;
import de.dbload.misc.DateTimeUtils;
import de.dbload.misc.NumberUtils;

/**
 * Convert a Java type to the associated JDBC type. Don´t share this converter
 * between different threads (DecimalFormat).
 *
 * @author <a href="http://www.andre-winkler.de">Andre Winkler</a> 
 */
public class DefaultJdbcTypeConverter implements JdbcTypeConverter {

    private static final Integer ONE = Integer.valueOf(1);
    private static final Integer ZERO = Integer.valueOf(0);
    private static final String NULL = "NULL";

    private final DecimalFormat decimalFormat;
    private final ZoneId zoneId;
    private final DateTimeFormatter dateTimeFormatter;

    public DefaultJdbcTypeConverter(
            ZoneId zoneId,
            DateTimeFormatter dateTimeFormatter,
            DecimalFormat decimalFormat
    ) {
        this.zoneId = zoneId;
        this.dateTimeFormatter = dateTimeFormatter;
        this.decimalFormat = decimalFormat;
    }

    /**
     * Konvertiert einen String anhand der Metadaten in das zugeordnete Objekt (String, Number, Boolean oder Date).
     *
     * @see de.dbload.jdbc.JdbcTypeConverter#toSqlValue(de.dbload.meta.ColumnMetaData, java.lang.String)
     */
    @Override
    public Object toSqlValue(ColumnMetaData columnMetaData, String value) {
        Object returnValue = null;

        // TODO NULL-Value wenn die Spalte das erlaubt?

        switch (columnMetaData.getColumnType()) {
        case VARCHAR:
            returnValue = value;
            break;
        case BIT:
            if (value == null) {
                returnValue = ZERO;
            } else {
                switch (value) {
                case "J", "1", "Y":
                    returnValue = ONE;
                    break;
                default:
                    returnValue = ZERO;
                }
            }
            break;
        case BOOLEAN:
            if (value == null) {
                returnValue = Boolean.FALSE;
            } else {
                switch (value) {
                case "J", "1", "Y", "TRUE":
                    returnValue = Boolean.TRUE;
                    break;
                default:
                    returnValue = Boolean.FALSE;
                }
            }
            break;

        case TIME:
        case DATE:
        case DATE_TIME:
            // MYSQL: str_to_date('1971-03-24 06:41:11', '%Y-%m-%d %h:%i:%s')
            if (value != null) {
                var datetime = this.dateTimeFormatter.parse(value);
                var formatter = dateTimeFormatter.withZone(zoneId);
                var datetime2 = formatter.parse(value);
                returnValue = datetime2;
            }
            break;

        case DECIMAL:
        case INTEGER, LONG:
            if (value != null) {
                if (StringUtils.equalsIgnoreCase(value, NULL)) {
                    returnValue = null;
                } else {
                    returnValue = NumberUtils.toNumber(value, decimalFormat);
                }
            }
            break;

        default:
            returnValue = value;
        }
        return returnValue;
    }

    @Override
    public void setSqlVariable(PreparedStatement stmt, int index, ColumnMetaData columnMetaData, Object value)
            throws SQLException {

        switch (columnMetaData.getColumnType()) {
        case VARCHAR:
            if (value == null) {
                stmt.setNull(index, java.sql.Types.VARCHAR);
            } else {
                stmt.setString(index, value.toString());
            }
            break;
        case BIT:
            if (value == null) {
                stmt.setNull(index, java.sql.Types.BIT);
            } else {
                if (value instanceof Number number) {
                    if (number.intValue() == 1) {
                        stmt.setObject(index, Boolean.TRUE, Types.BIT);
                    } else {
                        stmt.setObject(index, Boolean.FALSE, Types.BIT);
                    }
                } else {
                    stmt.setObject(index, value, java.sql.Types.BIT);
                }
            }
            break;
        case BOOLEAN:
            if (value == null) {
                stmt.setNull(index, java.sql.Types.BOOLEAN);
            } else {
                if (value instanceof Boolean bool) {
                    if (bool.booleanValue()) {
                        stmt.setObject(index, Boolean.TRUE, Types.BOOLEAN);
                    } else {
                        stmt.setObject(index, Boolean.FALSE, Types.BOOLEAN);
                    }
                } else {
                    stmt.setObject(index, value, java.sql.Types.BOOLEAN);
                }                
            }
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
                        + columnMetaData.getColumnKey());
            }
            break;

        case INTEGER, LONG:
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
                throw new IllegalStateException(
                    String.format("Unknown number type %s for object %s.", columnMetaData.getColumnType(), value));
            }
            break;

        case DATE, TIME, DATE_TIME:
            if (value == null) {
                stmt.setNull(index, java.sql.Types.TIMESTAMP);
            } else if (value instanceof String) {
                ZonedDateTime date = DateTimeUtils.toZonedDateTime((String) value);
                stmt.setObject(index, date);
            } else if (value instanceof ZonedDateTime) {
                stmt.setObject(index, value);
            } else if (value instanceof OffsetDateTime) {
                stmt.setObject(index, value);
            } else if (value instanceof Date) {
                // TODO This code was created from a drunken train passenger.
                final Date date = (Date) value;
                final java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                final Timestamp timestamp = new Timestamp(sqlDate.getTime());
                stmt.setTimestamp(index, timestamp);
            } else {
                throw new IllegalStateException(
                    String.format("Unknown datetime column type %s and class type %s", columnMetaData.getColumnKey().getColumnName(), value.getClass().getName()));
            }
            // MYSQL str_to_date('1971-03-24 06:41:11', '%Y-%m-%d %h:%i:%s')
            break;

        default:
            if (value == null) {
                // TODO Is this ok? I don´t know. Remove this todo if everything
                // works.
                stmt.setNull(index, java.sql.Types.NULL);
            } else {
                stmt.setObject(index, value);
            }
            break;
        }
    }

}
