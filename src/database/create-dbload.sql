CREATE DATABASE `dbload` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE USER 'dbload'@'localhost' IDENTIFIED BY 'dbload';
CREATE USER 'dbload'@'%' IDENTIFIED BY 'dbload';

REVOKE ALL PRIVILEGES ON * . * FROM 'dbload'@'localhost';
REVOKE ALL PRIVILEGES ON * . * FROM 'dbload'@'%';

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE,
  ALTER, INDEX, DROP, CREATE TEMPORARY TABLES, SHOW VIEW,
  CREATE ROUTINE, ALTER ROUTINE, EXECUTE, CREATE VIEW, EVENT, TRIGGER,
  LOCK TABLES
  ON dbload. * TO 'dbload'@'localhost'
  WITH GRANT OPTION
  MAX_QUERIES_PER_HOUR 0
  MAX_CONNECTIONS_PER_HOUR 0
  MAX_UPDATES_PER_HOUR 0
  MAX_USER_CONNECTIONS 0;

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE,
  ALTER, INDEX, DROP, CREATE TEMPORARY TABLES, SHOW VIEW,
  CREATE ROUTINE, ALTER ROUTINE, EXECUTE, CREATE VIEW, EVENT, TRIGGER,
  LOCK TABLES
  ON dbload. * TO 'dbload'@'%'
  WITH GRANT OPTION
  MAX_QUERIES_PER_HOUR 0
  MAX_CONNECTIONS_PER_HOUR 0
  MAX_UPDATES_PER_HOUR 0
  MAX_USER_CONNECTIONS 0;

/* TODO: Modify you network mask. */
GRANT ALL PRIVILEGES ON *.* TO 'dbload'@'192.168.0.%' IDENTIFIED BY 'dbload' WITH GRANT OPTION;

USE dbload;

drop table if exists person;
create table person (
    id bigint not null auto_increment,
    name varchar(40),
    vorname varchar(40),
    age integer,
    sex varchar(1),
    birthday datetime,
    human bit,
    primary key (id)
) ENGINE=InnoDB;

drop table if exists address;
create table address (
    id bigint not null auto_increment,
    street varchar(10),
    housenumber varchar(20),
    plz varchar(10),
    city varchar(40),
    fk_person_id bigint,
    primary key (id)
) ENGINE=InnoDB;

drop table if exists account;
create table account (
    id bigint not null auto_increment,
    accountnr varchar(10),
    bic varchar(20),
    iban varchar(20),
    fk_person_id bigint,
    primary key(id)
) ENGINE=InnoDB;

alter table address
    add index fk_address_person_id (fk_person_id),
    add constraint cst_address_person_id
    foreign key (fk_person_id)
    references person (id);

alter table account
    add index fk_account_person_id (fk_person_id),
    add constraint cst_account_person_id
    foreign key (fk_person_id)
    references person (id);
