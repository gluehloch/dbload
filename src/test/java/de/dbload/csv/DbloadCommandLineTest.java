package de.dbload.csv;

import org.junit.jupiter.api.Test;

import de.dbload.DbloadMain;

class DbloadCommandLineTest {

    @Test
    void dbloadCommandLine() {
        // -u betoffice -p betoffice -d jdbc:mariadb://127.0.0.1/betoffice --tables bo_session -f export.dat --export
        final String[] args = {"-u", "betoffice", "-p", "betoffice",
                "-d", "jdbc:mariadb://127.0.0.1/betoffice",
                "--tables", "bo_session,bo_user",
                "-f", "export.dat",
                "--export"};
        DbloadMain.main(args);
    }

}
