package de.dbload;

import java.sql.Connection;
import java.sql.SQLException;

import de.dbload.jdbc.connector.JdbcConnector;

public class DbLoadMain {

    public static void main(String[] args) {
        DbloadCommandLineParser dclp = new DbloadCommandLineParser();
        DbloadCommandLineArguments arguments = dclp.parse(args, System.out);

        Connection connection = JdbcConnector.createConnection(
                arguments.getUsername(), arguments.getPassword(), arguments.getJdbcUrl());
        try {
            connection.isValid(0);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
