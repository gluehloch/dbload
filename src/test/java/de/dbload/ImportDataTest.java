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

package de.dbload;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;

public class ImportDataTest {

    @Test
    public void testImportData() throws IOException {
        ImportData importData = new ImportData();
        importData.start(dibaJdbcTemplate, "resourceundertest.dat",
                ImportDataTest.class);

        String sql = "SELECT * FROM beteiligte_partei WHERE bp_id = 7777777";
        SqlRowSet rowSet = dibaJdbcTemplate.queryForRowSet(sql);

        boolean findSomething = false;

        while (rowSet.next()) {
            findSomething = true;

            String bpid = rowSet.getString("BP_ID");
            String mitarbkenz = rowSet.getString("MITARB_KENZ");
            Date dterf = rowSet.getDate("DT_ERF");
            DateTime datetime = new DateTime(dterf);

            assertThat(bpid, equalTo("7777777"));
            assertThat(mitarbkenz, nullValue());
            assertThat(datetime, equalTo(new DateTime(2013, 07, 22, 11, 0, 0)));
        }

        assertThat(findSomething, is(true));
    }
}
