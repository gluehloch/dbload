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

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import de.dbload.meta.ColumnKey;
import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.misc.DateTimeUtils;
import de.dbload.misc.NumberUtils;

/**
 * A test for {@link DefaultJdbcTypeConverter}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
class JdbcTypeConverterTest {

    private DefaultJdbcTypeConverter converter = new DefaultJdbcTypeConverter(
            DateTimeUtils.ZONE_EUROPE_BERLIN,
            DateTimeUtils.DEFAULT_LOCAL_DATETIME_FORMATTER,
            NumberUtils.createDecimalFormatter(Locale.GERMANY));

    @Test
    void testJdbcTypeConverterToNumberInteger() {
        ColumnMetaData columnMetaData = new ColumnMetaData(ColumnKey.of("col1"), Type.INTEGER);

        Object value = converter.toSqlValue(columnMetaData, "4711");
        assertThat(value).isInstanceOf(Number.class);
        Number number = (Number) value;
        assertThat(number.intValue()).isEqualTo(4711);

        value = converter.toSqlValue(columnMetaData, "4711,1");
        assertThat(value).isInstanceOf(Number.class);
        number = (Number) value;
        assertThat(number.intValue()).isEqualTo(4711);
    }

    @Test
    void testJdbcTypeConverterToNumberDecimal() {
        ColumnMetaData columnMetaData = new ColumnMetaData(ColumnKey.of("col1"), Type.INTEGER);

        Object value = converter.toSqlValue(columnMetaData, "4711,11");
        assertThat(value).isInstanceOf(Number.class);
        Number number = (Number) value;
        assertThat(number.doubleValue()).isEqualTo(4711.11);

        value = converter.toSqlValue(columnMetaData, "4711");
        assertThat(value).isInstanceOfAny(Number.class);
        number = (Number) value;
        assertThat(number.doubleValue()).isEqualTo(4711d);
    }

    @Test
    void testJdbcTypeConverterToString() {
        ColumnMetaData columnMetaData = new ColumnMetaData(ColumnKey.of("col1"), Type.VARCHAR);
        Object value = converter.toSqlValue(columnMetaData, "4711");

        assertThat(value).isInstanceOf(String.class);
        String str = (String) value;
        assertThat(str).isEqualTo("4711");
    }

    @Test
    void testJdbcTypeConverterToDate() {
        ColumnMetaData columnMetaData = new ColumnMetaData(ColumnKey.of("col1"), Type.DATE);
        Object value = converter.toSqlValue(columnMetaData, "2011-03-24 06:34:11");

        assertThat(value).isInstanceOfAny(ZonedDateTime.class);
        var valueAsZonedDateTime = (ZonedDateTime) value;
        assertThat(valueAsZonedDateTime.getZone()).isEqualTo(converter.getZoneId());

        // ZonedDateTime zdt = DateTimeUtils.toZonedDateTime("2011-03-24T06:34:11+01:00 Europe/Berlin");
        ZonedDateTime zdt = (ZonedDateTime) DateTimeUtils.toDateTimeWithOptionalOffsetAndTimeZoneId(
                "2011-03-24T06:34:11+01:00[Europe/Berlin]", converter.getZoneId());
        assertThat(zdt.getZone()).isEqualTo(converter.getZoneId());

        // ZonedDateTime zdt = DateTimeUtils.toZonedDateTime("2011-03-24 06:34:11");
        assertThat(value).isEqualTo(zdt);
    }

}
