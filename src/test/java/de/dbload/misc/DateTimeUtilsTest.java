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

import java.sql.Timestamp;
import java.util.Date;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

/**
 * Test for class {@link DateTimeUtils}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
class DateTimeUtilsTest {

    @Test
    void testDateTimeUtils() {
        DateTime jodaDateTime = DateTimeUtils
                .toJodaDateTime("1971-03-24 06:34:55");
        assertThat(jodaDateTime.getYear()).isEqualTo(1971);
    }

    @Test
    void testDateTimeVersusJavaSqlTimestamp() {
        DateTime jodaDateTime = DateTimeUtils
                .toJodaDateTime("1971-03-24 06:34:55");
        Date birthday = jodaDateTime.toDate();
        Timestamp timestamp = new Timestamp(birthday.getTime());

        assertThat(birthday.getTime()).isEqualTo(timestamp.getTime());
        assertThat(birthday.getTime()).isEqualTo(jodaDateTime.getMillis());

        System.out.println(jodaDateTime);
        System.out.println(birthday);
        System.out.println(timestamp);
        System.out.println(String.format("%tY-%tm-%td", birthday, birthday, birthday));
        System.out.println(jodaDateTime.toString("yyyy-MM-dd HH:mm:ss"));
    }

}
