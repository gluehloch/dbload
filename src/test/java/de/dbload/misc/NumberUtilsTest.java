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

import java.util.Locale;

import org.junit.jupiter.api.Test;

/**
 * Test for {@link NumberUtils}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
class NumberUtilsTest {

    @Test
    void testNumberUtilsToInteger() {
        System.out.println(Locale.getDefault().toLanguageTag());

        Number number = null;

        number = NumberUtils.toNumber("4711", Locale.GERMANY);
        assertThat(number.intValue()).isEqualTo(4711);

        number = NumberUtils.toNumber("4711,00", Locale.GERMANY);
        assertThat(number.intValue()).isEqualTo(4711);

        number = NumberUtils.toNumber("4711.00", Locale.GERMANY);
        assertThat(number.intValue()).isEqualTo(471100);

        number = NumberUtils.toNumber("4.711", Locale.GERMANY);
        assertThat(number.intValue()).isEqualTo(4711);

        number = NumberUtils.toNumber("4711,1", Locale.GERMANY);
        assertThat(number.intValue()).isEqualTo(4711);
    }

    @Test
    void testNumberUtilsToDouble() {
        Number number = null;

        number = NumberUtils.toNumber("4711", Locale.GERMANY);
        assertThat(number.doubleValue()).isEqualTo(4711.);

        number = NumberUtils.toNumber("4711,00", Locale.GERMANY);
        assertThat(number.doubleValue()).isEqualTo(4711.);

        number = NumberUtils.toNumber("4711.00", Locale.GERMANY);
        assertThat(number.doubleValue()).isEqualTo(471100.);

        number = NumberUtils.toNumber("4.711", Locale.GERMANY);
        assertThat(number.doubleValue()).isEqualTo(4711.);

        number = NumberUtils.toNumber("4711,1", Locale.GERMANY);
        assertThat(number.doubleValue()).isEqualTo(4711.1);
    }

    @Test
    void testNumberUtilsToIntegerWithEnglishLocale() {
        Number number = null;

        number = NumberUtils.toNumber("4711", Locale.ENGLISH);
        assertThat(number.intValue()).isEqualTo(4711);

        number = NumberUtils.toNumber("4711,00", Locale.ENGLISH);
        assertThat(number.intValue()).isEqualTo(471100);

        number = NumberUtils.toNumber("4711.00", Locale.ENGLISH);
        assertThat(number.intValue()).isEqualTo(4711);

        number = NumberUtils.toNumber("4.711", Locale.ENGLISH);
        assertThat(number.intValue()).isEqualTo(4);

        number = NumberUtils.toNumber("4711,1", Locale.ENGLISH);
        assertThat(number.intValue()).isEqualTo(47111);
    }

    @Test
    void testNumberUtilsToDoubleWithEnglishLocale() {
        Number number = null;

        number = NumberUtils.toNumber("4711", Locale.ENGLISH);
        assertThat(number.doubleValue()).isEqualTo(4711.);

        number = NumberUtils.toNumber("4711,00", Locale.ENGLISH);
        assertThat(number.doubleValue()).isEqualTo(471100.);

        number = NumberUtils.toNumber("4711.00", Locale.ENGLISH);
        assertThat(number.doubleValue()).isEqualTo(4711.);

        number = NumberUtils.toNumber("4.711", Locale.ENGLISH);
        assertThat(number.doubleValue()).isEqualTo(4.711);

        number = NumberUtils.toNumber("4711,1", Locale.ENGLISH);
        assertThat(number.doubleValue()).isEqualTo(47111.);
    }

}
