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

package de.dbload.csv.reader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.dbload.csv.reader.ColumnTypeParser;
import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.ColumnMetaData.Type;

/**
 * Test for class {@link ColumnTypeParser}.
 * 
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class ColumnTypeParserTest {

    @Test
    public void testColumnNameAndTypeParser() {
        ColumnMetaData columnMetaData = null;

        columnMetaData = ColumnTypeParser.parseColumnMetaData("hello");
        assertThat(columnMetaData.getColumnName(), equalTo("hello"));
        assertThat(columnMetaData.getColumnType(), equalTo(Type.DEFAULT));

        columnMetaData = ColumnTypeParser.parseColumnMetaData("hello(date)");
        assertThat(columnMetaData.getColumnName(), equalTo("hello"));
        assertThat(columnMetaData.getColumnType(), equalTo(Type.DATE_TIME));
    }

    @Test
    public void testColumnTypeParser() {
        assertThat(ColumnTypeParser.findType("thisismycolumn(date)"),
                is(Type.DATE_TIME));
        assertThat(ColumnTypeParser.findType("thisismycolumn (date)"),
                is(Type.DATE_TIME));
        assertThat(ColumnTypeParser.findType("thisismycolumn"),
                is(Type.DEFAULT));
    }

}
