/*drop table FILM_GENRE cascade;
drop table FILM_USERS_LIKES cascade;
drop table FILM cascade;
drop table FRIENDS cascade;
drop table GENRE cascade;
drop table RATING cascade;
drop table USERS cascade;*/

create table IF NOT EXISTS RATING
(
    RATING_ID   INTEGER auto_increment,
    RATING_NAME CHARACTER VARYING(10),
    constraint RATING_PK
        primary key (RATING_ID)
);

create table IF NOT EXISTS GENRE
(
    GENRE_ID   INTEGER auto_increment,
    GENRE_NAME CHARACTER VARYING(20),
    constraint "GENRE_pk"
        primary key (GENRE_ID)
);

create table IF NOT EXISTS FILM
(
    FILM_ID      INTEGER generated by default as identity primary key,
    FILM_NAME    CHARACTER VARYING(100) not null,
    DESCRIPTION  CHARACTER VARYING(200),
    RELEASE_DATE DATE,
    DURATION     INTEGER,
    RATING_ID    INTEGER,
    constraint "FILM_RATING_null_fk"
        foreign key (RATING_ID) references RATING
);

create table IF NOT EXISTS FILM_GENRE
(
    FILM_ID  INTEGER,
    GENRE_ID INTEGER,
    constraint "FILM_GENRE_FILM_null_fk"
        foreign key (FILM_ID) references FILM,
    constraint "FILM_GENRE_GENRE_null_fk"
        foreign key (GENRE_ID) references GENRE
);

create table IF NOT EXISTS USERS
(
    USER_ID  INTEGER generated by default as identity primary key,
    EMAIL    CHARACTER VARYING(30) not null,
    LOGIN    CHARACTER VARYING(50),
    NAME     CHARACTER VARYING(100),
    BIRTHDAY DATE
);

create table IF NOT EXISTS FILM_USERS_LIKES
(
    FILM_ID INTEGER,
    USER_ID INTEGER,
    constraint "FILM_USERS_LIKES_FILM_null_fk"
        foreign key (FILM_ID) references FILM,
    constraint "FILM_USERS_LIKES_USERS_null_fk"
        foreign key (USER_ID) references USERS
);

create table IF NOT EXISTS FRIENDS
(
    FRIEND_1     INTEGER,
    FRIEND_2     INTEGER,
    CONFIRMATION BOOLEAN not null,
    constraint "FRIENDS_USERS_null_fk1"
        foreign key (FRIEND_1) references USERS,
    constraint "FRIENDS_USERS_null_fk2"
        foreign key (FRIEND_2) references USERS
);