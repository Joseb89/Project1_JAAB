create table if not exists reimburse.forms (
	form_id serial primary key unique,
	employee_id int4 references reimburse.users(employee_id)  not null,
	first_name VARCHAR(20) not null,
	last_name VARCHAR(40) not null,
	department VARCHAR(30) not null,
	supervisor VARCHAR(40) not null,
	request_date DATE not null,
	request_topic VARCHAR(15) not null,
	request_reason VARCHAR,
	request_cost numeric (5,2),
	request_status VARCHAR(8)
);

select * from reimburse.forms f;

drop table if exists reimburse.forms;