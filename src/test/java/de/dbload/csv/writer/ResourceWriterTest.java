/*
 * Copyright 2018 Andre Winkler
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

package de.dbload.csv.writer;

import java.io.File;
import java.sql.SQLException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.dbload.BigDbloadTest;
import de.dbload.Dbload;
import de.dbload.DbloadContext;
import de.dbload.impl.DefaultDbloadContext;
import de.dbload.utils.TransactionalTest;

/**
 * Write a database table to a file.
 * 
 * @author Andre Winkler
 */
public class ResourceWriterTest extends TransactionalTest {

    @Disabled
    @Test
    public void testResourceWriter() throws SQLException {
        DbloadContext context = new DefaultDbloadContext(conn);
        Dbload.read(context, BigDbloadTest.class);

        File file = new File("E:/projects/tmp/dbload.dat");
        ResourceWriter resourceWriter = new ResourceWriter(file);
        resourceWriter.start(conn, "SELECT * FROM person", false);
    }

}
