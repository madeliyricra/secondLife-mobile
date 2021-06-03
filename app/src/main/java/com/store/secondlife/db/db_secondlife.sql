/*----------------categoria----------------*/
DROP TABLE IF EXISTS tb_categoria;
CREATE TABLE tb_categoria ( 
	id_categ INTEGER NOT NULL,
	nom_categ TEXT NOT NULL,
	descrip_categ TEXT NOT NULL,
	estado INTEGER NOT NULL DEFAULT 1,
	
	CONSTRAINT PKcateg PRIMARY KEY(id_categ  AUTOINCREMENT),
	CONSTRAINT  UQcateg_nom UNIQUE (nom_categ ),
	CONSTRAINT CKcateg_est CHECK (estado in (1, 2))   
);
/* estado
		1.- activo
		2.- desactivo
*/     
/*----------------tabla rol----------------*/
DROP TABLE IF EXISTS tb_rol;
CREATE TABLE tb_rol (
	id_rol INTEGER NOT NULL,
	nom_rol TEXT NOT NULL,
	
	CONSTRAINT PKrol PRIMARY KEY (id_rol AUTOINCREMENT),
	CONSTRAINT CKrol_nom CHECK (length(nom_rol)>=3)
);
 
/*----------------tabla usuario----------------*/
DROP TABLE IF EXISTS tb_usuario;
CREATE TABLE tb_usuario (
	id_usua TEXT,
	dni_usua TEXT NOT NULL,
	id_rol INTEGER NOT NULL,
	nom_usua  TEXT NOT NULL,
	ape_usua TEXT NOT NULL,
	tel_usua TEXT NOT NULL,
	fec_nac_usua DATE NOT NULL,

	usuario TEXT NOT NULL,
	pass TEXT NOT NULL,
	email_log TEXT NOT NULL,
	
    estado INTEGER NOT NULL DEFAULT 1,
	
	CONSTRAINT PKusua PRIMARY KEY (id_usua),
	CONSTRAINT FKusua_rol FOREIGN KEY(id_rol) REFERENCES tb_rol(id_rol),
    CONSTRAINT CKusua_dni CHECK (length(dni_usua)=8),
    CONSTRAINT CKusua_dato CHECK (length(nom_usua)>=2 AND (length(ape_usua)>=2)),
    CONSTRAINT CKusua_tel CHECK (length(tel_usua)=9),
	
	CONSTRAINT UQlogin UNIQUE(usuario, email_log),
    CONSTRAINT CKusua CHECK(length(usuario)>=7),
    CONSTRAINT CKpass CHECK (length(pass)>=7),
    CONSTRAINT CKlogins_est CHECK (estado in (1, 2)),
    CONSTRAINT CKlogin_email CHECK (length(email_log)>=10)
	
	CONSTRAINT CKemp_est CHECK (estado IN (1, 2))
);

DROP TRIGGER IF EXISTS tg_insertar_idusuario;
CREATE TRIGGER tg_insertar_idusuario
AFTER INSERT ON tb_usuario
FOR EACH ROW
BEGIN
    -- insertar id
	UPDATE tb_usuario
	SET id_usua=SUBSTR(REPLACE(SUBSTR(
      QUOTE(ZEROBLOB((5 + 1) / 2)), 3, 5), '0', '0'), 1, 5 - length((SELECT COUNT(*) FROM tb_usuario)))
      || SUBSTR(((SELECT COUNT(*) FROM tb_usuario)), 1, 5)
	 WHERE id_usua IS NULL;
END;	

/*---------------tabla numero de cuenta----------------*/
DROP TABLE IF EXISTS tb_tarjeta;
CREATE TABLE tb_tarjeta (
	id_tarj TEXT,
    tip_tarj TEXT NOT NULL,
    num_tarj TEXT NOT NULL,
    fec_venc DATE NOT NULL,
    cvv INTEGER NOT NULL,
    id_usua TEXT NOT NULL,
	
	CONSTRAINT PKtarjeta PRIMARY KEY (id_tarj),
	CONSTRAINT FKtarj_usua FOREIGN KEY(id_usua)REFERENCES tb_usuario(id_usua),
	CONSTRAINT CKtarj_tip CHECK (length(tip_tarj)>=4),
	CONSTRAINT CKtarj_num CHECK (length(num_tarj)=16),
	CONSTRAINT CKtarj_cvv CHECK (cvv>=100 and cvv<=999)
);
	
DROP TRIGGER IF EXISTS  tg_insertar_idtarjeta;
CREATE TRIGGER tg_insertar_idtarjeta
AFTER INSERT ON tb_tarjeta
FOR EACH ROW
BEGIN
	-- agregar id
	UPDATE tb_tarjeta
	SET id_tarj=SUBSTR(REPLACE(SUBSTR(
      QUOTE(ZEROBLOB((5 + 1) / 2)), 3, 5), '0', '0'), 1, 5 - length((SELECT COUNT(*) FROM tb_tarjeta)))
      || SUBSTR(((SELECT COUNT(*) FROM tb_tarjeta)), 1, 5)
	 WHERE id_tarj IS NULL;
END

/*----------------tabla departamento----------------*/
DROP TABLE IF EXISTS tb_departamento;
CREATE TABLE tb_departamento (
	id_dep INTEGER NOT NULL,
	nom_dep TEXT NOT NULL,
	
	CONSTRAINT PKdepar PRIMARY KEY (id_dep AUTOINCREMENT),
	CONSTRAINT CKdepar_nom CHECK (length(nom_dep)>=3)
);

/*----------------tabla provincia ----------------*/
DROP TABLE IF EXISTS tb_provincia;
CREATE TABLE tb_provincia (
	id_prov INTEGER NOT NULL,
	id_dep INTEGER NOT NULL,
	nom_prov TEXT NOT NULL,
	
	CONSTRAINT PKprov PRIMARY KEY(id_prov AUTOINCREMENT),
	CONSTRAINT FKprov_dep FOREIGN KEY(id_dep) REFERENCES tb_departamento(id_dep),
	CONSTRAINT CKprov_nom CHECK (length(nom_prov)>=3)
);

/*----------------tabla registro----------------*/
DROP TABLE IF EXISTS tb_distrito;
CREATE TABLE tb_distrito ( -- (ESTABLECIMIENTO - Cercado de Lima)
    id_dist INTEGER NOT NULL,    
	id_prov INTEGER NOT NULL, 
	nom_dist TEXT NOT NULL,
	
	CONSTRAINT PKdist primary key(id_dist AUTOINCREMENT),
	CONSTRAINT FKdist_prov FOREIGN KEY(id_prov) REFERENCES tb_provincia(id_prov),
	CONSTRAINT CKdist_nom  CHECK(length(nom_dist)>=3)
);

/*----------------tabla direccion----------------*/
DROP TABLE IF EXISTS tb_direccion;
CREATE TABLE tb_direccion(
	id_direc TEXT,
    latitud REAL NOT NULL,
    longitud REAL NOT NULL,
    desc_direc TEXT NOT NULL,
    etiqueta TEXT NOT NULL,
    id_dist  INTEGER NOT NULL,
	id_usua TEXT NOT NULL,
	
	CONSTRAINT PKdireccion PRIMARY KEY(id_direc),
    CONSTRAINT FKdirec_usua FOREIGN KEY(id_usua) REFERENCES tb_usuario(id_usua),
	CONSTRAINT FKdirec_dist FOREIGN KEY(id_dist) REFERENCES tb_distrito(id_dist),
	CONSTRAINT CKdirec_desc CHECK (length(desc_direc)>=4)
);

DROP TRIGGER IF EXISTS tg_insertar_iddireccion;
CREATE TRIGGER tg_insertar_iddireccion
AFTER INSERT ON tb_direccion
FOR EACH ROW
BEGIN
	-- agregar id
	UPDATE tb_direccion
	SET id_direc=SUBSTR(REPLACE(SUBSTR(
      QUOTE(ZEROBLOB((5 + 1) / 2)), 3, 5), '0', '0'), 1, 5 - length((SELECT COUNT(*) FROM tb_direccion)))
      || SUBSTR(((SELECT COUNT(*) FROM tb_direccion)), 1, 5)
	 WHERE id_direc IS NULL;
END;      

/*---------------- tabla registro ----------------*/
DROP TABLE IF EXISTS tb_registro;
CREATE TABLE tb_registro (
	id_regis TEXT,
	id_categ INTEGER NOT NULL,
	id_usua TEXT NOT NULL,
	descrip_prod TEXT NOT NULL,
	observacion TEXT,
	fecha_regis datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	stock INTEGER NOT NULL,
	precio REAL NOT NULL,
    image TEXT NOT NULL,
	calidad REAL NOT NULL,
	estado INTEGER NOT NULL DEFAULT 1,
	
	CONSTRAINT PKregistro PRIMARY KEY(id_regis),
    CONSTRAINT FKregis_categ FOREIGN KEY (id_categ) REFERENCES tb_categoria(id_categ),
    CONSTRAINT FKregis_usua FOREIGN KEY (id_usua) REFERENCES tb_usuario(id_usua),
    CONSTRAINT CKregis_prod CHECK (length(descrip_prod)>=10 AND length(observacion)>=10),
    CONSTRAINT CKregis_prec CHECK (precio>=5.0),
    CONSTRAINT CKregis_stock CHECK (stock>=0 AND stock <=100),
    CONSTRAINT CKregis_cal CHECK (calidad>=1 AND calidad<=10),
    CONSTRAINT CKregis_esta CHECK (estado IN (1, 2))
);

/* calidad
		0-3  >> mal estado: inservible, falta de algun componente fisico, daño grave en la pintura y/o cuerpo,
				software brickeado o bloqueado. (tiempo de uso < 2 años)
		3-5  >> estado regular: daño en la pintura, cuerpo dañado o software bloqueado. (tiempo de uso < 1 año)
		5-7  >> estado bueno: ligeros rayones en el cuerpo. (tiempo de uso < 6 meses)
        7-10 >> estado excelente: practicamente como nuevo sin señales de uso. (tiempo de uso < 3 meses)
	estado
		1.- activo (aceptado)
		2.- desactivo (no aceptado)
*/

DROP TRIGGER IF EXISTS tg_insertar_idregistro;
CREATE TRIGGER tg_insertar_idregistro
AFTER INSERT ON tb_registro
FOR EACH ROW
BEGIN
	-- insertar id
	UPDATE tb_registro
	SET id_regis=SUBSTR(REPLACE(SUBSTR(
	  QUOTE(ZEROBLOB((5 + 1) / 2)), 3, 5), '0', '0'), 1, 5 - length((SELECT COUNT(*) FROM tb_registro)))
	  || SUBSTR(((SELECT COUNT(*) FROM tb_registro)), 1, 5)
	 WHERE id_regis IS NULL;
END;

/*----------------tabla producto----------------*/    
DROP TABLE IF EXISTS tb_producto;
CREATE TABLE tb_producto (
	id_prod TEXT,
    cod_prod TEXT NOT NULL,
	id_categ INTEGER NOT NULL,
    mar_prod TEXT NOT NULL,
    mod_prod TEXT NOT NULL,
	descrip_prod TEXT NOT NULL,
    observacion TEXT NOT NULL,
	fec_comp_prod datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    stock INTEGER NOT NULL,
    precio REAL NOT NULL,
	image TEXT NOT NULL,
    calidad REAL NOT NULL,
	estado INTEGER NOT NULL DEFAULT 2,
	
	CONSTRAINT PKprod PRIMARY KEY (id_prod),
    CONSTRAINT FKprod_categ FOREIGN KEY (id_categ) REFERENCES tb_categoria(id_categ),
	CONSTRAINT CKprod_desc CHECK (length(descrip_prod)>=10 AND length(observacion)>=10),
    CONSTRAINT CKprod_cal_comp CHECK (calidad>=0 AND calidad<=10),
    CONSTRAINT CKprod_prec CHECK (precio>=5.0),
    CONSTRAINT CKprod_esta CHECK (estado IN (1, 2))
);
/* calidad
		0-3  >> mal estado: inservible, falta de algun componente fisico, daño grave en la pintura y/o cuerpo,
				software brickeado o bloqueado. (tiempo de uso < 2 años)
		3-5  >> estado regular: daño en la pintura, cuerpo dañado o software bloqueado. (tiempo de uso < 1 año)
		5-7  >> estado bueno: ligeros rayones en el cuerpo. (tiempo de uso < 6 meses)
        7-10 >> estado excelente: practicamente como nuevo sin señales de uso. (tiempo de uso < 3 meses)
	estado
		1.- activo (listo para la venta)
		2.- desactivo (en reparacion)
*/

DROP TRIGGER IF EXISTS tg_insertar_idproducto;
CREATE TRIGGER tg_insertar_idproducto
AFTER INSERT ON tb_producto
FOR EACH ROW
BEGIN
	-- insertar id
	UPDATE tb_producto
	SET id_prod=SUBSTR(REPLACE(SUBSTR(
	  QUOTE(ZEROBLOB((5 + 1) / 2)), 3, 5), '0', '0'), 1, 5 - length((SELECT COUNT(*) FROM tb_producto)))
	  || SUBSTR(((SELECT COUNT(*) FROM tb_producto)), 1, 5)
	 WHERE id_prod IS NULL;
END;

/*----------------tabla boleta----------------*/
DROP TABLE IF EXISTS tb_boleta;
CREATE TABLE tb_boleta (
	num_bol TEXT NOT NULL,
	id_usua TEXT NOT NULL,
	tipo_pago INTEGER NOT NULL,
    descrip_pago TEXT NOT NULL,
    id_direc TEXT NOT NULL,
	fec_bol datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	impo_bol REAL NOT NULL,
	envio REAL NOT NULL,
	total_bol REAL NOT NULL,
	
	CONSTRAINT PKbol PRIMARY KEY (num_bol),
    CONSTRAINT FKbol_usua FOREIGN KEY (id_usua) REFERENCES tb_login(id_usua),
    CONSTRAINT FKbol_direc FOREIGN KEY (id_direc) REFERENCES tb_direccion(id_direc),
    CONSTRAINT CKbol_impo CHECK (impo_bol>=1.0),
    CONSTRAINT CKbol_envio CHECK  (envio>=1.0),
    CONSTRAINT CKbol_total CHECK  (total_bol>=5.0),
    CONSTRAINT CKtip_pago CHECK (tipo_pago IN (1, 2))
);
/*
	1 - tarjeta
    2 - paypal
*/

DROP TRIGGER IF EXISTS tg_insertar_idboleta;
CREATE TRIGGER tg_insertar_idboleta
AFTER INSERT ON tb_boleta 
FOR EACH ROW
BEGIN
	-- insertar id
	UPDATE tb_boleta 
	SET num_bol=SUBSTR(REPLACE(SUBSTR(
	  QUOTE(ZEROBLOB((8 + 1) / 2)), 3, 8), '0', '0'), 1, 8 - length((SELECT COUNT(*) FROM tb_boleta)))
	  || SUBSTR(((SELECT COUNT(*) FROM tb_boleta)), 1, 8)
	 WHERE num_bol IS NULL;
END;
/*----------------tabla detalle de boleta----------------*/
DROP TABLE IF EXISTS tb_detalle_boleta;
CREATE TABLE tb_detalle_boleta(
	num_det_bol TEXT NOT NULL,
	num_bol  TEXT NOT NULL,
	id_prod TEXT NOT NULL,
	cant_prod  INTEGER NOT NULL,
	sub_tot REAL NOT NULL,
	
	CONSTRAINT PKdetalbol PRIMARY KEY (num_det_bol),
    CONSTRAINT FKdetalbol_bol FOREIGN KEY (num_bol) REFERENCES tb_boleta(num_bol),
    CONSTRAINT FKdetalbol_prod FOREIGN KEY (id_prod) REFERENCES tb_producto(id_prod),
    CONSTRAINT CKdetalbol_cant CHECK (cant_prod>=1 AND cant_prod<=5),
    CONSTRAINT CKdetalbol_sub CHECK (sub_tot>=5.0 AND sub_tot<=5000)
);

DROP TRIGGER IF EXISTS tg_insertar_iddetalleboleta;
CREATE TRIGGER tg_insertar_iddetalleboleta
BEFORE INSERT ON tb_detalle_boleta
FOR EACH ROW
BEGIN
	UPDATE tb_detalle_boleta
	SET num_det_bol=SUBSTR(REPLACE(SUBSTR(
	  QUOTE(ZEROBLOB((8 + 1) / 2)), 3, 8), '0', '0'), 1, 8 - length((SELECT COUNT(*) FROM tb_detalle_boleta)))
	  || SUBSTR(((SELECT COUNT(*) FROM tb_detalle_boleta)), 1, 8)
	 WHERE num_det_bol IS NULL;
END;
/*---------------------ingreso de datos-----------------------*/
/*insert into tb_categoria(nom_categ, descrip_categ) values ('Laptops', 'Computadoras portátiles de peso y tamaño ligero, su tamaño es aproximado al de un portafolio.');
insert into tb_categoria(nom_categ, descrip_categ) values ('Impresoras', 'Periféricos encargados de transferir las imágenes y textos de tu PC a papel.');
insert into tb_categoria(nom_categ, descrip_categ) values ('Smartphones', 'Teléfonos celulares inteligentes.');
insert into tb_categoria(nom_categ, descrip_categ) values ('Cámaras', 'Aparatos para registrar imágenes estáticas o en movimiento.');
insert into tb_categoria(nom_categ, descrip_categ) values ('Wearables', 'Dispositivos que se usan en el cuerpo humano y que interactúan con otros aparatos para transmitir o recoger algún tipo de datos.');
insert into tb_categoria(nom_categ, descrip_categ) values ('Smart TV´s', 'Televisores inteligentes.');
insert into tb_categoria(nom_categ, descrip_categ) values ('Audio', 'Dispositivos que reproducen, graban o procesan sonido.');

insert into tb_departamento values (1, 'Lima');

insert into tb_provincia values (null, 1, 'Lima');

insert into tb_distrito values (null,1, 'Ancón');
insert into tb_distrito values (null, 1, 'Ate');
insert into tb_distrito values (null, 1, 'Barranco');   
insert into tb_distrito values (null, 1, 'Breña'); 
insert into tb_distrito values (null, 1, 'Carabayllo');
insert into tb_distrito values (null, 1, 'Chaclacayo');
insert into tb_distrito values (null, 1, 'Chorrillos');
insert into tb_distrito values (null, 1, 'Cieneguilla');
insert into tb_distrito values (null, 1, 'Comas');
insert into tb_distrito values (null, 1, 'El Agustino'); 
insert into tb_distrito values (null, 1, 'Independencia'); 
insert into tb_distrito values (null, 1, 'Jesús María');
insert into tb_distrito values (null, 1, 'La Molina');   
insert into tb_distrito values (null, 1, 'La Victoria');   
insert into tb_distrito values (null, 1, 'Lima Centro');  
insert into tb_distrito values (null, 1, 'Lince');   
insert into tb_distrito values (null, 1, 'Los Olivos');   
insert into tb_distrito values (null, 1, 'Lurín');  
insert into tb_distrito values (null, 1, 'Magdalena');  
insert into tb_distrito values (null, 1, 'Miraflores');   
insert into tb_distrito values (null, 1, 'Pachacamac'); 
insert into tb_distrito values (null, 1, 'Pucusana'); 
insert into tb_distrito values (null, 1, 'Pblo. Libre');  
insert into tb_distrito values (null, 1, 'Puente Piedra');   
insert into tb_distrito values (null, 1, 'Punta Hermosa'); 
insert into tb_distrito values (null, 1, 'Punta Negra'); 
insert into tb_distrito values (null, 1, 'Rímac'); 
insert into tb_distrito values (null, 1, 'San Bartolo');  
insert into tb_distrito values (null, 1, 'San Borja');  
insert into tb_distrito values (null, 1, 'San Isidro');  
insert into tb_distrito values (null, 1, 'San Juan de Lurigancho');  
insert into tb_distrito values (null, 1, 'San Juan de Miraflores');  
insert into tb_distrito values (null, 1, 'San Luis');   
insert into tb_distrito values (null, 1, 'San Martín de Porres');  
insert into tb_distrito values (null, 1, 'San Miguel');     
insert into tb_distrito values (null, 1, 'Santa Anita');     
insert into tb_distrito values (null, 1, 'Santa María del Mar'); 
insert into tb_distrito values (null, 1, 'Santa Rosa');       
insert into tb_distrito values (null, 1, 'Santiago de Surco');  
insert into tb_distrito values (null, 1, 'Surquillo');       
insert into tb_distrito values (null, 1, 'Villa El Salvador');     
insert into tb_distrito values (null, 1, 'Villa María del Triunfo');            

insert into tb_login(usuario, pass, email_log) values ('madel2018', '12345678', 'madeliyricra@gmail.com');
insert into tb_login(usuario, pass, email_log) values ('calax590', '321654987', 'luizito590@gmail.com');
insert into tb_login(usuario, pass, email_log) values ('mknecroc12', '741852963', 'willymas123@gmail.com');
insert into tb_login(usuario, pass, email_log) values ('maver78', '987523641', 'maverick78@gmail.com');

insert into tb_cliente values (null,'70915220', 'Madeliy', 'Ricra Gutierrez', '2002-04-23', '987654321', '00001', 1);
insert into tb_cliente values (null,'72450000', 'Luis Fernando', 'Pérez Burga', '2000-09-05', '987654321', '00002', 1);
insert into tb_cliente values (null,'72397705', 'Willy Alberto', 'Melendez Gamarra', '2000-10-21', '987654321', '00003', 1);
insert into tb_cliente values (null,'71234568', 'Maverick', 'Champi Romero', '1999-05-07', '987654321', '00004', 1);
insert into tb_cliente values (null,'76428945', 'Juan', 'Rodriguez Suarez', '2002-04-23',  '987654321', null, 1);
insert into tb_cliente values (null,'73248756', 'Roberto', 'Fernandez Ramirez', '2002-04-23', '987654321', null, 1);
insert into tb_cliente values (null,'73200896', 'Alex', 'Quispe Cavero', '2002-04-23', '987654321', null, 1);

insert into tb_rol values (null, 'técnico infomático');
insert into tb_rol values (null, 'personal seguridad');
insert into tb_rol values (null, 'personal delivery');
insert into tb_rol values (null, 'cliente');
insert into tb_rol values (null, 'proveedor');*/
/*----------------------LAPTOPS-------------------------*/
/*insert into tb_registro values (null, 1, '00001', 'Es una laptop HP...', 'El equipo muestra ligero rapones en la pintura de la parte frontal, software y componentes en buen estado.', '2021-05-01', 1, 800.0, 'no imagen', 6.0, 1);
insert into tb_producto values (null, '632541-001', 1, 'HP', '15-dw1085la', 'Procesador: i3-10110U; RAM: 4GB DDR4; ROM: 256GB SSD; Pantalla: 15,6" FHD',
								'Equipo en buen estado, pintura refaccionada', '2021-05-07', 1, 1500.0, 'no imagen', 8.5, 1);
                                
insert into tb_registro values (null, 1, '00001', 'Es una laptop ...', 'El equipo se muestra sin sistema operativo, y daño en uno de los puertos USB', '2021-05-10', 1, 500.0,'no imagen', 4.5, 1);                      
insert into tb_producto values (null, '632541-002', 1, 'HP', '15-dw1085la', 'Procesador: i3-10110U; RAM: 4GB DDR4; ROM: 256GB SSD; Pantalla: 15,6" FHD',
								'Equipo en buen estado, sistema instalado y puerto usb reparado', '2021-05-15', 1, 1000.0, 'no imagen', 7.5, 1);
  
insert into tb_registro values (null, 1, '00001', 'Es una laptop ...', 'El equipo muestra placa base destruida, pantalla inservible y teclado con falta de teclas', '2021-05-10', 1, 200.0, 'no imagen', 2.7, 1);      
insert into tb_producto values (null, '632541-003', 1, 'HP', '15-dw1085la', 'Procesador: i3-10110U; RAM: 4GB DDR4; ROM: 256GB SSD; Pantalla: 15,6" FHD',
								 'Equipo en buen estado, completamente restaurado','2021-05-25', 1, 800.0, 'no imagen', 7.0, 1);
                                
insert into tb_registro values (null, 1, '00001', 'Es una laptop ...', 'El equipo muestra ligero rapones en la pintura de la parte frontal, software y componentes en buen estado.', '2021-05-10', 1, 2800.0, 'no imagen', 6.0, 1);                              
insert into tb_producto values (null, '732685-001', 1, 'Apple', 'Macbook Air 13', 'Procesador: M1; RAM: 8GB; ROM: 256GB; Pantalla: 13" FHD',
								'Equipo en buen estado, pintura refaccionada','2020-07-07', 1, 4000.0, 'no imagen', 8.5, 1);

insert into tb_registro values (null, 1, '00001', 'Es una laptop ...', 'El equipo se muestra sin sistema operativo, y daño en uno de los puertos USB', '2021-05-10', 1, 2000.0, 'no imagen', 4.5, 1);                                      
insert into tb_producto values (null, '732685-002', 1, 'Apple', 'Macbook Air 13', 'Procesador: M1; RAM: 8GB; ROM: 256GB; Pantalla: 13" FHD',
								 'Equipo en buen estado, sistema instalado y puerto usb reparado', '2020-07-15', 1,  2700.0, 'no imagen', 7.5, 1);

insert into tb_registro values (null, 1, '00001', 'Es una laptop ...', 'El equipo muestra placa base destruida, pantalla inservible y teclado con falta de teclas', '2021-05-10', 1,  1200.0, 'no imagen', 2.7, 1);                                      
insert into tb_producto values (null, '732685-003', 1, 'Apple', 'Macbook Air 13', 'Procesador: M1; RAM: 8GB; ROM: 256GB; Pantalla: 13" FHD',
								'Equipo en buen estado, completamente restaurado','2020-07-25', 1, 2000.0, 'no imagen', 7.0, 1);
 
insert into tb_registro values (null, 1, '00001', 'Es una laptop ...', 'El equipo muestra placa base destruida, pantalla inservible y teclado con falta de teclas', '2021-05-10', 1,  4000.0, 'no imagen', 2.7, 1);      
insert into tb_producto values (null, '852147-001', 1, 'ASUS', 'ROG Zephyrus G14', 'Procesador: Ryzen 9 4900HS; RAM: 16GB; ROM: 1TB SSD; Pantalla: 14" QHD',
								'Equipo en buen estado, completamente restaurado', '2020-11-07', 1, 7000.0, 'no imagen', 7.0, 1);
                                
insert into tb_registro values (null, 1, '00001', 'Es una laptop ...', 'El equipo muestra ligero rapones en la pintura de la parte frontal, software y componentes en buen estado.', '2021-05-10', 1, 3500.0, 'no imagen', 6.0, 1);      
insert into tb_producto values (null, '852147-002', 1, 'ASUS', 'ROG Zephyrus G14', 'Procesador: Ryzen 9 4900HS; RAM: 16GB; ROM: 1TB SSD; Pantalla: 14" QHD',
								'Equipo en buen estado, pintura refaccionada', '2021-11-13', 1, 6000.0, 'no imagen', 8.5, 1);
                                
insert into tb_registro values (null, 1, '00001', 'Es una laptop ...', 'El equipo se muestra sin sistema operativo, y daño en uno de los puertos USB', '2021-05-10', 1, 3000.0, 'no imagen', 4.5, 1);                                      
insert into tb_producto values (null, '852147-003', 1, 'ASUS', 'ROG Zephyrus G14', 'Procesador:Ryzen 9 4900HS; RAM: 16GB; ROM: 1TB SSD; Pantalla: 14" QHD',
								'Equipo en buen estado, sistema instalado y puerto usb reparado', '2021-11-25', 1, 5500.0, 'no imagen', 7.5, 1);
                                
insert into tb_registro values (null, 1, '00001', 'Es una laptop ...', 'El equipo se muestra sin placa base, pantalla inservible y teclado con falta de teclas', '2021-05-10', 1, 2500.0, 'no imagen', 2.0, 1);      						
insert into tb_producto values (null, '852147-004', 1, 'ASUS', 'ROG Zephyrus G14', 'Procesador: Ryzen 9 4900HS; RAM: 16GB; ROM: 1TB SSD; Pantalla: 14" QHD',
								'Equipo en buen estado, completamente restaurado', '2021-11-30', 1, 5000.0, 'no imagen', 2.0, 1);

*/
/*----------------------IMPRESORAS-------------------------*/
/*insert into tb_registro values (null, 1, '00001', 'Es una impresora ...', 'El equipo muestra ligeros raspones en el cuerpo y nivel de tinta al 50%', '2021-05-10', 1, 450.0, 'no imagen', 6.0, 1);      	
insert into tb_producto values (null, '524786-001', 2, 'HP', 'Multifuncional Ink Tank 415', 'Capacidad: 60 hojas; Wi-Fi: No; Bluetooth: No; NFC: No',
								'Equipo en buen estado, pintura refaccionada y tinta al 100%', '2020-07-07', 1, 700.0, 'no imagen', 8.5, 1);
                        
insert into tb_registro values (null, 1, '00001', 'Es una impresora ...', 'El equipo se muestra con daños en la bandeja y sin deposito de tinta', '2021-05-10', 1, 400.0, 'no imagen', 4.5, 1);      	                                
insert into tb_producto values (null, '524786-002', 2, 'HP', 'Multifuncional Ink Tank 415', 'Capacidad: 60 hojas; Wi-Fi: No; Bluetooth: No; NFC: No',
								'Equipo en buen estado, partes refaccionadas y tinta al 100%', '2020-07-15', 1, 650.0, 'no imagen', 7.5, 1);
                                
insert into tb_registro values (null, 1, '00001', 'Es una impresora ...', 'El equipo muestra sistema de impresion dañado, partes del cuerpo rotas y sin deposito de tinta', '2021-05-10', 1, 250.0, 'no imagen', 2.7, 1);                                    
insert into tb_producto values (null, '524786-003', 2, 'HP', 'Multifuncional Ink Tank 415', 'Capacidad: 60 hojas; Wi-Fi: No; Bluetooth: No; NFC: No',
								'Equipo en buen estado, completamente restaurado y tinta al 100%','2020-07-25', 1, 550.0, 'no imagen', 7.0, 1);
                                
insert into tb_registro values (null, 1, '00001', 'Es una impresora ...', 'El equipo muestra ligeros raspones en el cuerpo y nivel de tinta al 50%', '2021-05-10', 1, 300.0, 'no imagen', 6.0, 1);                                    
insert into tb_producto values (null, '374905-001', 2, 'CANON', 'Multifuncional Color G2110', 'Capacidad: 100 hojas; Wi-Fi: No; Bluetooth: No; NFC: No',
								'Equipo en buen estado, pintura refaccionada y tinta al 100%', '2020-07-07', 1, 500.0, 'no imagen', 8.5, 1);
                                
insert into tb_registro values (null, 1, '00001', 'Es una impresora ...', 'El equipo se muestra con daños en la bandeja y sin deposito de tinta', '2021-05-10', 1, 250.0, 'no imagen', 4.5, 1);                                    
insert into tb_producto values (null, '374905-002', 2, 'CANON', 'Multifuncional Color G2110', 'Capacidad: 100 hojas; Wi-Fi: No; Bluetooth: No; NFC: No',
								'Equipo en buen estado, partes refaccionadas y tinta al 100%', '2020-07-15', 1,  450.0, 'no imagen', 7.5, 1);
                                
insert into tb_registro values (null, 1, '00001', 'Es una impresora ...', 'El equipo muestra sistema de impresion dañado, partes del cuerpo rotas y sin deposito de tinta', '2021-05-10', 1, 150.0, 'no imagen', 2.7, 1);                                    
insert into tb_producto values (null, '374905-003', 2, 'CANON', 'Multifuncional Color G2110', 'Capacidad: 100 hojas; Wi-Fi: No; Bluetooth: No; NFC: No',
								'Equipo en buen estado, completamente restaurado y tinta al 100%', '2020-07-25', 1, 400.0, 'no imagen', 7.0, 1);
                                
insert into tb_registro values (null, 1, '00001', 'Es una impresora ...', 'El equipo muestra ligeros raspones en el cuerpo y nivel de tinta al 50%', '2021-05-10', 1, 700.0, 'no imagen', 6.0, 1);                                    
insert into tb_producto values (null, '842364-001', 2, 'Epson', 'Multifuncional Wifi EcoTank L4160', 'Capacidad: 100 hojas; Wi-Fi: Si; Bluetooth: No; NFC: No',
								'Equipo en buen estado, pintura refaccionada y tinta al 100%', '2020-07-07', 1, 1000.0, 'no imagen', 8.5, 1);
                                
insert into tb_registro values (null, 1, '00001', 'Es una impresora ...', 'El equipo se muestra con daños en la bandeja y sin deposito de tinta', '2021-05-10', 1, 650.0, 'no imagen', 4.5, 1);                                    
insert into tb_producto values (null, '842364-002', 2, 'Epson', 'Multifuncional Wifi EcoTank L4160', 'Capacidad: 100 hojas; Wi-Fi: Si; Bluetooth: No; NFC: No',
								'Equipo en buen estado, partes refaccionadas y tinta al 100%', '2020-07-15', 1, 850.0, 'no imagen', 7.5, 1);
                                
insert into tb_registro values (null, 1, '00001', 'Es una impresora ...', 'El equipo muestra sistema de impresion dañado, partes del cuerpo rotas y sin deposito de tinta', '2021-05-10', 1, 500.0, 'no imagen', 2.7, 1);                                    
insert into tb_producto values (null, '842364-003', 2, 'Epson', 'Multifuncional Wifi EcoTank L4160', 'Capacidad: 100 hojas; Wi-Fi: Si; Bluetooth: No; NFC: No',
								'Equipo en buen estado, completamente restaurado y tinta al 100%', '2020-07-25', 1, 700.0, 'no imagen', 7.0, 1);
                                
insert into tb_registro values (null, 1, '00001', 'Es una impresora ...', 'El equipo se muestra sin sistema de impresion, partes del cuerpo rotas y sin deposito de tinta', '2021-05-10', 1, 450.0, 'no imagen', 2.0, 1);    
insert into tb_producto values (null, '842364-004', 2, 'Epson', 'Multifuncional Wifi EcoTank L4160', 'Capacidad: 100 hojas; Wi-Fi: Si; Bluetooth: No; NFC: No',
								'Equipo en buen estado, completamente restaurado y tinta al 100%', '2020-07-25', 1, 650.0, 'no imagen', 7.0, 1);
              
     */         
/*----------------------SMARTPHONES-------------------------*/
/*insert into tb_registro values (null, 1, '00001', 'Es una celular ...', 'El equipo muestra ligeros raspones en el cuerpo', '2021-05-10', 1, 3500.0, 'no imagen', 6.0, 1);    
insert into tb_producto values (null, '125487-001', 3, 'Apple', 'iPhone 12 Blue', 'Pantalla: 6.1" FHD+; RAM: 4GB; ROM: 128GB; Procesador: A14 Bionic; Cámara posterior: 12MP; Cámara frontal: 12MP',
								'Equipo en buen estado, pintura refaccionada', '2020-07-07', 1, 4000.0, 'no imagen', 8.5, 1);
                                
insert into tb_registro values (null, 1, '00001', 'Es una celular ...', 'El equipo se muestra con parte posterior quebrada', '2021-05-10', 1, 3200.0, 'no imagen', 4.5, 1);                                    
insert into tb_producto values (null, '125487-002', 3, 'Apple', 'iPhone 12 Blue', 'Pantalla: 6.1" FHD+; RAM: 4GB; ROM: 128GB; Procesador: A14 Bionic; Cámara posterior: 12MP; Cámara frontal: 12MP',
								'Equipo en buen estado, vidrio reemplazado', '2020-07-15', 1, 3800.0, 'no imagen', 7.5, 1);
                                
insert into tb_registro values (null, 1, '00001', 'Es una celular ...', 'El equipo muestra pantalla quebrada, daño en el cuerpo y sistema bloqueado', '2021-05-10', 1, 3000.0, 'no imagen', 2.7, 1);                                    
insert into tb_producto values (null, '125487-003', 3, 'Apple', 'iPhone 12 Blue', 'Capacidad: 60 hojas; Wi-Fi: No; Bluetooth: No; NFC: No',
								'Equipo en buen estado, completamente restaurado', '2020-07-25', 1, 3500.0, 'no imagen', 7.0, 1);

insert into tb_registro values (null, 1, '00001', 'Es una celular ...', 'El equipo muestra ligeros raspones en el cuerpo', '2021-05-10', 1, 800.0, 'no imagen', 6.0, 1);                                    
insert into tb_producto values (null, '524861-001', 3, 'Xiaomi', 'Poco X3 NFC', 'Pantalla: 6.67" FHD+; RAM: 6GB; ROM: 128GB; Procesador: Qualcomm Snapdragon 732G; Cámara posterior: 64MP; Cámara frontal: 20MP',
								'Equipo en buen estado, pintura refaccionada', '2020-07-07', 1, 1000.0, 'no imagen', 8.5, 1);
                                
insert into tb_registro values (null, 1, '00001', 'Es una celular ...', 'El equipo se muestra con pantalla quebrada', '2021-05-10', 1, 600.0, 'no imagen', 4.5, 1);                                   
insert into tb_producto values (null, '524861-002', 3, 'Xiaomi', 'Poco X3 NFC', 'Pantalla: 6.67" FHD+; RAM: 6GB; ROM: 128GB; Procesador: Qualcomm Snapdragon 732G; Cámara posterior: 64MP; Cámara frontal: 20MP',
								'Equipo en buen estado, vidrio reemplazado', '2020-07-15', 1, 800.0, 'no imagen', 7.5, 1);
                                
insert into tb_registro values (null, 1, '00001', 'Es una celular ...', 'El equipo muestra pantalla quebrada, daño en el cuerpo y sistema bloqueado', '2021-05-10', 1, 450.0, 'no imagen', 2.7, 1);                                    
insert into tb_producto values (null, '524861-003', 3, 'Xiaomi', 'Poco X3 NFC', 'Pantalla: 6.67" FHD+; RAM: 6GB; ROM: 128GB; Procesador: Qualcomm Snapdragon 732G; Cámara posterior: 64MP; Cámara frontal: 20MP',
								'Equipo en buen estado, completamente restaurado', '2020-07-25', 1, 750.0, 'no imagen', 7.0, 1);
                                
 insert into tb_registro values (null, 1, '00001', 'Es una celular ...', 'El equipo muestra ligeros raspones en el cuerpo', '2021-05-10', 1, 1200.0, 'no imagen', 6.0, 1);                                   
insert into tb_producto values (null, '993254-001', 3, 'Samsung', 'Galaxy A71 Blanco', 'Pantalla: 6.7" FHD+; RAM: 6GB; ROM: 128GB; Procesador: Qualcomm Snapdragon 730; Cámara posterior: 40MP; Cámara frontal: 32MP',
								'Equipo en buen estado, pintura refaccionada', '2020-07-07', 1, 1400.0, 'no imagen', 8.5, 1);
                                
insert into tb_registro values (null, 1, '00001', 'Es una celular ...', 'El equipo se muestra con pantalla quebrada', '2021-05-10', 1, 1000.0, 'no imagen', 4.5, 1);                                    
insert into tb_producto values (null, '993254-002', 3, 'Samsung', 'Galaxy A71 Blanco', 'Pantalla: 6.7" FHD+; RAM: 6GB; ROM: 128GB; Procesador: Qualcomm Snapdragon 730; Cámara posterior: 40MP; Cámara frontal: 32MP',
								'Equipo en buen estado, vidrio reemplazado', '2020-07-15', 1, 1200.0, 'no imagen', 7.5, 1);
                                
insert into tb_registro values (null, 1, '00001', 'Es una celular ...', 'El equipo muestra pantalla quebrada, daño en el cuerpo y sistema bloqueado', '2021-05-10', 1, 600.0, 'no imagen', 2.7, 1);                                    
insert into tb_producto values (null, '993254-003', 3, 'Samsung', 'Galaxy A71 Blanco', 'Pantalla: 6.7" FHD+; RAM: 6GB; ROM: 128GB; Procesador: Qualcomm Snapdragon 730; Cámara posterior: 40MP; Cámara frontal: 32MP',
								'Equipo en buen estado, completamente restaurado', '2020-07-25', 1, 800.0, 'no imagen',  7.0, 1);
                                
insert into tb_registro values (null, 1, '00001', 'Es una celular ...', 'El equipo muestra pantalla inservivble, daño en la parte posterior y sistema bloqueado', '2021-05-10', 1, 550.0, 'no imagen', 2.0, 1);                                    
insert into tb_producto values (null, '993254-004', 3, 'Samsung', 'Galaxy A71 Blanco', 'Capacidad: 100 hojas; Wi-Fi: Si; Bluetooth: No; NFC: No',
								'Equipo en buen estado, completamente restaurado', '2020-07-25',  1, 750.0, 'no imagen', 7.0, 1);

*/

/*----------------------CÁMARAS-------------------------*/


/*----------------------WEAREABLES-------------------------*/


/*----------------------SMART TV'S-------------------------*/


/*----------------------AUDIO-------------------------*/


/*
select*from tb_boleta;
select*from tb_categoria;
select*from tb_cliente;
select*from tb_departamento;
select*from tb_detalle_boleta;
select*from distrito;
select*from tb_empleado;
select*from tb_login;
select*from tb_producto;
select*from tb_provincia;
select*from tb_registro;
select*from tb_rol;
*/