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

Beispiele:
* Export der kompletten Betoffice Datenbank (Linux Shell Line Break!):
  ```
  java -jar dbload-*.jar -u betoffice -p betoffice \
  -d jdbc:mariadb://127.0.0.1/betoffice \
  --tables bo_session,bo_user,bo_team,bo_teamalias,\
  bo_grouptype,bo_season,bo_group,bo_team_group,bo_player,bo_location,\
  bo_gamelist,bo_game,bo_goal,bo_gametipp,bo_community,bo_community_user \
  -f betoffice-all.dat --export
  ```
* Export der Stammdaten der Betoffice Datenbank
  ```
  java -jar dbload-*.jar -u betoffice -p betoffice \
  -d jdbc:mariadb://127.0.0.1/betoffice \
  --tables bo_user,bo_team,bo_teamalias,bo_grouptype \
  -f betoffice-core.dat --export
  ```

### TODO
 * Tutorial to setup a docker container
 * Dockerfile to create docker image​

### 
