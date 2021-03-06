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

import static org.assertj.core.api.Assertions.assertThat;

import de.dbload.meta.ColumnMetaData;
import de.dbload.meta.ColumnMetaData.Type;
import org.junit.jupiter.api.Test;

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
        assertThat(columnMetaData.getColumnName()).isEqualTo("hello");
        assertThat(columnMetaData.getColumnType()).isEqualTo(Type.VARCHAR);

        columnMetaData = ColumnTypeParser.parseColumnMetaData("hello(date)");
        assertThat(columnMetaData.getColumnName()).isEqualTo("hello");
        assertThat(columnMetaData.getColumnType()).isEqualTo(Type.DATE_TIME);
    }

    @Test
    public void testColumnTypeParser() {
        assertThat(ColumnTypeParser.findType("thisismycolumn(date)"))
                .isEqualTo(Type.DATE_TIME);
        assertThat(ColumnTypeParser.findType("thisismycolumn (date)"))
                .isEqualTo(Type.DATE_TIME);
        assertThat(ColumnTypeParser.findType("thisismycolumn"))
                .isEqualTo(Type.VARCHAR);
    }

}
