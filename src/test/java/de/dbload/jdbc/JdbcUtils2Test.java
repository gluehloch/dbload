package de.dbload.jdbc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.dbload.meta.TableMetaData;

public class JdbcUtils2Test {

    private Connection conn;
    private Statement stmt;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;

    @BeforeEach
    public void setUp() throws SQLException {
        conn = mock(Connection.class);
        stmt = mock(Statement.class);
        resultSet = mock(ResultSet.class);
        metaData = mock(ResultSetMetaData.class);

        when(conn.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.getMetaData()).thenReturn(metaData);
    }

    @Test
    public void testClose() {
        JdbcUtils.close(stmt);
        assertDoesNotThrow(() -> verify(stmt).close());
    }

    @Test
    public void testDelete() throws SQLException {
        JdbcUtils.delete(conn, "testTable");
        verify(stmt).executeUpdate("DELETE FROM testTable");
    }

    @Test
    public void testFindMetaData() throws SQLException {
        // Setup mock data
        TableMetaData csvMetaData = new TableMetaData("testTable", null);
        when(metaData.getTableName(1)).thenReturn("testTable");

        // Call method and verify
        JdbcUtils.findMetaData(conn, csvMetaData);
        verify(conn).createStatement();
        verify(stmt).executeQuery("SELECT * FROM testTable WHERE 1 = 0");
    }

    // Add more tests for the remaining methods and scenarios.

}
