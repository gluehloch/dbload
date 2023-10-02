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
import java.time.ZoneId;
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
    void javaDateTime() {
        ZoneId utcZone = ZoneId.of("UTC");
        assertThat(utcZone).isNotNull();

        ZoneId zZone = ZoneId.of("Z");
        assertThat(zZone).isNotNull();
        assertThat(utcZone.normalized()).isEqualTo(zZone);

        ZoneId europeBerlin = ZoneId.of("Europe/Berlin");
        assertThat(europeBerlin).isNotNull();
        assertThat(europeBerlin.normalized()).isEqualTo(europeBerlin);
                
        var now = Instant.now();
        
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(utcZone).format(now));
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z").withZone(utcZone).format(now));
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z").withZone(europeBerlin).format(now));
        System.out.println(DateTimeFormatter.ISO_INSTANT.withZone(europeBerlin).format(now));
        System.out.println(DateTimeFormatter.ISO_DATE_TIME.withZone(europeBerlin).format(now));
    }

    @Test
    void parseDateTime() {
        LocalDateTime ldt = LocalDateTime.parse("1971-03-24 06:00:01", DateTimeUtils.DEFAULT_LOCAL_DATETIME_FORMATTER);
        assertThat(ldt.getYear()).isEqualTo(1971);

        // In dem übergebenen String fehlt die Zeitzone.
        Assertions.assertThrows(DateTimeParseException.class, () -> {
            ZonedDateTime.parse("1971-03-24 06:00:01", DateTimeUtils.DEFAULT_UTC_FORMATTER);
        });

        LocalDateTime ldt2 = LocalDateTime.parse("1971-03-24 06:00:01", DateTimeUtils.DEFAULT_LOCAL_DATETIME_FORMATTER);
        assertThat(ldt2.getYear()).isEqualTo(1971);
        assertThat(ldt2.getMonthValue()).isEqualTo(3);
        assertThat(ldt2.getDayOfMonth()).isEqualTo(24);
        assertThat(ldt2.getHour()).isEqualTo(6);
        assertThat(ldt2.getMinute()).isEqualTo(0);
        assertThat(ldt2.getSecond()).isEqualTo(1);
    }

    @Test
    void testDateTimeUtils() {
        LocalDateTime ldt = DateTimeUtils.toLocalDateTime("1971-03-24 06:34:55");
        assertThat(ldt.getYear()).isEqualTo(1971);
        assertThat(ldt.getMonth().getValue()).isEqualTo(3);
        assertThat(ldt.getDayOfMonth()).isEqualTo(24);
    }

}
