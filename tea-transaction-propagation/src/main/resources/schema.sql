drop table t_menu if exists;

create table t_menu (
    id bigint auto_increment,
    name varchar(128),
    size varchar(16),
    price bigint,
    create_time timestamp,
    update_time timestamp,
    primary key (id)
);

drop table t_demo if exists;
create table t_demo (
    id bigint auto_increment,
    name varchar(128),
    create_time timestamp,
    update_time timestamp,
    primary key (id)
);