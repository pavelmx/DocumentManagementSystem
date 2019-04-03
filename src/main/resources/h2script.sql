drop table role;
drop table user;
drop table user_role;
drop table document;


create table role(id bigint auto_increment primary key, name varchar(255));
create table user(id bigint auto_increment primary key,activation_code varchar(255), name varchar(50), email varchar(50), password varchar(500), username varchar(20));
create table user_role(id_user bigint, id_role bigint);
create table document(id bigint auto_increment primary key, title varchar(255), DATE_OF_CREATION date, CONTRACT_TERM integer, DOCUMENT_DESCRIPTION varchar(255),
FILENAME varchar(255), CUSTOMER varchar(255), EXPIRED boolean, ID_USER bigint );


insert into role(name) values('ROLE_USER');
insert into role(name) values('ROLE_ADMIN');


insert into user(activation_code ,name, email,password, username) values(null, 'user123', 'user123@email', '$2a$10$CfiuRDgTnLgJa7zYFyoCuuo60r3P8yPRgr4gCrPCe0Q1DZRP.ls3G', 'user123');
insert into user(activation_code ,name, email,password, username) values(null, 'admin123', 'admin123@email', '$2a$10$CfiuRDgTnLgJa7zYFyoCuuo60r3P8yPRgr4gCrPCe0Q1DZRP.ls3G', 'admin123');


insert into user_role(id_user, id_role ) values(1, 1);
insert into user_role(id_user, id_role ) values(2, 2);


insert into document(TITLE,DATE_OF_CREATION ,CONTRACT_TERM ,DOCUMENT_DESCRIPTION ,CUSTOMER ,EXPIRED ,ID_USER , FILENAME )
values('document1', '2019-03-17', 10, 'this document about me',  'customer from bristol', false, 1, null);