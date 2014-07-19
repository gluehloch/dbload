/*
 * Copyright 2014 Andre Winkler
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package de.dbload.assertion;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import de.dbload.meta.DataRow;

/**
 * A hamcrest matcher for {@link DataRow}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DataRowMatchers extends TypeSafeMatcher<DataRow> {

    private DataRow dataRow;

    DataRowMatchers(DataRow _dataRow) {
        dataRow = _dataRow;
    }

    /**
     * Creates a {@link DataRow} matcher.
     *
     * @param dataRow
     *            the dataRow to match against
     * @return a hamcrest matcher
     */
    @Factory
    public static Matcher<DataRow> hasAllEntries(DataRow dataRow) {
        return new DataRowMatchers(dataRow);
    }

    @Override
    public boolean matchesSafely(DataRow dataRowToMatch) {
        return MapContentMatchers.hasAllEntries(dataRowToMatch.asMap())
                .matches(dataRow);
    }

    @Override
    public void describeTo(Description description) {
        MapContentMatchers.hasAllEntries(dataRow.asMap()).describeTo(
                description);
    }

}
