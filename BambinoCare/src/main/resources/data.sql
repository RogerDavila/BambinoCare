INSERT INTO role(role_id,role_desc) VALUES (1,'Administrador');
INSERT INTO role(role_id,role_desc) VALUES (2,'Nanny');
INSERT INTO role(role_id,role_desc) VALUES (3,'Cliente');

INSERT INTO user(user_id,email,password,role_id,enabled,firstname,lastname,phone)VALUES(1,'roger.davila@stech.com','$2a$10$4/gq4pJ45ymHDb43HNDuU.AC8RgiE.6gQZPzMmXbMIOTwR9Shq5WC',1,true,'Roger Ivan', 'Dávila Reyna', '8120658867');
INSERT INTO user(user_id,email,password,role_id,enabled,firstname,lastname,phone)VALUES(2,'david.cruz@stech.com','$2a$10$4/gq4pJ45ymHDb43HNDuU.AC8RgiE.6gQZPzMmXbMIOTwR9Shq5WC',1,true,'David Fernando', 'Cruz Florencia', '8120658867');
INSERT INTO user(user_id,email,password,role_id,enabled,firstname,lastname,phone)VALUES(3,'rog.davila94@gmail.com','$2a$10$4/gq4pJ45ymHDb43HNDuU.AC8RgiE.6gQZPzMmXbMIOTwR9Shq5WC',1,true,'Roger Ivan', 'Dávila Reyna', '8120658867');


INSERT INTO booking_type (booking_type_id, booking_type_desc) VALUES (1, 'Bambino Care');
INSERT INTO booking_type (booking_type_id, booking_type_desc) VALUES (2, 'Bambino Tutoring');
INSERT INTO booking_type (booking_type_id, booking_type_desc) VALUES (3, 'Bambino Events');
INSERT INTO booking_type (booking_type_id, booking_type_desc) VALUES (4, 'Bambino ASAP');

INSERT INTO booking_status (booking_status_id, booking_status_desc) VALUES (1, 'Pendiente Pago');
INSERT INTO booking_status (booking_status_id, booking_status_desc) VALUES (2, 'Abierta');
INSERT INTO booking_status (booking_status_id, booking_status_desc) VALUES (3, 'Agendada');
INSERT INTO booking_status (booking_status_id, booking_status_desc) VALUES (4, 'Rechazada');
INSERT INTO booking_status (booking_status_id, booking_status_desc) VALUES (5, 'Cancelada');

INSERT INTO event_type (event_type_id,event_type_desc) VALUES (1,'XV');
INSERT INTO event_type (event_type_id,event_type_desc) VALUES (2,'Boda');
INSERT INTO event_type (event_type_id,event_type_desc) VALUES (3,'Piñata');
INSERT INTO event_type (event_type_id,event_type_desc) VALUES (4,'Reunión Familiar');
INSERT INTO event_type (event_type_id,event_type_desc) VALUES (5,'Reunión de Negocios');

INSERT INTO user(user_id,email,password,role_id,enabled,firstname,lastname,phone)VALUES(4,'erika.rodriguez@bambinocare.com','$2a$10$4/gq4pJ45ymHDb43HNDuU.AC8RgiE.6gQZPzMmXbMIOTwR9Shq5WC',2,true,'Erika', 'Rodriguez', '8120658867');
INSERT INTO nanny(nanny_id, age, bambino_reason, career,hobbies, qualities, street, school, neighborhood, city, user_id,curp_file, ife_file,degree_file,state) VALUES(1,23,'Me gustan los niños','Licenciatura de Educación Preescolar','Leer','Trabajo bajo presión','Vasconcelos 314','UDEM','Los Sabinos','San Pedro Garza García',4, '', '',null,'Nuevo León');

/* COSTOS BAMBINO CARE */
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(1, 1, 1, 140,1);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(2, 2, 1, 190,1);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(3, 3, 1, 240,1);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(4, 1, 4, 130,1);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(5, 2, 4, 180,1);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(6, 3, 4, 230,1);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(7, 1, 10, 120,1);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(8, 2, 10, 170,1);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(9, 3, 10, 220,1);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(10, 1, 15, 110,1);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(11, 2, 15, 160,1);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(12, 3, 15, 210,1);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(13, 1, 20, 100,1);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(14, 2, 20, 150,1);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(15, 3, 20, 200,1);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(16, 1, 30, 95,1);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(17, 2, 30, 145,1);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(18, 3, 30, 195,1);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(19, 1, 40, 90,1);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(20, 2, 40, 130,1);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(21, 3, 40, 180,1);

/* COSTOS BAMBINO TUTORY */
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(22, 1, 1, 300,2);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(23, 1, 5, 250,2);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(24, 1, 10, 200,2);

/* COSTOS BAMBINO EVENTS */
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id, cost_extra_hour) VALUES(25, 10, 4, 500,3, 400);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id, cost_extra_hour) VALUES(26, 15, 4, 625,3, 500);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id, cost_extra_hour) VALUES(27, 20, 4, 750,3, 600);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id, cost_extra_hour) VALUES(28, 25, 4, 875,3, 700);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id, cost_extra_hour) VALUES(29, 30, 4, 1000,3, 800);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id, cost_extra_hour) VALUES(30, 35, 4, 1125,3, 900);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id, cost_extra_hour) VALUES(31, 40, 4, 1250,3, 1000);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id, cost_extra_hour) VALUES(32, 45, 4, 1375,3, 1100);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id, cost_extra_hour) VALUES(33, 50, 4, 1500,3, 1200);

/* COSTOS BAMBINO ASAP */
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(34, 1, 1, 250,4);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(35, 2, 1, 300,4);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(36, 3, 1, 350,4);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(37, 1, 4, 200,4);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(38, 2, 4, 250,4);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(39, 3, 4, 300,4);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(40, 1, 10, 150,4);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(41, 2, 10, 200,4);
INSERT INTO cost(cost_id, bambino_quantity, hour_quantity, cost, booking_type_id) VALUES(42, 3, 10, 250,4);

INSERT INTO city(city_id, city_desc) VALUES (1,'Monterrey');
INSERT INTO city(city_id, city_desc) VALUES (2,'San Pedro');
INSERT INTO city(city_id, city_desc) VALUES (3,'Santa Catarina');

INSERT INTO neighborhood(neighborhood_id, neighborhood_desc, city_id) VALUES (1,'Contry', 1);
INSERT INTO neighborhood(neighborhood_id, neighborhood_desc, city_id) VALUES (2,'Valle Alto', 1);
INSERT INTO neighborhood(neighborhood_id, neighborhood_desc, city_id) VALUES (3,'San Jerónimo', 1);
INSERT INTO neighborhood(neighborhood_id, neighborhood_desc, city_id) VALUES (4,'Cumbres', 1);

INSERT INTO state(state_id, state_desc) VALUES (1, 'Nuevo León');

INSERT INTO payment_type(payment_type_id, payment_type_desc) VALUES (1, 'Paypal');
INSERT INTO payment_type(payment_type_id, payment_type_desc) VALUES (2, 'Pago en Oxxo o a cuenta Bancaria');
INSERT INTO payment_type(payment_type_id, payment_type_desc) VALUES (3, 'Pago en efectivo');

INSERT INTO parameter(parameter_id, parameter_key, parameter_value) VALUES (1, 'Hora Apertura', '14:00');
INSERT INTO parameter(parameter_id, parameter_key, parameter_value) VALUES (2, 'Hora Cierre', '');
INSERT INTO parameter(parameter_id, parameter_key, parameter_value) VALUES (3, 'Hora Apertura Fin de Semana', '');
INSERT INTO parameter(parameter_id, parameter_key, parameter_value) VALUES (4, 'Hora Cierre Fin de Semana', '');