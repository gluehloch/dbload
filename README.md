# dbload 
Loading data from a csv file to a database. Great. Here comes the example. The CSV file is accessible
by the classpath and is called 'DbloadTest.dat'

### The csv file DbloadTest.dat.

    ### TAB person
    ### id | name    | vorname | age | sex | birthday (date)     | human (bit)
       100 | Winkler | Andre   | 43  | M   | 1971-03-24 06:38:00 | 1
       101 | Winkler | Lars    | 40  | M   | 1974-06-01 07:00:12 | 0

### Example
    import de.dbload.Dbload;
    import de.dbload.impl.DefaultDbloadContext;

    DbloadContext context = new DefaultDbloadContext(conn);
    Dbload.read(context, DbloadTest.class);

### TODO
 * Tutorial to setup a docker container
 * Dockerfile to create docker image​
