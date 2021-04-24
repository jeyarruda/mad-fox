create schema madfox_db; 

use madfox_db; 

CREATE USER 'user'@'localhost' IDENTIFIED BY 'development';

GRANT ALL PRIVILEGES ON madfox_db.* TO 'user'@'localhost';

create table user (
    id bigint unsigned not null auto_increment,
    username varchar(50) not null, 
    nickname varchar(20) not null, 
    user_password varchar(255) not null, 
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
    user_id bigint unsigned not null , 
    authorizer_id bigint unsigned not null, 
    primary key (user_id, authorizer_id), 
    foreign key auth_user_fk (user_id) references user (id) on delete restrict on update cascade,
    foreign key auth_authorizer_fk (authorizer_id) references authorizer (id) on delete restrict on update cascade
);

create table post (
    id bigint unsigned not null auto_increment, 
    content varchar(255) not null, 
    time_post datetime not null,
    category varchar(20),
    user_id bigint unsigned not null,
    primary key(id), 
    foreign key post_user_fk (user_id) references user(id)
)