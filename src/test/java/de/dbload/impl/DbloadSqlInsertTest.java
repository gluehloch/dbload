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

package de.dbload.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.dbload.DbloadContext;
import de.dbload.meta.DataRow;
import de.dbload.meta.TableMetaData;
import de.dbload.utils.TestMetaDataFactory;
import de.dbload.utils.TransactionalTest;

/**
 * Test for class {@link DbloadSqlInsert}.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DbloadSqlInsertTest extends TransactionalTest {

    private DbloadContext dbloadContext;
    private TableMetaData tableMetaData;

    @BeforeEach
    public void before() {
        dbloadContext = new DefaultDbloadContext(conn);
        tableMetaData = TestMetaDataFactory.createPersonMetaData();
    }

    @Test
    public void testDbloadSqlInsert() throws SQLException {
        try (DbloadSqlInsert dbloadSqlInsert = new DbloadSqlInsert(
                dbloadContext)) {
            dbloadSqlInsert.newTableMetaData(tableMetaData);
            DataRow dataRow = new DataRow();
            dataRow.put("id", "1");
            dataRow.put("name", "winkler");
            dataRow.put("vorname", "andre");
            dataRow.put("age", "55");
            dbloadSqlInsert.execute(dataRow);
        }

        try (Statement stmt = dbloadContext.getConnection().createStatement()) {
            try (ResultSet resultSet = stmt
                    .executeQuery(
                            "select id, name, vorname, age from person")) {
                assertThat(resultSet.next()).isTrue();
                assertThat(resultSet.getInt("id")).isEqualTo(1);
                assertThat(resultSet.getString("name")).isEqualTo("winkler");
                assertThat(resultSet.getString("vorname")).isEqualTo("andre");
                assertThat(resultSet.getInt("age")).isEqualTo(55);
            }
        }
    }

}
