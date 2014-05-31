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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * Test for class {@link DateTimeUtils}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DateTimeUtilsTest {

    @Test
    public void testDateTimeUtils() {
        DateTime jodaDateTime = DateTimeUtils.toJodaDateTime("1971-03-24 06:34:55");
        assertThat(1971, equalTo(jodaDateTime.getYear()));
    }

}
