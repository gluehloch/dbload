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

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for class {@link DateTimeUtils}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
class DateTimeUtilsTest {

    @Test
    void parseDateTime() {
        LocalDateTime ldt = LocalDateTime.parse("1971-03-24 06:00:01", DateTimeUtils.DEFAULT_DATETIME_FORMATTER);
        assertThat(ldt.getYear()).isEqualTo(1971);

        Assertions.assertThrows(DateTimeParseException.class, () -> {
            ZonedDateTime.parse("1971-03-24 06:00:01", DateTimeUtils.DEFAULT_DATETIME_FORMATTER);
        });

        System.out.println(DateTimeFormatter.BASIC_ISO_DATE.format(Instant.now()));

        ZonedDateTime zdt2 = ZonedDateTime.parse("1971-03-24 06:00:01", DateTimeUtils.DEFAULT_UTC_FORMATTER);
    }

    @Test
    void testDateTimeUtils() {
        ZonedDateTime zdt = DateTimeUtils.toZonedDateTime("1971-03-24 06:34:55");
        assertThat(zdt.getYear()).isEqualTo(1971);
        assertThat(zdt.getMonth().getValue()).isEqualTo(3);
        assertThat(zdt.getDayOfMonth()).isEqualTo(24);
    }

}
