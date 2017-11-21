INSERT INTO role(role_id,role_desc) VALUES (1,'Administrador');
INSERT INTO role(role_id,role_desc) VALUES (2,'Nanny');
INSERT INTO role(role_id,role_desc) VALUES (3,'Cliente');

INSERT INTO user(user_id,email,password,role_id,enabled,firstname,lastname,phone)VALUES(1,'roger.davila@stech.com','$2a$10$4/gq4pJ45ymHDb43HNDuU.AC8RgiE.6gQZPzMmXbMIOTwR9Shq5WC',1,true,'Roger Ivan', 'Dávila Reyna', '8120658867');
INSERT INTO user(user_id,email,password,role_id,enabled,firstname,lastname,phone)VALUES(2,'david.cruz@stech.com','$2a$10$4/gq4pJ45ymHDb43HNDuU.AC8RgiE.6gQZPzMmXbMIOTwR9Shq5WC',1,true,'David Fernando', 'Cruz Florencia', '8120658867');
INSERT INTO user(user_id,email,password,role_id,enabled,firstname,lastname,phone)VALUES(3,'rog.davila94@gmail.com','$2a$10$4/gq4pJ45ymHDb43HNDuU.AC8RgiE.6gQZPzMmXbMIOTwR9Shq5WC',1,true,'Roger Ivan', 'Dávila Reyna', '8120658867');


INSERT INTO booking_type (booking_type_id, booking_type_desc) VALUES (1, 'Bambino Care');
/*INSERT INTO booking_type (booking_type_id, booking_type_desc) VALUES (2, 'Bambino Tutory');
INSERT INTO booking_type (booking_type_id, booking_type_desc) VALUES (3, 'Bambino Events');
INSERT INTO booking_type (booking_type_id, booking_type_desc) VALUES (4, 'Bambino ASAP');*/

INSERT INTO booking_status (booking_status_id, booking_status_desc) VALUES (1, 'Abierta');
INSERT INTO booking_status (booking_status_id, booking_status_desc) VALUES (2, 'Agendada');
INSERT INTO booking_status (booking_status_id, booking_status_desc) VALUES (3, 'Rechazada');
INSERT INTO booking_status (booking_status_id, booking_status_desc) VALUES (4, 'Cancelada');

INSERT INTO event_type (event_type_id,event_type_desc) VALUES (1,'XV');
INSERT INTO event_type (event_type_id,event_type_desc) VALUES (2,'Boda');
INSERT INTO event_type (event_type_id,event_type_desc) VALUES (3,'Piñata');
INSERT INTO event_type (event_type_id,event_type_desc) VALUES (4,'Reunión Familiar');
INSERT INTO event_type (event_type_id,event_type_desc) VALUES (5,'Reunión de Negocios');

INSERT INTO user(user_id,email,password,role_id,enabled,firstname,lastname,phone)VALUES(4,'erika.rodriguez@bambinocare.com','$2a$10$4/gq4pJ45ymHDb43HNDuU.AC8RgiE.6gQZPzMmXbMIOTwR9Shq5WC',2,true,'Erika', 'Rodriguez', '8120658867');
INSERT INTO nanny(nanny_id, age, bambino_reason, course, children_reason, hobbies, comments, qualities, street, degree, school, neighborhood, city, user_id,curp_file, ife_file,degree_file,state) VALUES(1,23,'Observación','NA','','','','','','','','','',4, null, null,null,'state');




