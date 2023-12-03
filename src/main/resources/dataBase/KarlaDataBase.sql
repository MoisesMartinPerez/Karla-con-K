DROP DATABASE IF EXISTS Karla;
CREATE DATABASE Karla;
USE Karla;


CREATE TABLE usuario (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre_usuario VARCHAR(50) NOT NULL,
    clave VARCHAR(20) NOT NULL,
    imagen_usuario BLOB
);


/*metemos favoritos como boolean*/
CREATE TABLE canciones (
    id_cancion INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    artista VARCHAR(100) NOT NULL,
    duracion TIME NOT NULL,
    favorito boolean, 
    genero varchar(100),
    año int(4),
    audio_cancion blob, 
    imagen_cancion varchar(100)
);

insert into canciones values ('01', 'Americano Remix', 'Dj Moisexxx ft. MC Santa María', '0:59', true, 'Clásica', '1864', 
	"src/main/resources/canciones/Americano x Heads Will Roll Dance till you're dead DJP Mashup TikTok Bass Boosted.mp3", 
	'src/main/resources/cancionesImg/papaAmericano.JPG');
insert into canciones values ('02', 'Candy', 'Plan B', '3:27', true, 'Reggaetón', '2014', 
	'src/main/resources/canciones/Plan B - Candy [Official Audio]_k8nxe6UE1gY.mp3', 
	'src/main/resources/cancionesImg/candy.jpg');
insert into canciones values ('03', 'El Efecto', 'Rauw Alejandro ft. Chencho Corleone', '3:26', true, 'Reggaetón', '2019', 
	'src/main/resources/canciones/Rauw Alejandro - El Efecto (Letra) ft. Chencho Corleone.mp3', 
	'src/main/resources/cancionesImg/el efecto.JPG');
insert into canciones values ('04', 'Punto 40', 'Rauw Alejandro ft. Baby Rasta', '3:11', false, 'Trap', '2022', 
	'src/main/resources/canciones/Rauw Alejandro  x Baby Rasta -  PUNTO 40 (Letra_Lyrics)_kvF8dvj8jsA.mp3', 
	'src/main/resources/cancionesImg/punto40.JPG');
insert into canciones values ('05', 'Está que Quema', 'YSY A ft. Sixto Yegros', '3:09', false, 'Trap', '2023', 
	'src/main/resources/canciones/YSY A FT. SIXTO YEGROS - ESTA QUE QUEMA (Letra_Lyrics) [PROD. BAXIAN]_40YL-83RuF4.mp3', 
	'src/main/resources/music_img/estaquequema.JPG');
    
select * from canciones;

CREATE TABLE favoritos (
    id_favorito INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    id_cancion INT,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_cancion) REFERENCES canciones(id_cancion)
);



CREATE TABLE historial_reproduccion (
    id_historial INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    id_cancion INT,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_cancion) REFERENCES canciones(id_cancion)
);

CREATE TABLE listas_reproduccion (
    id_lista INT PRIMARY KEY AUTO_INCREMENT,
    nombre_lista VARCHAR(100) NOT NULL,
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE canciones_lista (
	id_lista INT,
    id_cancion INT,
    PRIMARY KEY (id_lista, id_cancion),
    FOREIGN KEY (id_lista) REFERENCES listas_reproduccion(id_lista),
    FOREIGN KEY (id_cancion) REFERENCES canciones(id_cancion)
);



