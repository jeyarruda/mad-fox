create schema madfox_bd; 

use madfox_bd; 

create user 'root'@'localhost' indetified by 'development'

grant all privileges on madfox_bd.* to root@'localhost'


create table user (
    id varchar(16) not null,
    username varchar(50) not null, 
    nickname varchar(20) not null, 
    user_password varchar(50) not null, 
    primary key(id), 
    unique key uni_user_nickname (nickname)
);

create table authorizer (
    id bigint unsigned not null auto_increment, 
    authname varchar(20) not null, 
    primary key(id), 
    unique key uni_authorizer_name (authname)
);

create table user_authorizer (
    user_id varchar(16) not null, 
    authorizer_id bigint unsigned not null, 
    primary key (user_id, authorizer_id), 
    foreign key auth_user_fk (user_id) references id (user)
    foreign key auth_authorizer_fk (authorizer_id) references id (authorizer)
);