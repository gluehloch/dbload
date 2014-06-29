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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.ComparisonFailure;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import de.dbload.DataRow;

// import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An example for a good assertion class.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class ExampleForAGoodAssertionTest {

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void messageOfAnAssertionError() {
        ComparisonFailure failure = new ComparisonFailure("Some nice text.",
                "expected", "actual");

        collector
                .checkThat(
                        failure.getMessage(),
                        equalTo("Some nice text. expected:<[expected]> but was:<[actual]>"));
    }

    /**
     * Test method to find the 'perfect' matcher for {@link DataRow} or a
     * {@link Map}.
     */
    @Test
    @Ignore
    public void matchDataRow() {
        DataRow rowA = new DataRow();
        rowA.put("col1", "value1");
        rowA.put("col2", "value2");
        rowA.put("col3", "value3");

        DataRow rowB = new DataRow();
        rowB.put("col1", "value1");
        rowB.put("col2", "value2");
        rowB.put("col3", "value3");

        DataRow rowC = new DataRow();
        rowC.put("col1", "value1");
        rowC.put("col2", "value2");
        rowC.put("col3", "value4");

        collector.checkThat("identical dataRows", rowA,
                new DataRowMatcher(rowB));
        collector.checkThat("identical dataRows", rowA,
                new DataRowMatcher(rowC)); // error

        collector.checkThat("identical dataRows", rowA,
                new DataRowTypeSafeMatcher(rowB));
        collector.checkThat("identical dataRows", rowA,
                new DataRowTypeSafeMatcher(rowC)); // error

        collector.checkThat("identical dataRows", rowA,
                new DataRowTypeSafeDignosticMatcher(rowB));
        collector.checkThat("identical dataRows", rowA,
                new DataRowTypeSafeDignosticMatcher(rowC)); // error
        collector.checkThat(new DataRow(), new DataRowTypeSafeDignosticMatcher(
                rowC)); // error

        collector.checkThat(rowA.asMap(),
                MapContentMatchers.hasAllEntries(rowC.asMap()));

        collector.checkThat(rowA, DataRowMatchers.hasAllEntries(rowC));

        assertThat("identical dataRows", rowA,
                DataRowMatchers.hasAllEntries(rowC)); // error
    }

    private class DataRowMatcher extends BaseMatcher<DataRow> {

        private final DataRow dataRow;

        DataRowMatcher(DataRow _dataRow) {
            dataRow = _dataRow;
        }

        @Override
        public boolean matches(Object value) {
            DataRow dataRowToMatch = (DataRow) value;
            return dataRow.equals(dataRowToMatch);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("the datarow should by equal to ")
                    .appendValue(dataRow);
        }

    }

    private class DataRowTypeSafeMatcher extends TypeSafeMatcher<DataRow> {

        private final DataRow dataRow;

        DataRowTypeSafeMatcher(DataRow _dataRow) {
            dataRow = _dataRow;
        }

        @Override
        public boolean matchesSafely(DataRow dataRowToMatch) {
            return dataRow.equals(dataRowToMatch);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("the datarow should by equal to ")
                    .appendValue(dataRow);
        }

    }

    private class DataRowTypeSafeDignosticMatcher extends
            TypeSafeDiagnosingMatcher<DataRow> {

        private final DataRow dataRow;

        DataRowTypeSafeDignosticMatcher(DataRow _dataRow) {
            dataRow = _dataRow;
        }

        @Override
        public void describeTo(Description description) {
            description.appendValue(dataRow);
        }

        @Override
        protected boolean matchesSafely(DataRow dataRowToMatch,
                Description mismatchDescription) {

            mismatchDescription.appendValue(dataRow)
                    .appendText(" is not equal to ")
                    .appendValue(dataRowToMatch);

            return dataRow.equals(dataRowToMatch);
        }

    }

}
