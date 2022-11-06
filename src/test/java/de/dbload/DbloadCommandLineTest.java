package de.dbload;

import org.junit.jupiter.api.Test;

class DbloadCommandLineTest {

    @Test
    void dbloadCommandLine() {
        // -u betoffice -p betoffice -d jdbc:mariadb://127.0.0.1/betoffice --tables bo_session -f export.dat --export
        final String[] args = {"-u", "betoffice", "-p", "betoffice",
                "-d", "jdbc:mariadb://127.0.0.1/betoffice",
                "--tables", "bo_session,bo_user,bo_team,bo_teamalias,bo_grouptype,bo_season,bo_group,bo_team_group,bo_player,bo_location,bo_gamelist,bo_game,bo_goal,bo_gametipp,bo_community,bo_community_user",
                "-f", "export.dat",
                "--export"};
        DbloadMain.main(args);
    }

    @Test
    void dbloadCommandLinePrintHelp() {
        final String[] args = {"--help"};
        DbloadMain.main(args);
    }

}
