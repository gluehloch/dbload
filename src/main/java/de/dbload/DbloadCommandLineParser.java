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

    public static final String COMMAND_EXPORT = "export";
    public static final String COMMAND_IMPORT = "import";
    public static final String COMMAND_CREATE = "createschema";

    private Options parseCommandLine(String[] args) {
        Option exportOption = Option.builder()
                .argName("e")
                .longOpt(COMMAND_EXPORT)
                .desc("Export some tables of the database.")
                .build();

        Option importOption = Option.builder()
                .argName("i")
                .longOpt(COMMAND_IMPORT)
                .desc("Import some tables to the database.")
                .build();

        Option username = Option.builder()
                .argName(USERNAME)
                .longOpt(USER)
                .required()
                .hasArg()
                .desc("Database connection user name.")
                .build();

        Option password = Option.builder()
                .argName(PASSWORD)
                .longOpt(USER_PASSWORD)
                .required()
                .desc("Database connection password.")
                .build();

        Option jdbcUrl = Option.builder()
                .argName(JDBCURL)
                .longOpt("url")
                .required()
                .hasArg()
                .desc("Database connection JDBC url.")
                .build();

        Option file = Option.builder()
                .argName(FILE)
                .longOpt("file")
                .desc("File.")
                .build();

        Option tables = Option.builder()
                .argName(TABLES).longOpt("tables")
                .hasArg()
                .valueSeparator(',')
                .desc("Tables to export.")
                .build();

        Option help = Option.builder()
                .argName(DbloadCommandLineParser.HELP)
                .longOpt("help")
                .desc("print this help")
                .build();

        Options options = new Options();
        options.addOption(exportOption);
        options.addOption(importOption);

        options.addOption(username);
        options.addOption(password);
        options.addOption(jdbcUrl);

        options.addOption(file);
        options.addOption(tables);
        options.addOption(help);

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
            if (commandLine.hasOption(COMMAND_EXPORT)) {
                edp.setCommand(Command.EXPORT);
            } else if (commandLine.hasOption(COMMAND_IMPORT)) {
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
        edp.setTables(commandLine.getOptionValues(TABLES));
    }

    private void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("betoffice database csv import/export tool",
                options);
    }

}
