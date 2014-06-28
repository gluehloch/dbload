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

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.ComparisonFailure;
import org.junit.Test;

import de.dbload.DataRow;
import de.dbload.meta.TableMetaData;
import de.dbload.utils.TestMetaDataFactory;

/**
 * An example for a good assertion class.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class ExampleForAGoodAssertionTest {

    @Test
    public void messageOfAnAssertionError() {
        ComparisonFailure failure = new ComparisonFailure("Some nice text.",
                "expected", "actual");

        assertThat(
                failure.getMessage(),
                equalTo("Some nice text. expected:<[expected]> but was:<[actual]>"));
    }

    private class MyAssertionError extends ComparisonFailure {
        public MyAssertionError(String message, String expected, String actual) {
            super(message, expected, actual);
        }
    }

    @Test
    public void matchDataRow() {
        DataRow rowA = new DataRow();
        rowA.put("col1", "value1");
        rowA.put("col2", "value2");
        rowA.put("col3", "value3");

        DataRow rowB = new DataRow();
        rowB.put("col1", "value1");
        rowB.put("col2", "value2");
        rowB.put("col3", "value3");

        assertThat("identical dataRows", rowA, new DataRowMatcher(rowB));

        DataRow rowC = new DataRow();
        rowC.put("col1", "value1");
        rowC.put("col2", "value2");
        rowC.put("col3", "value4");

        assertThat("identical dataRows", rowA, new DataRowMatcher(rowC));
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
            description.appendText("the datarow should by equal to " + dataRow);
        }

    }

}
