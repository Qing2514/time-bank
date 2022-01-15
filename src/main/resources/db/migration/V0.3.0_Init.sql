alter table activity
    drop
        foreign key if exists FKebcmtynv5tnsfyms8f4f8koah;


alter table activity_volunteers
    drop
        foreign key if exists FKf0qaqy3fu1o1sdm3lkvb4o15w;


alter table activity_volunteers
    drop
        foreign key if exists FKfmf8yhngfmd9s2s2ds9l188xj;


alter table article
    drop
        foreign key if exists FKgwrtdbqvt9ucntp82nd3yiuec;


alter table team
    drop
        foreign key if exists FKhyy6xir089fkxhtdtg7he2wfs;


alter table user_following
    drop
        foreign key if exists FKj0avh5q4feap4g0rkus640u4d;


alter table user_following
    drop
        foreign key if exists FKk3oj7eus6p08k33yhjcmysiu5;


alter table user_roles
    drop
        foreign key if exists FKm31t5l9qfo2s8c35gh9lcuemr;


alter table user_roles
    drop
        foreign key if exists FK55itppkw3i07do3h7qoclqd4k;


alter table user_teams
    drop
        foreign key if exists FKjqux92pssgho3pl0eh3vfx25l;


alter table user_teams
    drop
        foreign key if exists FKktn9i8himfsgma0j9pvalcyuh;


alter table user_verification_token
    drop
        foreign key if exists FK9lu98gs6yf15w4o4r9w4w0qum;


drop table if exists activity;


drop table if exists activity_volunteers;


drop table if exists article;


drop table if exists team;


drop table if exists user;


drop table if exists user_following;


drop table if exists user_roles;


drop table if exists user_teams;


drop table if exists user_role;


drop table if exists user_verification_token;


create table activity
(
    id             binary(16)   not null,
    address        varchar(255) not null,
    city           varchar(255) not null,
    date_end       datetime(6)  not null,
    date_published datetime(6)  not null,
    date_start     datetime(6)  not null,
    details        varchar(256) not null,
    number         integer      not null,
    popularity     integer      not null,
    reward         integer      not null,
    status         integer      not null,
    title          varchar(32)  not null,
    type           integer      not null,
    founder_id     varchar(60)  not null,
    primary key (id)
) engine = InnoDB;


create table activity_volunteers
(
    activity_id   binary(16)  not null,
    volunteers_id varchar(60) not null,
    primary key (activity_id, volunteers_id)
) engine = InnoDB;


create table article
(
    id            binary(16)   not null,
    content       longtext     not null,
    date_modified datetime(6)  not null,
    source        varchar(255) not null,
    title         varchar(32)  not null,
    author_id     varchar(60)  not null,
    primary key (id)
) engine = InnoDB;


create table team
(
    id           binary(16)   not null,
    city         varchar(255) not null,
    date_created datetime(6)  not null,
    description  varchar(128),
    name         varchar(24)  not null,
    type         integer      not null,
    founder_id   varchar(60)  not null,
    primary key (id)
) engine = InnoDB;


create table user
(
    id                      varchar(60)  not null,
    city                    varchar(255) not null,
    date_credential_expired datetime(6),
    date_username_updated   datetime(6)  not null,
    email                   varchar(255) not null,
    enabled                 bit          not null,
    expired                 bit          not null,
    full_name               varchar(255) not null,
    locked                  bit          not null,
    password                varchar(60)  not null,
    phone_number            bigint       not null,
    username                varchar(24)  not null,
    primary key (id)
) engine = InnoDB;


create table user_following
(
    followers_id varchar(60) not null,
    following_id varchar(60) not null,
    primary key (followers_id, following_id)
) engine = InnoDB;


create table user_roles
(
    user_id    varchar(60) not null,
    roles_name integer     not null,
    primary key (user_id, roles_name)
) engine = InnoDB;


create table user_teams
(
    members_id varchar(60) not null,
    teams_id   binary(16)  not null,
    primary key (members_id, teams_id)
) engine = InnoDB;


create table user_role
(
    name integer not null,
    primary key (name)
) engine = InnoDB;


create table user_verification_token
(
    token        binary(16)  not null,
    date_expired datetime(6) not null,
    date_sent    datetime(6) not null,
    user_id      varchar(60) not null,
    primary key (token)
) engine = InnoDB;


alter table team
    add constraint UK_g2l9qqsoeuynt4r5ofdt1x2td unique (name);


alter table user
    add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);


alter table user
    add constraint UK_4bgmpi98dylab6qdvf9xyaxu4 unique (phone_number);


alter table user
    add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username);


alter table user_verification_token
    add constraint UK_98w5gv79qjmhs4xxfxwomqsok unique (user_id);


alter table activity
    add constraint FKebcmtynv5tnsfyms8f4f8koah
        foreign key (founder_id)
            references user (id);


alter table activity_volunteers
    add constraint FKf0qaqy3fu1o1sdm3lkvb4o15w
        foreign key (volunteers_id)
            references user (id);


alter table activity_volunteers
    add constraint FKfmf8yhngfmd9s2s2ds9l188xj
        foreign key (activity_id)
            references activity (id);


alter table article
    add constraint FKgwrtdbqvt9ucntp82nd3yiuec
        foreign key (author_id)
            references user (id);


alter table team
    add constraint FKhyy6xir089fkxhtdtg7he2wfs
        foreign key (founder_id)
            references user (id);


alter table user_following
    add constraint FKj0avh5q4feap4g0rkus640u4d
        foreign key (following_id)
            references user (id);


alter table user_following
    add constraint FKk3oj7eus6p08k33yhjcmysiu5
        foreign key (followers_id)
            references user (id);


alter table user_roles
    add constraint FKm31t5l9qfo2s8c35gh9lcuemr
        foreign key (roles_name)
            references user_role (name);


alter table user_roles
    add constraint FK55itppkw3i07do3h7qoclqd4k
        foreign key (user_id)
            references user (id);


alter table user_teams
    add constraint FKjqux92pssgho3pl0eh3vfx25l
        foreign key (teams_id)
            references team (id);


alter table user_teams
    add constraint FKktn9i8himfsgma0j9pvalcyuh
        foreign key (members_id)
            references user (id);


alter table user_verification_token
    add constraint FK9lu98gs6yf15w4o4r9w4w0qum
        foreign key (user_id)
            references user (id);