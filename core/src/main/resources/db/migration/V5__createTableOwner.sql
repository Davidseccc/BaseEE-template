create table meltingPoint
(
	id BIGSERIAL
		constraint meltingPoint_pk primary key,
	compound_id INTEGER references compound,
	temperatureFrom decimal not null,
	temperatureTo decimal not null,
	oil boolean not null
);

create table compoundOwner
(
	id BIGSERIAL constraint compoundOwner_pk primary key,
	name varchar(100)
);

alter table compound
	add originalCodename VARCHAR(100) default NULL;

alter table compound
	add owner_id  INTEGER references compoundOwner;

alter table compound
	add meltingPoint  INTEGER references meltingPoint;

create unique index compound_originalCodename_uindex
	on compound (originalCodename);

alter table descriptor
	add NMR TEXT;

alter table descriptor
	add HRMS VARCHAR(255);

alter table descriptor
	add purity INTEGER;

alter table descriptor
	add purityOperator char;

alter table descriptor
	add solubility char;

