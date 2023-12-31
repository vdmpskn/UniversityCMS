INSERT INTO university (name) VALUES ('National Technical University of Ukraine “Igor Sikorsky Kyiv Polytechnic Institute”');

INSERT INTO faculty (university_id, name) VALUES (1, 'Faculty of Informatics and Software Engineering');
INSERT INTO faculty (university_id, name) VALUES (1, 'Faculty of Economics');

INSERT INTO student_group (faculty_id, name) VALUES (1, 'IK-82');
INSERT INTO student_group (faculty_id, name) VALUES (1, 'IK-81');
INSERT INTO student_group (faculty_id, name) VALUES (2, 'IC-83');

INSERT INTO course (name, description, faculty_id) VALUES ('Physics','Teory of Physics', 1);
INSERT INTO course (name, description,  faculty_id) VALUES ('Chemistry','Teory of Physics', 1);
INSERT INTO course (name, description,  faculty_id) VALUES ('Literature','Teory of Physics', 2);

INSERT INTO role(role_id, name) VALUES  (1,'ADMIN');
INSERT INTO role(role_id, name) VALUES  (2,'STUDENT');
INSERT INTO role(role_id, name) VALUES  (3,'PROFESSOR');


INSERT INTO users (username, password, role_id, faculty_id) VALUES ('admin', 'adminpass98', 1 ,1);
INSERT INTO users (username, password, role_id, faculty_id) VALUES ('vdm.pskn', 'password123', 2, 1);
INSERT INTO users (username, password, role_id, faculty_id) VALUES ('jane.smith', 'pass987', 2, 1);
INSERT INTO users (username, password, role_id, faculty_id) VALUES ('professormath', 'secret456', 3, 1);
INSERT INTO users (username, password, role_id, faculty_id) VALUES ('professorsupercool', 'confidential', 3, 2);

INSERT INTO students (user_id, group_id)
SELECT user_id,
       CASE
           WHEN user_id = 2 THEN 1
           WHEN user_id = 3 THEN 2
           ELSE NULL
           END AS group_id
FROM users
WHERE role_id = 2;

INSERT INTO professors (user_id)
SELECT user_id
FROM users
WHERE role_id = 3;


INSERT INTO schedule (course_id, group_id, professor_id,start_time, end_time, date) VALUES (1, 1,1, '2023-07-07 09:00:00', '2023-07-07 11:00:00', '2023-07-07');
INSERT INTO schedule (course_id, group_id,professor_id, start_time, end_time, date) VALUES (2, 1, 2, '2023-07-08 10:00:00', '2023-07-08 12:00:00', '2023-07-08');
INSERT INTO schedule (course_id, group_id,professor_id, start_time, end_time, date) VALUES (3, 1, 1, '2023-07-07 14:00:00', '2023-07-07 16:00:00', '2023-07-07');
