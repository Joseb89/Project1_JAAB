create table if not exists reimburse.users (
	employee_id serial primary key,
	first_name VARCHAR(20) not null,
	last_name VARCHAR(40) not null,
	user_email VARCHAR(40) not null,
	user_password VARCHAR(100) not null,
	department VARCHAR(30) not null,
	supervisor VARCHAR(60),
	user_role VARCHAR(13) not null
);

select * from reimburse.users u;

update reimburse.users set user_email = 'laxepo7621@xasems.com' where employee_id = 4; 