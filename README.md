# dbload 
Loading data from a csv file to a database. Great.

### Example
    import de.dbload.Dbload;
    import de.dbload.impl.DefaultDbloadContext;

    DbloadContext context = new DefaultDbloadContext(conn);
    Dbload.read(context, DbloadTest.class);

### Development Setup
Before you are able to run the test code, you have to create a property file at `./src/test/resources` with name
`my-db.properties`. Or copy and rename `db.properties` and uncomment your desird database:
```
dbload.database.url = jdbc:h2:./target/liquibase/h2dbload;USER=liquibaseTest;PASSWORD=pass
```

```
mvn clean liquibase:update install
```

### TODO
 * Tutorial to setup a docker container
 * Dockerfile to create docker image​

### 
