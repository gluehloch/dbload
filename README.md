# dbload 
Loading data from a csv file to a database. Great.

### Example
    import de.dbload.Dbload;
    import de.dbload.impl.DefaultDbloadContext;

    DbloadContext context = new DefaultDbloadContext(conn);
    Dbload.read(context, DbloadTest.class);

### TODO
 * Tutorial to setup a docker container
 * Dockerfile to create docker image​
