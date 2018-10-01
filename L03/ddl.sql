create schema public
;

comment on schema public is 'standard public schema'
;

alter schema public owner to postgres
;

create sequence hibernate_sequence
;

alter sequence hibernate_sequence owner to postgres
;

create table appointments
(
	appointment_id bigint not null
		constraint appointments_pkey
			primary key,
	name varchar(255)
)
;

alter table appointments owner to postgres
;

create table credentials
(
	id bigint not null
		constraint credentials_pkey
			primary key,
	login varchar(255),
	passhash varchar(255)
)
;

alter table credentials owner to postgres
;

create table departs
(
	depart_id bigint not null
		constraint departs_pkey
			primary key,
	name varchar(255)
)
;

alter table departs owner to postgres
;

create table employes
(
	id bigint not null
		constraint employes_pkey
			primary key,
	city varchar(255),
	fullname varchar(255),
	salary bigint not null,
	appointment_id bigint
		constraint fkb8outbkty0pdn4tlsx3t2fl38
			references appointments,
	credentials_id bigint not null
		constraint uk_k9019il89g5g1ykoetatrd3n2
			unique
		constraint fklriah1bdpie9eax1e77wm0rc3
			references credentials,
	depart_id bigint
		constraint fknqfayasvak6nfciwmdls5dtao
			references departs
)
;

alter table employes owner to postgres
;

