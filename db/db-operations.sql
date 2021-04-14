SELECT * FROM appcourses.users;

insert into users (email, password, username)
values ('sofi@admin.com', 'admin', 'sysadmin');

insert into users (email, password, username) values 
('test@agent.com', 'test', 'testagent'),
('test@student.com', 'test', 'teststudent');

update users set user_role = 'ROLE_ADMIN' where id = 1;
update users set user_role = 'ROLE_AGENT' where id = 2;
update users set user_role = 'ROLE_STUDENT' where id = 3;

select * from users u, students s where u.id = s.user_id;
select * from users u, agents a where u.id = a.user_id;

SELECT * FROM appcourses.agents;

insert into agents (user_id, name, lastname, document_type, document_number) 
values (2, 'agentname', 'agentlastname', 'DNI', 12345678);

select * from agents a, users u where u.id = 2;

SELECT * FROM appcourses.students;

insert into students (user_id, name, lastname, birthday, gender, location) 
values (3, 'studentname', 'studentlastname', '', 'F', 'Argentina');

select * from students s, users u where u.id = 3;

update students set studying = 0, working = 0, income = '100', family_in_charge = 1, dependents = 2, scholarship_status = 'APPROVED' where user_id = 3;

SELECT * FROM appcourses.organizations;

insert into organizations (address, category, contact_number, cuil, foundation_year, name, status, type, agents_id)
values ('orgadress', 'orgcategory', '1234567890', '12345678901', '2000', 'orgname', 'AWAITING_APPROVAL', 'orgtype', 2);

select * from organizations o, agents a where o.agents_id = a.user_id;
select * from organizations o, agents a, users u where o.agents_id = a.user_id and a.user_id = u.id;

update organizations set org_status = 'APPROVED' where id = 1;

SELECT * FROM appcourses.courses;

insert into courses (cost, description, hours, modality, name, quotas, scholarship_quotas, course_status, organizations_id)
values (50, 'course description', '40', 'online', 'java', '15', '5', 'ENROLLMENT', 1);

update courses set course_status = 'ENROLLMENT' where id = 1;

select * from courses c, organizations o where c.organizations_id = o.id;
select * from courses c, organizations o, agents a where c.organizations_id = o.id and o.agents_id = a.user_id;
select * from courses c, organizations o, agents a, users u where c.organizations_id = o.id and o.agents_id = a.user_id and a.user_id = u.id;

SELECT * FROM appcourses.enrollments;

insert into enrollments (enrollment_status, courses_id, students_id, type)
values ('AWAITING_APPROVAL', 1, 3, 'FULL_PAYMENT');

update enrollments set enrollment_status = 'AWAITING_APPROVAL' where id = 1;

select * from enrollments e, courses c, students s where e.courses_id = c.id and e.students_id = s.user_id;

select * from enrollments e, courses c, students s, users u where e.courses_id = c.id and e.students_id = s.user_id and s.user_id = u.id;

select * from enrollments e, courses c, organizations o, agents a, students s, users u 
where e.courses_id = c.id 
and e.students_id = s.user_id
and c.organizations_id = o.id
and o.agents_id = a.user_id
and a.user_id = u.id;
