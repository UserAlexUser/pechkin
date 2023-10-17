create table roles (
    id bigserial,
    role_name varchar(255),
    primary key (id)
);

create table usr (
    id bigserial,
    created timestamp,
    email varchar(255),
    first_name varchar(255),
    password varchar(255),
    second_name varchar(255),
    status varchar(255),
    updated timestamp,
    username varchar(255),
    primary key (id)
);

create table user_roles (
    usr_id bigint not null,
    role_id bigint not null
);

alter table if exists usr_roles
    add constraint fk_user_roles_roles
    foreign key (role_id) references roles;

alter table if exists usr_roles
    add constraint fk_user_roles_user
    foreign key (usr_id) references usr;