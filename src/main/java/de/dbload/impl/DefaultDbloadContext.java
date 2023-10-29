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

package de.dbload.impl;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import de.dbload.DbloadContext;
import de.dbload.JdbcTypeConverter;
import de.dbload.jdbc.DefaultJdbcTypeConverter;
import de.dbload.misc.DateTimeUtils;
import de.dbload.misc.NumberUtils;

/**
 * Holds the database connection.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DefaultDbloadContext implements DbloadContext {

    private final Connection conn;
    private final ZoneId zoneId;
    private final DateTimeFormatter dateTimeFormatter;
    private final DecimalFormat decimalFormat;  
    private final JdbcTypeConverter jdbcTypeConverter;

    public static DbloadContext of(Connection connection) {
        return DefaultDbloadContext.of(connection, Locale.getDefault());
    }

    public static DbloadContext of(Connection connection, Locale locale) {
        ZoneId zoneId = ZoneId.systemDefault();
        DateTimeFormatter dateTimeFormatter = DateTimeUtils.DEFAULT_LOCAL_DATETIME_FORMATTER;
        DecimalFormat decimalFormat = NumberUtils.createDecimalFormatter(locale);
        return DefaultDbloadContext.of(connection, zoneId, dateTimeFormatter, decimalFormat);
    }

    public static DbloadContext of(
            Connection connection,
            ZoneId zoneId,
            DateTimeFormatter dateTimeFormatter,
            DecimalFormat decimalFormat) {
        DefaultJdbcTypeConverter jdbcTypeConverter = new DefaultJdbcTypeConverter(zoneId, dateTimeFormatter, decimalFormat);
        return new DefaultDbloadContext(connection, zoneId, dateTimeFormatter, decimalFormat, jdbcTypeConverter);
    }

    private DefaultDbloadContext(
            Connection conn,
            ZoneId zoneId,
            DateTimeFormatter dateTimeFormatter,
            DecimalFormat decimalFormat,
            JdbcTypeConverter jdbcTypeConverter) {
        this.conn = conn;
        this.zoneId = zoneId;
        this.dateTimeFormatter = dateTimeFormatter;
        this.decimalFormat = decimalFormat;
        this.jdbcTypeConverter = jdbcTypeConverter;

    }
    
    @Override
    public Connection connection() {
        return conn;
    }

    @Override
    public JdbcTypeConverter converter() {
        return jdbcTypeConverter;
    }

    @Override
    public ZoneId zoneId() {
        return zoneId;
    }

    @Override
    public DateTimeFormatter dateTimeFormatter() {
        return dateTimeFormatter;
    }

    @Override
    public DecimalFormat decimalFormat() {
        return decimalFormat;
    }

}
