INSERT INTO university (name) VALUES ('National Technical University of Ukraine “Igor Sikorsky Kyiv Polytechnic Institute”');

INSERT INTO faculty (university_id, name) VALUES (1, 'Faculty of Informatics and Software Engineering');
INSERT INTO faculty (university_id, name) VALUES (1, 'Faculty of Economics');

INSERT INTO student_group (faculty_id, name) VALUES (1, 'IK-82');
INSERT INTO student_group (faculty_id, name) VALUES (1, 'IK-81');
INSERT INTO student_group (faculty_id, name) VALUES (2, 'IC-83');

INSERT INTO course (name, description, faculty_id) VALUES ('Physics','Teory of Physics', 1);
INSERT INTO course (name, description,  faculty_id) VALUES ('Chemistry','Teory of Physics', 1);
INSERT INTO course (name, description,  faculty_id) VALUES ('Literature','Teory of Physics', 2);

INSERT INTO users (username, password, role, faculty_id) VALUES ('admin', 'adminpass98', 'admin',1);
INSERT INTO users (username, password, role, faculty_id, group_id, course_id) VALUES ('vdm.pskn', 'password123', 'student', 1, 1,1);
INSERT INTO users (username, password, role, faculty_id, group_id, course_id) VALUES ('jane.smith', 'pass987', 'student', 1, 2, 2);
INSERT INTO users (username, password, role, faculty_id) VALUES ('professormath', 'secret456', 'professor', 1);
INSERT INTO users (username, password, role, faculty_id) VALUES ('professorsupercool', 'confidential', 'professor', 2);

INSERT INTO schedule (course_id, group_id, start_time, end_time, date) VALUES (1, 1, '2023-07-07 09:00:00', '2023-07-07 11:00:00', '2023-07-07');
INSERT INTO schedule (course_id, group_id, start_time, end_time, date) VALUES (2, 1, '2023-07-08 10:00:00', '2023-07-08 12:00:00', '2023-07-08');
INSERT INTO schedule (course_id, group_id, start_time, end_time, date) VALUES (3, 1, '2023-07-07 14:00:00', '2023-07-07 16:00:00', '2023-07-07');