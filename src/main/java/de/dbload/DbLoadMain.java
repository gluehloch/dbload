package de.dbload;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class DbLoadMain {

    public static void main(String[] args) {
        Options options = CommandLineParserConfig.create();
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

}
