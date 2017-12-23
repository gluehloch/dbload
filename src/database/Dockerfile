#FROM gluehloch/mariadb-awi:latest
FROM mariadb:latest
# RUN apt-get -y update && apt-get install -y vim

# TODO Does not work ... cure me!
COPY *.sql /tmp/

# RUN /bin/bash -c 'mysql -u root << /tmp/create_mysql.sql'
# RUN /bin/bash -c 'mysql -u dbload --password=dbload << /tmp/create_mysql_test_schema.sql'

# docker run --name some-mysql -e MYSQL_ROOT_PASSWORD_FILE=/run/secrets/mysql-root -d mariadb:tag