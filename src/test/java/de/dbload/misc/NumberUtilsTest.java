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

import java.util.Locale;

import org.junit.Test;

/**
 * Test for {@link NumberUtils}.
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class NumberUtilsTest {

    @Test
    public void testNumberUtilsToInteger() {
    	System.out.println(Locale.getDefault().toLanguageTag());
    	
        Number number = null;

        number = NumberUtils.toNumber("4711", Locale.GERMANY);
        assertThat(number.intValue(), equalTo(4711));

        number = NumberUtils.toNumber("4711,00", Locale.GERMANY);
        assertThat(number.intValue(), equalTo(4711));

        number = NumberUtils.toNumber("4711.00", Locale.GERMANY);
        assertThat(number.intValue(), equalTo(471100));

        number = NumberUtils.toNumber("4.711", Locale.GERMANY);
        assertThat(number.intValue(), equalTo(4711));

        number = NumberUtils.toNumber("4711,1", Locale.GERMANY);
        assertThat(number.intValue(), equalTo(4711));
    }

    @Test
    public void testNumberUtilsToDouble() {
        Number number = null;

        number = NumberUtils.toNumber("4711", Locale.GERMANY);
        assertThat(number.doubleValue(), equalTo(4711.));

        number = NumberUtils.toNumber("4711,00", Locale.GERMANY);
        assertThat(number.doubleValue(), equalTo(4711.));

        number = NumberUtils.toNumber("4711.00", Locale.GERMANY);
        assertThat(number.doubleValue(), equalTo(471100.));

        number = NumberUtils.toNumber("4.711", Locale.GERMANY);
        assertThat(number.doubleValue(), equalTo(4711.));

        number = NumberUtils.toNumber("4711,1", Locale.GERMANY);
        assertThat(number.doubleValue(), equalTo(4711.1));
    }

    @Test
    public void testNumberUtilsToIntegerWithEnglishLocale() {
        Number number = null;

        number = NumberUtils.toNumber("4711", Locale.ENGLISH);
        assertThat(number.intValue(), equalTo(4711));

        number = NumberUtils.toNumber("4711,00", Locale.ENGLISH);
        assertThat(number.intValue(), equalTo(471100));

        number = NumberUtils.toNumber("4711.00", Locale.ENGLISH);
        assertThat(number.intValue(), equalTo(4711));

        number = NumberUtils.toNumber("4.711", Locale.ENGLISH);
        assertThat(number.intValue(), equalTo(4));

        number = NumberUtils.toNumber("4711,1", Locale.ENGLISH);
        assertThat(number.intValue(), equalTo(47111));
    }

    @Test
    public void testNumberUtilsToDoubleWithEnglishLocale() {
        Number number = null;

        number = NumberUtils.toNumber("4711", Locale.ENGLISH);
        assertThat(number.doubleValue(), equalTo(4711.));

        number = NumberUtils.toNumber("4711,00", Locale.ENGLISH);
        assertThat(number.doubleValue(), equalTo(471100.));

        number = NumberUtils.toNumber("4711.00", Locale.ENGLISH);
        assertThat(number.doubleValue(), equalTo(4711.));

        number = NumberUtils.toNumber("4.711", Locale.ENGLISH);
        assertThat(number.doubleValue(), equalTo(4.711));

        number = NumberUtils.toNumber("4711,1", Locale.ENGLISH);
        assertThat(number.doubleValue(), equalTo(47111.));
    }

}
