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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.InputStream;

import org.junit.Test;

import de.dbload.assertion.Assertion;
import de.dbload.impl.DefaultDbloadContext;
import de.dbload.utils.TransactionalTest;

/**
 * Test Dbload. The <code>.dat</code> file is missing a column.
 *
 * @author Andre Winkler. http://www.andre-winkler.de
 */
public class DbloadWithMissingColumnTest extends TransactionalTest {

    @Test
    public void testDbload() throws Exception {
        DbloadContext context = new DefaultDbloadContext(conn);
        Dbload.read(context, DbloadWithMissingColumnTest.class);

        InputStream resourceAsStream = DbloadWithMissingColumnTest.class
                .getResourceAsStream("DbloadWithMissingColumnTest.dat");
        assertThat(resourceAsStream, notNullValue());
        
        Assertion.assertExists(context, DbloadWithMissingColumnTest.class);
    }

}
