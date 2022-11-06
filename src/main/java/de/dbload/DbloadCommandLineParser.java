/*
 * ============================================================================
 * Project betoffice-storage Copyright (c) 2000-2014 by Andre Winkler. All
 * rights reserved.
 * ============================================================================
 * GNU GENERAL PUBLIC LICENSE TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND
 * MODIFICATION
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */

package de.dbload;

import java.io.PrintStream;
import java.io.PrintWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import de.dbload.DbloadCommandLineArguments.Command;

/**
 * Command line parser for database export/import.
 *
 * @author Andre Winkler
 */
public class DbloadCommandLineParser {

    public static final String TABLES = "t";
    public static final String FILE = "f";
    public static final String JDBCURL = "d";
    public static final String PASSWORD = "p";
    public static final String USERNAME = "u";
    public static final String HELP = "h";

    public static final String SCHEMANAME = "schema";
    public static final String USER = "user";
    public static final String USER_PASSWORD = "password";
    public static final String SU = "su";
    public static final String SU_PASSWORD = "supassword";

    private Options parseCommandLine(String[] args) {
        Options options = new Options();

        options.addOption("u", "user", true, "Database connection user name.");
        options.addOption("p", "password", true, "Database connection password.");
        options.addOption("d", "url", true, "Database connection JDBC url.");
        
        Option exportOption = Option.builder("export")
                .longOpt("export")
                .desc("Export some tables of the database.")
                .build();

        Option importOption = Option.builder("import")
                .longOpt("import")
                .desc("Import some tables to the database.")
                .build();

        options.addOption("f", "file", true, "Database export or import file.");

        Option tablesOption = Option.builder("tables")
                .longOpt("tables")
                .argName("tableNames")
                .hasArgs()
                .valueSeparator(',')
                .desc("The database tables to export seperated with ','.")
                .build();

        Option helpOption = Option.builder()
                .argName(DbloadCommandLineParser.HELP)
                .longOpt("help")
                .desc("print this help")
                .build();

        options.addOption(exportOption);
        options.addOption(importOption);
        options.addOption(tablesOption);
        options.addOption(helpOption);

        return options;
    }

    /**
     * Parse the command line.
     *
     * @param  args the command line arguments
     * @param  ps   the print stream
     * @return      The properties from the command line
     */
    public DbloadCommandLineArguments parse(String[] args, PrintStream ps) {
        Options options = parseCommandLine(args);
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = null;

        try {
            commandLine = parser.parse(options, args);
        } catch (MissingOptionException ex) {
            ps.println(String.format("%s", ex.getMessage()));
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("DBLoad csv export/import tool", options);
            formatter.printUsage(new PrintWriter(ps), 30, "use me....");
            return null;
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }

        DbloadCommandLineArguments edp = null;
        if (commandLine.hasOption(DbloadCommandLineParser.HELP)) {
            printHelp(options);
        } else {
            edp = new DbloadCommandLineArguments();
            setupConnectionProperties(edp, commandLine);
            if (commandLine.hasOption("export")) {
                edp.setCommand(Command.EXPORT);
            } else if (commandLine.hasOption("import")) {
                edp.setCommand(Command.IMPORT);
            } else {
                System.out.println("Missing command parameter.");
                printHelp(options);
            }
        }

        return edp;
    }

    private void setupConnectionProperties(DbloadCommandLineArguments edp, CommandLine commandLine) {
        edp.setUsername(commandLine.getOptionValue(USERNAME));
        if (commandLine.hasOption(PASSWORD)) {
            edp.setPassword(commandLine.getOptionValue(PASSWORD));
        }
        edp.setJdbcUrl(commandLine.getOptionValue(JDBCURL));
        edp.setFile(commandLine.getOptionValue(FILE));
        if (commandLine.hasOption("tables")) {
            edp.setTables(commandLine.getOptionValues("tables"));
        }
    }

    private void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("betoffice database csv import/export tool",
                options);
    }

}
