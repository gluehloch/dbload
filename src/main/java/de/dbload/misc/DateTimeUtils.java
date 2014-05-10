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

package de.dbload.misc;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

/**
 * A utility to create date objects.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DateTimeUtils {

    private static DateTimeFormatter DEFAULT_FORMATTER = new DateTimeFormatterBuilder()
            .appendYear(4, 4).appendMonthOfYear(2).appendDayOfMonth(2)
            .appendLiteral(" ").appendHourOfDay(2).appendMinuteOfHour(2)
            .appendSecondOfMinute(2).toFormatter();

    /**
     * Creates a Joda DateTime object.
     *
     * @param dateAsString a String with pattern like 'YYYYMMDDhhmmss'
     * @return A Joda {@link DateTime}
     */
    public static DateTime toJodaDateTime(String dateAsString) {
        DateTime dateTime = DateTime.parse(dateAsString, DEFAULT_FORMATTER);
        return dateTime;
    }

}
