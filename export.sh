#!/bin/sh
java -jar ./target/dbload-0.7.0-SNAPSHOT.jar -u betoffice -p betoffice -d jdbc:mariadb://127.0.0.1/betoffice --tables bo_session -f export.dat --export
