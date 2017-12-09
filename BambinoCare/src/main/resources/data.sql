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

INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(1, 1, 1, 140);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(2, 2, 1, 190);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(3, 3, 1, 240);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(4, 1, 4, 130);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(5, 2, 4, 180);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(6, 3, 4, 230);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(7, 1, 10, 120);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(8, 2, 10, 170);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(9, 3, 10, 220);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(10, 1, 15, 110);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(11, 2, 15, 160);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(12, 3, 15, 210);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(13, 1, 20, 100);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(14, 2, 20, 150);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(15, 3, 20, 200);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(16, 1, 30, 95);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(17, 2, 30, 145);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(18, 3, 30, 195);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(19, 1, 40, 90);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(20, 2, 40, 130);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost) VALUES(21, 3, 40, 180);



