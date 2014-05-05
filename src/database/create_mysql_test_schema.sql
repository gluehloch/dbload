create table person (
    id bigint not null auto_increment,
    name varchar(40),
    vorname varchar(40),
    age integer,
    sex bit,
    birthday datetime,
    primary key (id)
) ENGINE=InnoDB;

create table address (
    id bigint not null auto_increment,
    street varchar(10),
    housenumber varchar(20),
    plz varchar(10),
    city varchar(40),
    fk_person_id bigint,
    primary key (id)
) ENGINE=InnoDB;

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

