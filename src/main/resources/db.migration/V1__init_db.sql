create sequence hibernate_sequence start 1 increment 1;

create table roles (
    id  int8 not null,
    created timestamp,
    status varchar(255),
    updated timestamp,
    name varchar(255),
    primary key (id)
);

create table usr (
    id  int8 not null,
    created timestamp,
    status varchar(255),
    updated timestamp,
    email varchar(255),
    first_name varchar(255),
    password varchar(255),
    second_name varchar(255),
    username varchar(255),
    primary key (id)
);

create table usr_roles (
    usr_id int8 not null,
    role_id int8 not null
);

alter table if exists usr_roles add constraint fk_user_roles_roles foreign key (role_id) references roles
alter table if exists usr_roles add constraint fk_user_roles_user foreign key (usr_id) references usr