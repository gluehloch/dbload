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

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.dbload.Dbload;
import de.dbload.DbloadContext;
import de.dbload.impl.DefaultDbloadContext;
import de.dbload.utils.TransactionalTest;

/**
 * Write a database table to a file.
 *
 * @author Andre Winkler
 */
class ResourceWriterTest extends TransactionalTest {

    private DbloadContext context;
    
    @BeforeEach
    void before() {
        context = DefaultDbloadContext.of(conn);
    }
    
    @Test
    void testResourceWriter() throws Exception {
        Dbload.read(context, ClasspathFileMarker.class);

        select(context);

        Path temp1 = writeToFile("dbload1");
        System.out.println(readFileToString(temp1));

        try (Statement stmt = context.connection().createStatement()) {
            stmt.execute("DELETE person");
        }

        Dbload.read(context, temp1.toFile());
        
        select(context);

        Path temp2 = writeToFile("dbload2");
        System.out.println(readFileToString(temp2));
    }
    
    private void select(DbloadContext context) throws Exception {
        try (Statement stmt = context.connection().createStatement()) {
            ResultSet resultSet = stmt.executeQuery("SELECT human FROM person ORDER BY id");
            while (resultSet.next()) {
                System.out.println(resultSet.getBoolean(1));
            }
        }
    }

    private Path writeToFile(String fileName) throws IOException, SQLException {
        Path temp = Files.createTempFile(fileName, ".dat");
        ResourceWriter resourceWriter = new ResourceWriter(temp.toFile(), context.dateTimeFormatter());
        resourceWriter.start(conn, "SELECT * FROM person ORDER BY id", false);
        return temp;
    }

    private String readFileToString(Path temp) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(temp, StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException ex) {
        }
        return contentBuilder.toString();
    }

    @Test
    void testResourceWriterStart() throws Exception {
        try (Statement stmt = context.connection().createStatement()) {
            stmt.execute("INSERT INTO person(id, firstname, lastname, age, human) values(1, 'Alice', 'Wunderland', 25, true)");
            stmt.execute("INSERT INTO person(id, firstname, lastname, age, human) values(2, 'Bob', 'Geldorf', 30, true)");
            stmt.execute("INSERT INTO person(id, firstname, lastname, age, human) values(3, 'Charlie', 'Brown', 35, false)");
        }

        // create a temporary file
        Path temp = Files.createTempFile("testResourceWriterStart", ".dat");

        // create a ResourceWriter instance
        ResourceWriter resourceWriter = new ResourceWriter(temp.toFile(), context.dateTimeFormatter());

        // execute the start method
        resourceWriter.start(conn, "SELECT * FROM person ORDER BY id", false);

        // read the content of the file
        String fileContent = new String(Files.readAllBytes(temp));

        // check if the file content is correct
        String expectedContent = """
                ### TAB PERSON
                ### ID | FIRSTNAME | LASTNAME | AGE | SEX | BIRTHDAY | HUMAN
                1 | Alice | Wunderland | 25 |  |  | TRUE
                2 | Bob | Geldorf | 30 |  |  | TRUE
                3 | Charlie | Brown | 35 |  |  | FALSE
                """;
        assertThat(fileContent).isEqualToIgnoringWhitespace(expectedContent);
    }

}
