drop table role;
drop table user;
drop table user_role;



create table role(id bigint auto_increment primary key, name varchar(255));
create table user(id bigint auto_increment primary key,activation_code varchar(255), name varchar(50), email varchar(50), password varchar(500), username varchar(20), adress varchar(255));
create table user_role(id_user bigint, id_role bigint);



insert into role(name) values('ROLE_USER');
insert into role(name) values('ROLE_ADMIN');


insert into user(activation_code ,name, email,password, username, adress) values(null, 'user123', 'user123@email', '$2a$10$CfiuRDgTnLgJa7zYFyoCuuo60r3P8yPRgr4gCrPCe0Q1DZRP.ls3G', 'user123', null);
insert into user(activation_code ,name, email,password, username, adress) values(null, 'admin123', 'admin123@email', '$2a$10$CfiuRDgTnLgJa7zYFyoCuuo60r3P8yPRgr4gCrPCe0Q1DZRP.ls3G', 'admin123', null);


insert into user_role(id_user, id_role ) values(1, 1);
insert into user_role(id_user, id_role ) values(2, 2);

