package de.dbload;

public class DbloadMain {

    public static void main(String[] args) {
        DbloadCommandLineParser dclp = new DbloadCommandLineParser();
        DbloadCommandLineArguments arguments = dclp.parse(args, System.out);

        switch (arguments.getCommand()) {
            case EXPORT -> ExportDatabase.start(arguments, System.out);
            case IMPORT -> ImportDatabase.start(arguments, System.out);
            case HELP -> System.out.println(".");
        }

    }

}
