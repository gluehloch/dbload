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

package de.dbload;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import de.dbload.impl.DefaultDbloadContext;
import de.dbload.misc.DateTimeUtils;
import de.dbload.utils.TransactionalTest;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

/**
 * Test Dbload. The whole!
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DbloadTest extends TransactionalTest {

    @Test
    public void testDbload() throws Exception {
        DbloadContext context = new DefaultDbloadContext(conn);
        Dbload.read(context, DbloadTest.class);

        assertThat(context).isInstanceOf(DefaultDbloadContext.class);

        try (Statement stmt = conn.createStatement()) {
            // ResultSet resultSet = stmt
            // .executeQuery("SELECT * FROM xxx WHERE birthday = '1971-03-24
            // 05:55:55'");
            try (ResultSet resultSet = stmt
                    .executeQuery(
                            "SELECT * FROM person WHERE birthday = '1971-03-24 06:38:00'")) {
                assertThat(resultSet.next()).isTrue();

                DateTime datetime = DateTimeUtils
                        .toJodaDateTime("1971-03-24 06:38:00");

                Timestamp expectedTimestamp = new Timestamp(datetime.toDate()
                        .getTime());
                assertThat(resultSet.getTimestamp("birthday"))
                        .isEqualTo(expectedTimestamp);
            }
        }
    }

}
