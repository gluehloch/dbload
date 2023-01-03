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

package de.dbload.csv.reader;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import de.dbload.meta.ColumnKey;
import de.dbload.meta.DataRow;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test fuer {@link ResourceParser}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
@Tag("csv")
class ResourceParserTest {

    /**
     * Test {@link ResourceParser#readTableDefinition(String)}
     */
    @Test
    void testResourceParserTableName() {
        ResourceParser resourceParser = new ResourceParser();
        String readTableDefinition = resourceParser.readTableDefinition("### TAB table_name");
        assertThat(readTableDefinition).isEqualTo("table_name");
    }

    /**
     * Test {@link ResourceParser#readColumnNames(String)}
     */
    @Test
    void testDataLoderParseColumns() {
        ResourceParser resourceParser = new ResourceParser();
        List<ColumnKey> columns = resourceParser.readColumnNames("### col1 | col2(date) | col3");

        assertThat(columns.get(0).getColumnName()).isEqualTo("col1");
        assertThat(columns.get(1).getColumnName()).isEqualTo("col2(date)");
        // assertEquals(ColumnMetaData.Type.DATE,
        // columns.get(1).getColumnType());
        assertThat(columns.get(2).getColumnName()).isEqualTo("col3");
    }

    /**
     * Test {@link ResourceParser#readRow(List, int, String)}
     */
    @Test
    void testResourceParserDataRow() {
        List<ColumnKey> sixColumnNames = Arrays.asList("col1", "col2", "col3", "col4", "col5", "col6").stream()
                .map(ColumnKey::of).toList();

        ResourceParser resourceParser = new ResourceParser();
        DataRow data = null;

        data = resourceParser.readRow(sixColumnNames, 1, "dat1 | dat2|dat3  | dat4 | | ");

        assertThat(data.get(ColumnKey.of("col1"))).isEqualTo("dat1");
        assertThat(data.get(ColumnKey.of("col2"))).isEqualTo("dat2");
        assertThat(data.get(ColumnKey.of("col3"))).isEqualTo("dat3");
        assertThat(data.get(ColumnKey.of("col4"))).isEqualTo("dat4");
        assertThat(data.get(ColumnKey.of("col5"))).isNull();
        assertThat(data.get(ColumnKey.of("col6"))).isNull();

        data = resourceParser.readRow(sixColumnNames, 1, "dat1 | dat2|dat3  | dat4 || ");

        assertThat(data.get(ColumnKey.of("col1"))).isEqualTo("dat1");
        assertThat(data.get(ColumnKey.of("col2"))).isEqualTo("dat2");
        assertThat(data.get(ColumnKey.of("col3"))).isEqualTo("dat3");
        assertThat(data.get(ColumnKey.of("col4"))).isEqualTo("dat4");
        assertThat(data.get(ColumnKey.of("col5"))).isNull();
        assertThat(data.get(ColumnKey.of("col6"))).isNull();

        List<ColumnKey> sevenColumnNames = Arrays.asList("col1", "col2", "col3", "col4", "col5", "col6", "col7")
                .stream().map(ColumnKey::of).toList();

        data = resourceParser.readRow(sevenColumnNames, 1, "dat1 | dat2|dat3  | dat4 ||  |");
        assertThat(data.get(ColumnKey.of("col1"))).isEqualTo("dat1");
        assertThat(data.get(ColumnKey.of("col2"))).isEqualTo("dat2");
        assertThat(data.get(ColumnKey.of("col3"))).isEqualTo("dat3");
        assertThat(data.get(ColumnKey.of("col4"))).isEqualTo("dat4");
        assertThat(data.get(ColumnKey.of("col5"))).isNull();
        assertThat(data.get(ColumnKey.of("col6"))).isNull();
        assertThat(data.get(ColumnKey.of("col7"))).isNull();
    }

}
