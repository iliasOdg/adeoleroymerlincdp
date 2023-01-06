create table band
(
    id   bigint generated by default as identity (start with 1),
    name varchar(255),
    primary key (id)
);
create table member
(
    id   bigint generated by default as identity (start with 1),
    name varchar(255),
    primary key (id)
);
create table band_members
(
    band_id    bigint not null,
    members_id bigint not null,
    primary key (band_id, members_id)
);
alter table band_members
    add constraint FK551i3sllw1wj7ex6nir16blse foreign key (members_id) references member;
alter table band_members
    add constraint FK551i3sllw1wj7ex6nir16blsf foreign key (band_id) references band;
create table event
(
    id       bigint generated by default as identity (start with 1),
    comment  varchar(255),
    nb_stars integer,
    img_url  varchar(255),
    title    varchar(255),
    primary key (id)
);
create table event_bands
(
    event_id bigint not null,
    bands_id bigint not null,
    primary key (event_id, bands_id)
);
alter table event_bands
    add constraint FK551i3sllw1wj7ex6nir16blsm foreign key (bands_id) references band;
alter table event_bands
    add constraint FKs4xm7q8i3uxvaiswj1c35nnxw foreign key (event_id) references event;
