CREATE SEQUENCE IF NOT EXISTS seq_cuenta;
CREATE SEQUENCE IF NOT EXISTS seq_movimiento;
CREATE SEQUENCE IF NOT EXISTS seq_cliente;

DROP TABLE IF EXISTS movimiento;
DROP TABLE IF EXISTS cuenta;
DROP TABLE IF EXISTS cliente;
--DROP TABLE IF EXISTS usuarios;


create sequence IF NOT EXISTS seq_cuenta start with 1 increment by 1;
create sequence IF NOT EXISTS seq_movimiento start with 1 increment by 1;
create sequence IF NOT EXISTS seq_cliente start with 1 increment by 1;

create table IF NOT EXISTS cliente (edad integer, id bigint not null, apellido varchar(255), cargo varchar(255), identificacion varchar(255), nombre varchar(255), tipo varchar(255), primary key (id));
create table IF NOT EXISTS cuenta (fecha_creacion date, id bigint not null, cliente_id bigint, numero_cuenta varchar(255), primary key (id), CONSTRAINT fk_cliente_id FOREIGN KEY (cliente_id) REFERENCES cliente(id));
create table IF NOT EXISTS movimiento (valor numeric(38,2), cuenta_id bigint, id bigint not null, detalle varchar(255), tipo varchar(255), primary key (id), CONSTRAINT fk_cuenta_id FOREIGN KEY (cuenta_id) REFERENCES cuenta(id));


INSERT INTO cliente (id, nombre, apellido, edad, identificacion, tipo) VALUES (1, 'Jaimito', 'Pérez', 38, '001384529', 'C');
INSERT INTO cliente (id, nombre, apellido, edad, identificacion, tipo, cargo) VALUES (2, 'Luis', 'Sánchez', 31, '008283819', 'E', 'Desarrollador');


INSERT INTO cuenta (id, numero_cuenta, fecha_creacion, cliente_id) VALUES (1, '98583xxx000', '08/10/2020', 1);
INSERT INTO cuenta (id, numero_cuenta, fecha_creacion, cliente_id) VALUES (2, '13289xxx000', '01/05/2022', 2);


INSERT INTO movimiento (id, tipo, detalle, valor, cuenta_id) VALUES (1, 'Credito', 'Salario', 1500, 1);
INSERT INTO movimiento (id, tipo, detalle, valor, cuenta_id) VALUES (2, 'Debito', 'Compra Online', 10, 1);
INSERT INTO movimiento (id, tipo, detalle, valor, cuenta_id) VALUES (3, 'Debito', 'Transferencia a otro banco', 12, 1);
INSERT INTO movimiento (id, tipo, detalle, valor, cuenta_id) VALUES (4, 'Credito', 'Ahorro', 80, 1);


INSERT INTO movimiento (id, tipo, detalle, valor, cuenta_id) VALUES (5, 'Credito', 'Salario', 2000, 2);
INSERT INTO movimiento (id, tipo, detalle, valor, cuenta_id) VALUES (6, 'Debito', 'Compra Online', 200, 2);
INSERT INTO movimiento (id, tipo, detalle, valor, cuenta_id) VALUES (7, 'Debito', 'Transferencia a otro banco', 12, 2);
INSERT INTO movimiento (id, tipo, detalle, valor, cuenta_id) VALUES (8, 'Credito', 'Ahorro', 80, 2);



-- Insertar un usuario inicial con rol USER
--INSERT INTO usuarios (id, username, password, role) VALUES
--(1, 'testuser', '$2a$10$kPZo6puR9zGjdfGkG8Pm9eT4jPzF5S8sdVW0skwaIx4WlzvO.Lm/a', 'ROLE_USER');
