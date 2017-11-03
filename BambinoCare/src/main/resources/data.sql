INSERT INTO rol(id_rol,rol_desc) VALUES (1,'Administrador');
INSERT INTO rol(id_rol,rol_desc) VALUES (2,'Nanny');
INSERT INTO rol(id_rol,rol_desc) VALUES (3,'Cliente');

INSERT INTO user(id_user,email,password,id_rol,enabled,name,lastname,telephone)VALUES(1,'roger.davila@stech.com','$2a$10$4/gq4pJ45ymHDb43HNDuU.AC8RgiE.6gQZPzMmXbMIOTwR9Shq5WC',1,true,'Roger Ivan', 'Dávila Reyna', '8120658867');
INSERT INTO user(id_user,email,password,id_rol,enabled,name,lastname,telephone)VALUES(2,'david.cruz@stech.com','$2a$10$4/gq4pJ45ymHDb43HNDuU.AC8RgiE.6gQZPzMmXbMIOTwR9Shq5WC',1,true,'David Fernando', 'Cruz Florencia', '8120658867');
INSERT INTO user(id_user,email,password,id_rol,enabled,name,lastname,telephone)VALUES(3,'rog.davila94@gmail.com','$2a$10$4/gq4pJ45ymHDb43HNDuU.AC8RgiE.6gQZPzMmXbMIOTwR9Shq5WC',1,true,'Roger Ivan', 'Dávila Reyna', '8120658867');


INSERT INTO booking_type (id_booking_type, booking_type_desc) VALUES (1, 'Bambino Care');
INSERT INTO booking_type (id_booking_type, booking_type_desc) VALUES (2, 'Bambino Tutory');
INSERT INTO booking_type (id_booking_type, booking_type_desc) VALUES (3, 'Bambino Events');
INSERT INTO booking_type (id_booking_type, booking_type_desc) VALUES (4, 'Bambino ASAP');

INSERT INTO booking_status (id_booking_status, booking_status_desc) VALUES (1, 'Abierta');
INSERT INTO booking_status (id_booking_status, booking_status_desc) VALUES (2, 'Agendada');
INSERT INTO booking_status (id_booking_status, booking_status_desc) VALUES (3, 'Rechazada');
INSERT INTO booking_status (id_booking_status, booking_status_desc) VALUES (4, 'Cancelada');

INSERT INTO event_type (id_event_type,event_type_desc) VALUES (1,'XV');
INSERT INTO event_type (id_event_type,event_type_desc) VALUES (2,'Boda');
INSERT INTO event_type (id_event_type,event_type_desc) VALUES (3,'Piñata');
INSERT INTO event_type (id_event_type,event_type_desc) VALUES (4,'Reunión Familiar');
INSERT INTO event_type (id_event_type,event_type_desc) VALUES (5,'Reunión de Negocios');

INSERT INTO user(id_user,email,password,id_rol,enabled,name,lastname,telephone)VALUES(4,'erika.rodriguez@bambinocare.com','$2a$10$4/gq4pJ45ymHDb43HNDuU.AC8RgiE.6gQZPzMmXbMIOTwR9Shq5WC',1,true,'Erika', 'Rodriguez', '8120658867');
INSERT INTO nanny(id_nanny, age, bambino_reason, capacitation_certification, children_reason, hobby, observation, qualitie, street, study_level, study_place, suburb, town, id_user,curp, ife,college_degree,state) VALUES(1,23,'Observación','NA','','','','','','','','','',4, null, null,null,'state')




