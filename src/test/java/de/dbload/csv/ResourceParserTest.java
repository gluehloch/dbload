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

package de.dbload.csv;

import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.dbload.DataRow;

/**
 * Test fuer {@link ResourceParser}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class ResourceParserTest {

    /**
     * Test {@link ResourceParser#readTableDefinition(String)}
     */
    @Test
    public void testResourceParserTableName() {
        ResourceParser resourceParser = new ResourceParser();
        String readTableDefinition = resourceParser
                .readTableDefinition("### TAB table_name");
        assertEquals("table_name", readTableDefinition);
    }

    /**
     * Test {@link ResourceParser#readColumnNames(String)}
     */
    @Test
    public void testDataLoderParseColumns() {
        ResourceParser resourceParser = new ResourceParser();
        List<String> columns = resourceParser
                .readColumnNames("### col1 | col2(date) | col3");

        assertEquals("col1", columns.get(0));
        assertEquals("col2", columns.get(1));
        //assertEquals(ColumnMetaData.Type.DATE, columns.get(1).getColumnType());
        assertEquals("col3", columns.get(2));
    }

    /**
     * Test {@link ResourceParser#readRow(List, String)}
     */
    @Test
    public void testResourceParserDataRow() {
        List<String> sixColumnNames = Arrays.asList("col1", "col2", "col3",
                "col4", "col5", "col6");

        ResourceParser resourceParser = new ResourceParser();
        DataRow data = null;

        data = resourceParser.readRow(sixColumnNames,
                "dat1 | dat2|dat3  | dat4 | | ");

        assertEquals("dat1", data.get("col1"));
        assertEquals("dat2", data.get("col2"));
        assertEquals("dat3", data.get("col3"));
        assertEquals("dat4", data.get("col4"));
        assertThat(data.get("col5"), nullValue());
        assertThat(data.get("col6"), nullValue());

        data = resourceParser.readRow(sixColumnNames,
                "dat1 | dat2|dat3  | dat4 || ");

        assertEquals("dat1", data.get("col1"));
        assertEquals("dat2", data.get("col2"));
        assertEquals("dat3", data.get("col3"));
        assertEquals("dat4", data.get("col4"));
        assertThat(data.get("col5"), nullValue());
        assertThat(data.get("col6"), nullValue());

        List<String> sevenColumnNames = Arrays.asList("col1", "col2", "col3",
                "col4", "col5", "col6", "col7");

        data = resourceParser.readRow(sevenColumnNames,
                "dat1 | dat2|dat3  | dat4 ||  |");

        assertEquals("dat1", data.get("col1"));
        assertEquals("dat2", data.get("col2"));
        assertEquals("dat3", data.get("col3"));
        assertEquals("dat4", data.get("col4"));
        assertThat(data.get("col5"), nullValue());
        assertThat(data.get("col6"), nullValue());
        assertThat(data.get("col7"), nullValue());
    }

}
