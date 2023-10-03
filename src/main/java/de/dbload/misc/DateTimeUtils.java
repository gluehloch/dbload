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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A utility to create date objects.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DateTimeUtils {

    public static final ZoneId ZONE_UTC = ZoneId.of("UTC");
    public static final ZoneId ZONE_EUROPE_BERLIN = ZoneId.of("Europe/Berlin");

    /**
     * Konvertierungsformat {@code Oracle-Date-Type} to {@code String}.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Konvertierungsformat {@code String} to {@code Oracle-Date-Type}
     */
    public static final String ORACLE_DATE_FORMAT = "yyyy-MM-dd HH24:MI:ss";

    /**
     * Format: '2020-03-24 18:10:33'
     */
    public static final DateTimeFormatter DEFAULT_LOCAL_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter DEFAULT_UTC_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    /**
     * Creates a Joda DateTime object. The following date and time pattern will
     * be used:
     *
     * <pre>
     * yyyy-MM-dd HH24:MI:ss
     * </pre>
     *
     * @param dateAsString a String with pattern like 'yyyy-MM-dd HH24:MI:ss'
     * @return toZonedDateTime
     */
    public static LocalDateTime toLocalDateTime(final String dateAsString) {
        return LocalDateTime.parse(dateAsString, DEFAULT_LOCAL_DATETIME_FORMATTER);
    }

    public static ZonedDateTime toZonedDateTim(final String dateAsString) {
        return ZonedDateTime.parse(dateAsString, DEFAULT_UTC_FORMATTER);
    }

    // Timestamp timestamp = resultSet.getTimestamp(i);
    // Date date = new Date(timestamp.getTime());
    // DateTime dateTime = new DateTime(date);
    // print = dateTime.toString(DateTimeUtils.DATE_FORMAT);

}
