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

package de.dbload.misc;

import java.sql.Timestamp;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

/**
 * A utility to create date objects.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DateTimeUtils {

    /**
     * Konvertierungsformat {@code Oracle-Date-Type} to {@code String}.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Konvertierungsformat {@code String} to {@code Oracle-Date-Type}
     */
    public static final String ORACLE_DATE_FORMAT = "yyyy-MM-dd HH24:MI:ss";

    private static DateTimeFormatter DEFAULT_FORMATTER_FOR_JODA_DATETIME = new DateTimeFormatterBuilder()
            .appendYear(4, 4).appendLiteral("-").appendMonthOfYear(2)
            .appendLiteral("-").appendDayOfMonth(2).appendLiteral(" ")
            .appendHourOfDay(2).appendLiteral(":").appendMinuteOfHour(2)
            .appendLiteral(":").appendSecondOfMinute(2).toFormatter();

    /**
     * Creates a Joda DateTime object. The following date and time pattern will
     * be used:
     * 
     * <pre>
     * yyyy-MM-dd HH24:MI:ss
     * </pre>
     *
     * @param dateAsString
     *            a String with pattern like 'yyyy-MM-dd HH24:MI:ss'
     * @return A Joda {@link DateTime}
     */
    public static DateTime toJodaDateTime(String dateAsString) {
        DateTime dateTime = DateTime.parse(dateAsString,
                DEFAULT_FORMATTER_FOR_JODA_DATETIME);
        return dateTime;
    }

    /**
     * Convert a JDBC <code>Timestamp</code> object to a Joda
     * <code>DateTime</code> object.
     * 
     * @param timestamp
     *            a JDBC time stamp
     * @return a Joda {@link DateTime}
     */
    public static DateTime toDateJodaTime(Timestamp timestamp) {
        Date date = new Date(timestamp.getTime());
        return new DateTime(date);
    }

    // Timestamp timestamp = resultSet.getTimestamp(i);
    // Date date = new Date(timestamp.getTime());
    // DateTime dateTime = new DateTime(date);
    // print = dateTime.toString(DateTimeUtils.DATE_FORMAT);

    /**
     * Returns the milliseconds of {@link DateTime}.
     * 
     * @param datetime
     *            the Joda date and time object
     * @return milliseconds
     */
    public static long toLong(DateTime datetime) {
        return datetime.toDate().getTime();
    }

    /**
     * Returns the SQL <code>Timestamp</code> for a Joda DateTime object.
     * 
     * @param datetime
     *            the joda date and time object
     * @return a SQL Timestamp.
     */
    public static Timestamp toTimestamp(DateTime datetime) {
        return new Timestamp(datetime.toDate().getTime());
    }

}
