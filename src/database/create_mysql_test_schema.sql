create table tablefortest_a (
    id bigint not null auto_increment,
    dbload_bigint_a bigint,
    dbload_bigint_b bigint,
    dbload_integer_a integer,
    dbload_integer_b integer,
    dbload_bit bit,
    dbload_datetime datetime,
    dbload_text_a varchar(10),
    dbload_text_b varchar(20),
    primary key (id)
) ENGINE=InnoDB;

create table tablefortest_b (
    id bigint not null auto_increment,
    dbload_bigint_a bigint,
    dbload_bigint_b bigint,
    dbload_integer_a integer,
    dbload_integer_b integer,
    dbload_bit bit,
    dbload_datetime datetime,
    dbload_text_a varchar(10),
    dbload_text_b varchar(20),
    fk_tablefortest_a bigint,
    primary key (id)
) ENGINE=InnoDB;

alter table tablefortest_b
    add index fkindex (fk_tablefortest_a), 
    add constraint fkconstraint
    foreign key (fk_tablefortest_a) 
    references tablefortest_a (id);
