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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
import java.util.Locale;

import org.joda.time.DateTime;
import org.junit.Test;

import de.dbload.JdbcTypeConverter;
import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.ColumnMetaData.Type;
import de.dbload.misc.DateTimeUtils;

/**
 * A test for {@link DefaultJdbcTypeConverter}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class JdbcTypeConverterTest {

    @Test
    public void testJdbcTypeConverterToNumberInteger() {
        JdbcTypeConverter converter = new DefaultJdbcTypeConverter(Locale.GERMANY);
        ColumnMetaData columnMetaData = new ColumnMetaData("col1", Type.INTEGER);

        Object value = converter.convert(columnMetaData, "4711");
        assertThat(value, instanceOf(Number.class));
        Number number = (Number) value;
        assertThat(number.intValue(), equalTo(4711));

        value = converter.convert(columnMetaData, "4711,1");
        assertThat(value, instanceOf(Number.class));
        number = (Number) value;
        assertThat(number.intValue(), equalTo(4711));
    }

    @Test
    public void testJdbcTypeConverterToNumberDecimal() {
        JdbcTypeConverter converter = new DefaultJdbcTypeConverter(Locale.GERMANY);
        ColumnMetaData columnMetaData = new ColumnMetaData("col1", Type.INTEGER);

        Object value = converter.convert(columnMetaData, "4711,11");
        assertThat(value, instanceOf(Number.class));
        Number number = (Number) value;
        assertThat(number.doubleValue(), equalTo(4711.11));

        value = converter.convert(columnMetaData, "4711");
        assertThat(value, instanceOf(Number.class));
        number = (Number) value;
        assertThat(number.doubleValue(), equalTo(4711d));
    }

    @Test
    public void testJdbcTypeConverterToString() {
        JdbcTypeConverter converter = new DefaultJdbcTypeConverter(Locale.GERMANY);
        ColumnMetaData columnMetaData = new ColumnMetaData("col1", Type.VARCHAR);
        Object value = converter.convert(columnMetaData, "4711");

        assertThat(value, instanceOf(String.class));
        String str = (String) value;
        assertThat(str, equalTo("4711"));
    }

    @Test
    public void testJdbcTypeConverterToDate() {
        JdbcTypeConverter converter = new DefaultJdbcTypeConverter(Locale.GERMANY);
        ColumnMetaData columnMetaData = new ColumnMetaData("col1", Type.DATE);
        Object value = converter.convert(columnMetaData, "2011-03-24 06:34:11");

        assertThat(value, instanceOf(Timestamp.class));
        DateTime jodaDateTime = DateTimeUtils.toJodaDateTime("2011-03-24 06:34:11");
        assertThat(((Timestamp) value).getTime() , equalTo(jodaDateTime.toDate().getTime()));
    }

}
