create sequence hibernate_sequence start 1 increment 1;

create table usr (
     id int8 not null,
     email varchar(255),
     password varchar(255) not null,
     username varchar(255) not null,
     primary key (id)
);