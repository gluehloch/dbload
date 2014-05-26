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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class NumberUtils {

    /** The default format. */
    public static final String DEFAULT_DECIMAL_FORMAT = "###,###.##";

    /** The default format. */
    public static final String DEFAULT_ZERO_FORMAT = "##,##0.00";

    /** The default locale. */
    private static final Locale DEFAULT_LOCALE = Locale.getDefault();

    public static Number toNumber(final String value) {
        return toNumber(value, DEFAULT_LOCALE);
    }

    public static Number toNumber(final String value, Locale locale) {
        return toNumber(value, createDecimalFormatter(locale));
    }

    public static Number toNumber(final String value, DecimalFormat decimalFormat) {
        Number number;
        try {
            number = parseNumber(value, decimalFormat);
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        return number;
    }

    private static Number parseNumber(String value, DecimalFormat decimalFormat) throws ParseException {
        return (decimalFormat.parse(value));
    }

    public static DecimalFormat createDecimalFormatter(Locale locale) {
        DecimalFormat decimalFormat =
                (DecimalFormat) NumberFormat.getNumberInstance(locale);
        decimalFormat.applyPattern(DEFAULT_DECIMAL_FORMAT);
        return decimalFormat;
    }

}
