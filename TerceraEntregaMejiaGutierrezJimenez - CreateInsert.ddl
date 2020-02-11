-- Generado por Oracle SQL Developer Data Modeler 18.2.0.179.0806
--   en:        2018-10-16 13:04:21 COT
--   sitio:      Oracle Database 11g
--   tipo:      Oracle Database 11g

drop table Seguidores;
drop table cancionXinterprete;
drop table cancionXplaylist;
drop table registro;
drop table reaccion;
drop table playlist;
drop table interprete;
drop table cancion;
drop table genero;
drop table idioma;
drop table album;
drop table discografica;
drop table usuario;
drop table pais;
drop table familiar;
drop table individual;
drop table gratuita;
drop table suscripcion;
drop table auditoria;

drop sequence codigo_Cancion;
drop sequence codigo_Suscripcion;
drop sequence codigo_Pais;
drop sequence codigo_Discografica;
drop sequence codigo_Album;
drop sequence codigo_Interprete;
drop sequence codigo_Playlist;

CREATE TABLE auditoria(
    entidad NVARCHAR2(40),
    idEntidad NUMBER primary key,
    proceso NVARCHAR2(5),
    hora timestamp
);
    
CREATE TABLE album (
    cod_album           NUMBER NOT NULL,
    titulo_album        NVARCHAR2(40),
    fecha_lanzamiento   DATE,
    cod_discografica    NUMBER NOT NULL,
    isEP                CHAR(1)
);

COMMENT ON TABLE album IS
    'Tabla que contiene los álbumes y sus datos';

ALTER TABLE album ADD CONSTRAINT album_pk PRIMARY KEY ( cod_album );

CREATE TABLE cancion (
    cod_cancion      NUMBER NOT NULL,
    titulo_cancion   NVARCHAR2(40) NOT NULL,
    tiempo_reprM     NUMBER,
    tiempo_reprS     NUMBER,
    cod_album        NUMBER NOT NULL,
    cod_genero       NUMBER NOT NULL,
    cod_idioma       NUMBER NOT NULL,
    file_cancion     BLOB
);

COMMENT ON TABLE cancion IS
    'Tabla que contiene las canciones y sus datos';

ALTER TABLE cancion ADD CONSTRAINT cancion_pk PRIMARY KEY ( cod_cancion );

CREATE TABLE cancionxinterprete (
    cod_interprete   NUMBER NOT NULL,
    cod_cancion      NUMBER NOT NULL,
    rol              NVARCHAR2(10) NOT NULL
);

COMMENT ON TABLE cancionxinterprete IS
    'Tabla que contiene los intérpretes para cada canción';

ALTER TABLE cancionxinterprete ADD CONSTRAINT cancionxinterprete_pk PRIMARY KEY ( cod_interprete,
                                                                                  cod_cancion );

CREATE TABLE cancionxplaylist (
    cod_cancion    NUMBER NOT NULL,
    cod_playlist   NUMBER NOT NULL,
    orden          NUMBER NOT NULL
);

COMMENT ON TABLE cancionxplaylist IS
    'Tabla que contiene las canciones para cada playlist';

ALTER TABLE cancionxplaylist ADD CONSTRAINT cancionxplaylist_pk PRIMARY KEY ( cod_cancion,
                                                                              cod_playlist );

CREATE TABLE discografica (
    cod_discografica      NUMBER NOT NULL,
    nombre_discografica   NVARCHAR2(20)
);

COMMENT ON TABLE discografica IS
    'Tabla que contiene las discográficas y sus datos';

ALTER TABLE discografica ADD CONSTRAINT discografica_pk PRIMARY KEY ( cod_discografica );

CREATE TABLE familiar (
    cod_suscripcion   NUMBER NOT NULL,
    fecha_inicio      DATE,
    fecha_final       DATE,
    nombre            NVARCHAR2(30)
);

COMMENT ON TABLE familiar IS
    'Tabla que contiene la suscripciones familiares';

ALTER TABLE familiar ADD CONSTRAINT familiar_pk PRIMARY KEY ( cod_suscripcion );

CREATE TABLE genero (
    cod_genero      NUMBER NOT NULL,
    cod_idioma      NUMBER NOT NULL,
    nombre_genero   NVARCHAR2(10)
);

COMMENT ON TABLE genero IS
    'Tabla que contiene los géneros y sus datos';

ALTER TABLE genero ADD CONSTRAINT genero_pk PRIMARY KEY ( cod_genero,
                                                          cod_idioma );

CREATE TABLE gratuita (
    cod_suscripcion   NUMBER NOT NULL,
    nombre            NVARCHAR2(30)
);

COMMENT ON TABLE gratuita IS
    'Tabla que contiene la suscripciones gratuitas';

ALTER TABLE gratuita ADD CONSTRAINT gratuita_pk PRIMARY KEY ( cod_suscripcion );

CREATE TABLE idioma (
    cod_idioma NUMBER NOT NULL,
    idioma     NVARCHAR2(20)
);

COMMENT ON TABLE idioma IS
    'Tabla que contiene los idiomas';

ALTER TABLE idioma ADD CONSTRAINT idioma_pk PRIMARY KEY ( cod_idioma );


CREATE TABLE individual (
    cod_suscripcion   NUMBER NOT NULL,
    fecha_inicio      DATE,
    fecha_final       DATE,
    nombre            NVARCHAR2(30)
);

COMMENT ON TABLE individual IS
    'Tabla que contiene la suscripciones individuales';

ALTER TABLE individual ADD CONSTRAINT individual_pk PRIMARY KEY ( cod_suscripcion );

CREATE TABLE interprete (
    cod_interprete   NUMBER NOT NULL,
    nombre_artista   NVARCHAR2(20),
    nombre_real      NVARCHAR2(30),
    cod_pais         NUMBER NOT NULL
);

COMMENT ON TABLE interprete IS
    'Tabla que contiene los intérpretes y sus dato';

ALTER TABLE interprete ADD CONSTRAINT interprete_pk PRIMARY KEY ( cod_interprete );

CREATE TABLE pais (
    cod_pais      NUMBER NOT NULL,
    nombre_pais   NVARCHAR2(20)
);

COMMENT ON TABLE pais IS
    'Tabla que contiene los países y sus datos';

ALTER TABLE pais ADD CONSTRAINT pais_pk PRIMARY KEY ( cod_pais );

CREATE TABLE playlist (
    cod_playlist      NUMBER NOT NULL,
    nombre_playlist   NVARCHAR2(40),
    privacidad        NVARCHAR2(10),
    nickname          NVARCHAR2(15) NOT NULL
);

COMMENT ON TABLE playlist IS
    'Tabla que contiene las playlists y sus datos';

ALTER TABLE playlist ADD CONSTRAINT playlist_pk PRIMARY KEY ( cod_playlist );

CREATE TABLE reaccion (
    cod_cancion   NUMBER NOT NULL,
    nickname      NVARCHAR2(15) NOT NULL,
    fecha_like    TIMESTAMP
);

COMMENT ON TABLE reaccion IS
    'Tabla que contiene las reacciones y sus datos';

ALTER TABLE reaccion ADD CONSTRAINT reaccion_pk PRIMARY KEY ( cod_cancion,
                                                              nickname );

CREATE TABLE registro (
    fecha_repr    TIMESTAMP NOT NULL,
    fuesaltada    TIMESTAMP
--  WARNING: CHAR size not specified 
   ,
    cod_cancion   NUMBER NOT NULL,
    nickname      NVARCHAR2(15) NOT NULL
);

COMMENT ON TABLE registro IS
    'Tabla que contiene las registros y sus datos';

ALTER TABLE registro
    ADD CONSTRAINT registro_pk PRIMARY KEY ( cod_cancion,
                                             nickname,
                                             fecha_repr );

CREATE TABLE seguidores (
    seguidor   NVARCHAR2(15) NOT NULL,
    seguido    NVARCHAR2(15) NOT NULL
);

COMMENT ON TABLE seguidores IS
    'Tabla que contiene los seguidores y sus datos';

ALTER TABLE seguidores ADD CONSTRAINT seguidores_pk PRIMARY KEY ( seguidor,
                                                                  seguido );

CREATE TABLE suscripcion (
    cod_suscripcion   NUMBER NOT NULL
);

COMMENT ON TABLE suscripcion IS
    'Tabla que contiene los suscripción y sus datos';

ALTER TABLE suscripcion ADD CONSTRAINT suscripcion_pk PRIMARY KEY ( cod_suscripcion );

CREATE TABLE usuario (
    nickname          NVARCHAR2(15) NOT NULL,
    password          NVARCHAR2(20) NOT NULL,
    nombre            NVARCHAR2(15),
    apellido          NVARCHAR2(15),
    rol               NVARCHAR2(9),
    cod_pais          NUMBER NOT NULL,
    cod_suscripcion   NUMBER NOT NULL
);

COMMENT ON TABLE usuario IS
    'Tabla que contiene los usuarios y sus datos';

ALTER TABLE usuario ADD CONSTRAINT usuario_pk PRIMARY KEY ( nickname );

ALTER TABLE album
    ADD CONSTRAINT album_discografica_fk FOREIGN KEY ( cod_discografica )
        REFERENCES discografica ( cod_discografica );

ALTER TABLE cancion
    ADD CONSTRAINT cancion_album_fk FOREIGN KEY ( cod_album )
        REFERENCES album ( cod_album );

ALTER TABLE cancion
    ADD CONSTRAINT cancion_genero_fk FOREIGN KEY ( cod_genero,
                                                   cod_idioma )
        REFERENCES genero ( cod_genero,
                            cod_idioma );

ALTER TABLE cancionxinterprete
    ADD CONSTRAINT cancionxinter_interprete_fk FOREIGN KEY ( cod_interprete )
        REFERENCES interprete ( cod_interprete );

ALTER TABLE cancionxinterprete
    ADD CONSTRAINT cancionxinterprete_cancion_fk FOREIGN KEY ( cod_cancion )
        REFERENCES cancion ( cod_cancion );

ALTER TABLE cancionxplaylist
    ADD CONSTRAINT cancionxplaylist_cancion_fk FOREIGN KEY ( cod_cancion )
        REFERENCES cancion ( cod_cancion );

ALTER TABLE cancionxplaylist
    ADD CONSTRAINT cancionxplaylist_playlist_fk FOREIGN KEY ( cod_playlist )
        REFERENCES playlist ( cod_playlist );

ALTER TABLE familiar
    ADD CONSTRAINT familiar_suscripcion_fk FOREIGN KEY ( cod_suscripcion )
        REFERENCES suscripcion ( cod_suscripcion );
        
ALTER TABLE genero
    ADD CONSTRAINT genero_idioma_fk FOREIGN KEY ( cod_idioma )
        REFERENCES idioma ( cod_idioma );

ALTER TABLE gratuita
    ADD CONSTRAINT gratuita_suscripcion_fk FOREIGN KEY ( cod_suscripcion )
        REFERENCES suscripcion ( cod_suscripcion );

ALTER TABLE individual
    ADD CONSTRAINT individual_suscripcion_fk FOREIGN KEY ( cod_suscripcion )
        REFERENCES suscripcion ( cod_suscripcion );

ALTER TABLE interprete
    ADD CONSTRAINT interprete_pais_fk FOREIGN KEY ( cod_pais )
        REFERENCES pais ( cod_pais );

ALTER TABLE playlist
    ADD CONSTRAINT playlist_usuario_fk FOREIGN KEY ( nickname )
        REFERENCES usuario ( nickname );

ALTER TABLE reaccion
    ADD CONSTRAINT reaccion_cancion_fk FOREIGN KEY ( cod_cancion )
        REFERENCES cancion ( cod_cancion );

ALTER TABLE reaccion
    ADD CONSTRAINT reaccion_usuario_fk FOREIGN KEY ( nickname )
        REFERENCES usuario ( nickname );

ALTER TABLE registro
    ADD CONSTRAINT registro_cancion_fk FOREIGN KEY ( cod_cancion )
        REFERENCES cancion ( cod_cancion );

ALTER TABLE registro
    ADD CONSTRAINT registro_usuario_fk FOREIGN KEY ( nickname )
        REFERENCES usuario ( nickname );

ALTER TABLE seguidores
    ADD CONSTRAINT seguidores_usuario_fk FOREIGN KEY ( seguidor )
        REFERENCES usuario ( nickname );

ALTER TABLE seguidores
    ADD CONSTRAINT seguidores_usuario_fk2 FOREIGN KEY ( seguido )
        REFERENCES usuario ( nickname );

ALTER TABLE usuario
    ADD CONSTRAINT usuario_pais_fk FOREIGN KEY ( cod_pais )
        REFERENCES pais ( cod_pais );

ALTER TABLE usuario
    ADD CONSTRAINT usuario_suscripcion_fk FOREIGN KEY ( cod_suscripcion )
        REFERENCES suscripcion ( cod_suscripcion );

----------------SEQUENCES---------------------

create sequence codigo_Cancion
    start with 400
    increment by 1
    minvalue -1
    nomaxvalue;
    
create sequence codigo_Suscripcion
    start with 500
    increment by 1
    minvalue -1
    nomaxvalue;
    
create sequence codigo_Pais
    start with 900
    increment by 1
    minvalue -1
    nomaxvalue;
    
create sequence codigo_Discografica
    start with 800
    increment by 1
    minvalue -1
    nomaxvalue;
    
create sequence codigo_Album
    start with 300
    increment by 1
    minvalue -1
    nomaxvalue;
    
create sequence codigo_Interprete
    start with 100
    increment by 1
    minvalue -1
    nomaxvalue;
    
create sequence codigo_Playlist
    start with 200
    increment by 1
    minvalue -1
    nomaxvalue;
    

----------------INSERTS-----------------------

insert into suscripcion values(codigo_Suscripcion.nextVal);
insert into suscripcion values(codigo_Suscripcion.nextVal);
insert into suscripcion values(codigo_Suscripcion.nextVal);
insert into suscripcion values(codigo_Suscripcion.nextVal);
insert into suscripcion values(codigo_Suscripcion.nextVal);
insert into suscripcion values(codigo_Suscripcion.nextVal);
insert into suscripcion values(codigo_Suscripcion.nextVal);

insert into gratuita values(500,'Suscripcion Gratuita');
insert into gratuita values(501,'Suscripcion Gratuita');
insert into gratuita values(502,'Suscripcion Gratuita');

insert into individual values(503,to_date('12/05/2017','DD/MM/YYYY'),to_date('21/10/2018','DD/MM/YYYY'),'Suscripcion Individual');
insert into individual values(504,to_date('09/08/2017','DD/MM/YYYY'),to_date('09/09/2017','DD/MM/YYYY'),'Suscripcion Individual');

insert into familiar values(505, to_date('12/05/2018','DD/MM/YYYY'),to_date('12/05/2019','DD/MM/YYYY'), 'Suscripcion Familiar');
insert into familiar values(506, to_date('30/05/2015','DD/MM/YYYY'),to_date('10/03/2020','DD/MM/YYYY'), 'Suscripcion Familiar');

insert into pais values(codigo_Pais.nextVal, 'Colombia');
insert into pais values(codigo_Pais.nextVal, 'Perú');
insert into pais values(codigo_Pais.nextVal, 'Estados Unidos');
insert into pais values(codigo_Pais.nextVal, 'México');
insert into pais values(codigo_Pais.nextVal, 'Argentina');
insert into pais values(codigo_Pais.nextVal, 'Canadá');
insert into pais values(codigo_Pais.nextVal, 'Puerto Rico');

insert into usuario values('sebasgut1','lulu','Sebastian','Gutierrez','Principal',900,500);
insert into usuario values('samt210300','pepito','Sergio','Mejia','Principal',900,504);
insert into usuario values('la.jimenez','subapte','Laura','Jimenez','Principal',900,505);
insert into usuario values('moni99','juanmanuel','Monica','Alvarez','Principal',904,506);
insert into usuario values('aldedios','aynomames','Aldemar','Ramirez','Invitado',903,505);
insert into usuario values('hector.old','gutenmorgen','Hector','Rodriguez','Invitado',902,505);
insert into usuario values('balayiyo','depresion','Sebastian','Balaguera','Invitado',900,506);
insert into usuario values('pala1007','perico','Santiago','Palacios','Invitado',902,506);
insert into usuario values('julian1010','toche','Julian','Parada','Principal',902,503);
insert into usuario values('ortegon','nose','Daniel','Ortegon','Principal',900,501);
insert into usuario values('jajalaby','allahuakbar','David','Halaby','Principal',900,502);

insert into discografica values(codigo_Discografica.nextVal,'Caucho Records');
insert into discografica values(codigo_Discografica.nextVal,'SeSeLa Discs');
insert into discografica values(codigo_Discografica.nextVal,'Hector Old Grooves');

insert into album values(codigo_Album.nextVal, 'Witness', to_date('09/06/2017','dd/mm/yyyy'), 800,'N'); --KatyPerry
insert into album values(codigo_Album.nextVal, 'Sale el Sol', to_date('19/10/2010','dd/mm/yyyy'), 800,'N'); --Shakira
insert into album values(codigo_Album.nextVal, 'Paso a Paso',to_date('15/09/2012','dd/mm/yyyy'), 801,'S'); --Pasabordo
insert into album values(codigo_Album.nextVal, 'Purpose', to_date('13/11/2015','dd/mm/yyyy'), 800,'N'); --Justin
insert into album values(codigo_Album.nextVal, 'Mi Sangre', to_date('28/09/2004','dd/mm/yyyy'), 800,'S'); --Juanes
insert into album values(codigo_Album.nextVal, 'Limón y Sal', to_date('30/05/2006','dd/mm/yyyy'), 802,'S'); --Julieta
insert into album values(codigo_Album.nextVal, 'Views', to_date('29/04/2016','dd/mm/yyyy'), 801,'S'); --Drake
insert into album values(codigo_Album.nextVal, 'Ahí Vamos', to_date('04/04/2006','dd/mm/yyyy'), 802,'N'); --Cerati
insert into album values(codigo_Album.nextVal, 'Vamo a mangiare', to_date('05/11/2011','dd/mm/yyyy'), 800,'N');
insert into album values(codigo_Album.nextVal, 'Despacito', to_date('12/01/2017','dd/mm/yyyy'), 800,'S'); --Fonsi
insert into album values(codigo_Album.nextVal, 'Papito por Qué Me Dejaste..!', to_date('04/04/2004','dd/mm/yyyy'), 802,'S'); --Wendy
insert into album values(codigo_Album.nextVal, 'Swing in the future', to_date('12/11/1989','dd/mm/yyyy'), 802,'N');

insert into idioma values(1, 'Español');
insert into idioma values(2, 'English');
insert into idioma values(3, 'Italiano');
insert into idioma values(4, 'Latin');

insert into genero values(600,1,'Pop');
insert into genero values(600,2,'Pop');
insert into genero values(601,1,'Roca');
insert into genero values(601,2,'Rock');
insert into genero values(601,3,'Rocca');
insert into genero values(602,1,'Reggaeton');
insert into genero values(602,2,'Shit');
insert into genero values(603,1,'Rap');
insert into genero values(603,2,'Rap');
insert into genero values(604,1,'Hip Hop');
insert into genero values(605,1,'Latino');
insert into genero values(605,4,'Latinvm');
insert into genero values(605,2,'Mexicans');

insert into cancion values(codigo_Cancion.nextVal, 'Chained To The Rythm',3,15,300, 600, 2, null);--0
insert into cancion values(codigo_Cancion.nextVal, 'Swish Swish',  4,22 , 300, 600 , 2, null);
insert into cancion values(codigo_Cancion.nextVal, 'Loca', 3,45 , 301, 605,1, null);
insert into cancion values(codigo_Cancion.nextVal, 'Loca (Live)', 4,45 , 301, 605,1, null);
insert into cancion values(codigo_Cancion.nextVal, 'Quisiera', 3,0 , 302, 605,1, null);
insert into cancion values(codigo_Cancion.nextVal, 'What Do You Mean?', 3,10 , 303, 600,2, null);
insert into cancion values(codigo_Cancion.nextVal, 'La Camisa Negra', 3,15 , 303, 601,1, null);
insert into cancion values(codigo_Cancion.nextVal, 'Nada Valgo Sin Tu Amor', 3,20 , 303, 601,1, null);
insert into cancion values(codigo_Cancion.nextVal, 'La Camisa Negra (Live)', 4,15 , 303, 601,1, null);
insert into cancion values(codigo_Cancion.nextVal, 'Limón Y Sal', 3,07 , 305, 600,1, null);
insert into cancion values(codigo_Cancion.nextVal, 'One Dance', 2,59 , 306, 600,2, null);--10
insert into cancion values(codigo_Cancion.nextVal, 'Crimen', 3,30 , 307, 601,1, null);
insert into cancion values(codigo_Cancion.nextVal, 'Despacito', 3,14 , 309, 605,1, null);
insert into cancion values(codigo_Cancion.nextVal, 'Despacito ft. Justin Bieber', 3,29 ,309, 602, 2, null);
insert into cancion values(codigo_Cancion.nextVal, 'Cerveza',  5,12 , 310, 605, 1, null);
insert into cancion values(codigo_Cancion.nextVal, 'Loquito por ti',  3,32 , 311, 605,1, null);
insert into cancion values(codigo_Cancion.nextVal, 'Rude', 4,12 , 311, 601,2, null);
insert into cancion values(codigo_Cancion.nextVal, 'La Maldita Primavera',  2,45 , 311, 600,1, null);
insert into cancion values(codigo_Cancion.nextVal, 'La Gasolina', 2,53 , 311, 602,1, null);
insert into cancion values(codigo_Cancion.nextVal, 'Pasarela',  2,47 , 311, 602,2, null);
insert into cancion values(codigo_Cancion.nextVal, 'Kiki', 2,16 , 300, 603,2, null);--20
insert into cancion values(codigo_Cancion.nextVal, 'Doya lob mi', 3,45 , 301, 603,2, null);
insert into cancion values(codigo_Cancion.nextVal, 'Ursou', 4,26 , 302, 605,4, null);
insert into cancion values(codigo_Cancion.nextVal, 'Fakingu', 2,56 , 303, 603,2, null);
insert into cancion values(codigo_Cancion.nextVal, 'Precious', 3,06 , 304, 604,1, null);
insert into cancion values(codigo_Cancion.nextVal, 'When your smile', 3,34 , 305, 600,2, null);
insert into cancion values(codigo_Cancion.nextVal, 'Me escuchan', 3,00 , 306, 604,1, null);
insert into cancion values(codigo_Cancion.nextVal, 'Como te atreves', 4,27 , 307, 600,1, null);
insert into cancion values(codigo_Cancion.nextVal, 'Besos en Guerra', 4,21 , 310, 600,1, null);
insert into cancion values(codigo_Cancion.nextVal, 'Amiga', 4,15 , 309, 605,1, null);
insert into cancion values(codigo_Cancion.nextVal, 'Per dimenticare', 5,22 , 310, 601,3, null);--30
insert into cancion values(codigo_Cancion.nextVal, 'Baciami ancora', 3,09 , 311, 601,3, null);
insert into cancion values(codigo_Cancion.nextVal, 'Tra ne te', 4,38 , 310, 601,3, null);
insert into cancion values(codigo_Cancion.nextVal, 'Come mi pare', 3,06 , 309, 601,3, null);
insert into cancion values(codigo_Cancion.nextVal, 'Jvlivs Cesare', 3,10 , 307, 605,4, null);
insert into cancion values(codigo_Cancion.nextVal, 'Divano', 3,16 , 307, 601,3, null);
insert into cancion values(codigo_Cancion.nextVal, 'Pinshis Mexicans', 4,56 , 306, 605,2, null);
insert into cancion values(codigo_Cancion.nextVal, 'Gimme the power', 4,24 , 305, 605,2, null);--37

insert into interprete values(codigo_Interprete.nextVal, 'Drake', 'Aubrey Drake Graham',902);
insert into interprete values(codigo_Interprete.nextVal, 'Katy Perry', 'Katheryn Elizabeth Hudson',902); 
insert into interprete values(codigo_Interprete.nextVal, 'Juanes', 'Juan Esteban Aristizabal',900); 
insert into interprete values(codigo_Interprete.nextVal, 'Wendy Sulca', 'Wendy Sulca Quispe',901); 
insert into interprete values(codigo_Interprete.nextVal, null, 'Jhonatan Hernández',900); 
insert into interprete values(codigo_Interprete.nextVal, null, 'Juan Gabriel Rodriguez',900); 
insert into interprete values(codigo_Interprete.nextVal, null, 'Julieta Venegas',903);
insert into interprete values(codigo_Interprete.nextVal, 'Justin Bieber', 'Justin Drew Bieber',905);
insert into interprete values(codigo_Interprete.nextVal, 'Luis Fonsi', 'Luis Alfonso Rodríguez',906);
insert into interprete values(codigo_Interprete.nextVal, 'Shakira', 'Shakira Isabel Mebarak',900);
insert into interprete values(codigo_Interprete.nextVal, 'Gustavo Cerati', 'Gustavo Adrián Cerati',904);

insert into playlist values(codigo_Playlist.nextVal, 'Sergios Play','Publica','samt210300');
insert into playlist values(codigo_Playlist.nextVal, 'Caucho','Publica','sebasgut1');
insert into playlist values(codigo_Playlist.nextVal, 'Lala ra mamaa','Publica','la.jimenez');
insert into playlist values(codigo_Playlist.nextVal, 'Fisgon Tunes','Privada','moni99');
insert into playlist values(codigo_Playlist.nextVal, 'Monas Chinas','Privada','aldedios');
insert into playlist values(codigo_Playlist.nextVal, 'Oldy Groovies','Publica','hector.old');
insert into playlist values(codigo_Playlist.nextVal, 'Rolas Depresivas','Publica','balayiyo');
insert into playlist values(codigo_Playlist.nextVal, ':)','Publica','pala1007');
insert into playlist values(codigo_Playlist.nextVal, 'Toche y Queso','Publica','julian1010');
insert into playlist values(codigo_Playlist.nextVal, 'ReRandom','Publica','ortegon');
insert into playlist values(codigo_Playlist.nextVal, 'AllahuAkbar','Privada','jajalaby');
insert into playlist values(codigo_Playlist.nextVal, 'Mosofu Fest','Privada','sebasgut1');

insert into reaccion values(400,'samt210300',TO_TIMESTAMP('2018-10-25 22:35:45', 'YYYY-MM-DD HH24:MI:SS'));
insert into reaccion values(401,'sebasgut1',TO_TIMESTAMP('2017-10-25 21:35:45', 'YYYY-MM-DD HH24:MI:SS'));
insert into reaccion values(402,'sebasgut1',TO_TIMESTAMP('2017-10-25 22:35:44', 'YYYY-MM-DD HH24:MI:SS'));
insert into reaccion values(403,'samt210300',TO_TIMESTAMP('2017-10-25 22:35:55', 'YYYY-MM-DD HH24:MI:SS'));
insert into reaccion values(403,'hector.old',TO_TIMESTAMP('2018-10-25 20:35:45', 'YYYY-MM-DD HH24:MI:SS'));
insert into reaccion values(403,'la.jimenez',TO_TIMESTAMP('2018-10-25 19:35:45', 'YYYY-MM-DD HH24:MI:SS'));
insert into reaccion values(403,'pala1007',TO_TIMESTAMP('2018-10-25 18:35:45', 'YYYY-MM-DD HH24:MI:SS'));
insert into reaccion values(404,'balayiyo',TO_TIMESTAMP('2018-10-25 17:35:45', 'YYYY-MM-DD HH24:MI:SS'));
insert into reaccion values(404,'ortegon',TO_TIMESTAMP('2018-10-25 16:35:45', 'YYYY-MM-DD HH24:MI:SS'));
insert into reaccion values(405,'julian1010',TO_TIMESTAMP('2018-10-25 16:38:45', 'YYYY-MM-DD HH24:MI:SS'));
insert into reaccion values(406,'julian1010',TO_TIMESTAMP('2017-10-25 22:35:45', 'YYYY-MM-DD HH24:MI:SS'));
insert into reaccion values(407,'aldedios',TO_TIMESTAMP('2018-10-25 15:35:45', 'YYYY-MM-DD HH24:MI:SS'));
insert into reaccion values(408,'sebasgut1',TO_TIMESTAMP('2017-11-25 22:45:45', 'YYYY-MM-DD HH24:MI:SS'));
insert into reaccion values(408,'jajalaby',TO_TIMESTAMP('2018-10-25 14:35:45', 'YYYY-MM-DD HH24:MI:SS'));
insert into reaccion values(409,'sebasgut1',TO_TIMESTAMP('2017-09-25 22:35:45', 'YYYY-MM-DD HH24:MI:SS'));
insert into reaccion values(410,'samt210300',TO_TIMESTAMP('2017-08-26 22:35:45', 'YYYY-MM-DD HH24:MI:SS'));

insert into registro values(TO_TIMESTAMP('2018-10-25 22:35:45', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-10-25 22:36:45', 'YYYY-MM-DD HH24:MI:SS'), 400, 'samt210300');
insert into registro values(TO_TIMESTAMP('2018-10-25 11:36:45', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-10-25 11:37:45', 'YYYY-MM-DD HH24:MI:SS'), 401, 'hector.old');
insert into registro values(TO_TIMESTAMP('2018-10-26 10:37:45', 'YYYY-MM-DD HH24:MI:SS'), null, 402, 'sebasgut1');
insert into registro values(TO_TIMESTAMP('2018-10-27 13:38:45', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-10-27 13:39:45', 'YYYY-MM-DD HH24:MI:SS'), 404, 'pala1007');
insert into registro values(TO_TIMESTAMP('2018-10-28 14:39:45', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-10-28 14:40:45', 'YYYY-MM-DD HH24:MI:SS'), 403, 'la.jimenez');
insert into registro values(TO_TIMESTAMP('2018-10-28 15:30:45', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-10-28 15:31:45', 'YYYY-MM-DD HH24:MI:SS'), 406, 'ortegon');
insert into registro values(TO_TIMESTAMP('2018-10-29 16:31:45', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-10-29 16:32:45', 'YYYY-MM-DD HH24:MI:SS'), 408, 'samt210300');
insert into registro values(TO_TIMESTAMP('2018-10-30 17:32:45', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-10-30 17:34:45', 'YYYY-MM-DD HH24:MI:SS'), 409, 'julian1010');
insert into registro values(TO_TIMESTAMP('2018-10-31 12:33:45', 'YYYY-MM-DD HH24:MI:SS'), null, 411, 'samt210300');
insert into registro values(TO_TIMESTAMP('2018-10-02 18:34:45', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-10-02 18:35:45', 'YYYY-MM-DD HH24:MI:SS'), 400, 'pala1007');
insert into registro values(TO_TIMESTAMP('2018-10-03 19:12:45', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-10-03 19:13:45', 'YYYY-MM-DD HH24:MI:SS'), 409, 'balayiyo');
insert into registro values(TO_TIMESTAMP('2018-11-24 20:13:45', 'YYYY-MM-DD HH24:MI:SS'), null, 409, 'samt210300');
insert into registro values(TO_TIMESTAMP('2017-11-24 21:14:45', 'YYYY-MM-DD HH24:MI:SS'), null, 401, 'samt210300');
insert into registro values(TO_TIMESTAMP('2016-11-24 23:16:45', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2016-11-24 23:17:45', 'YYYY-MM-DD HH24:MI:SS'), 415, 'jajalaby');
insert into registro values(TO_TIMESTAMP('2015-11-24 00:18:45', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2015-11-24 00:19:45', 'YYYY-MM-DD HH24:MI:SS'), 414, 'pala1007');
insert into registro values(TO_TIMESTAMP('2017-11-12 09:19:45', 'YYYY-MM-DD HH24:MI:SS'), null, 406, 'aldedios');
insert into registro values(TO_TIMESTAMP('2018-09-21 08:20:45', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-09-21 08:21:45', 'YYYY-MM-DD HH24:MI:SS'), 417, 'balayiyo');
insert into registro values(TO_TIMESTAMP('2018-04-21 21:21:21', 'YYYY-MM-DD HH24:MI:SS'), null, 413, 'sebasgut1');

insert into registro values(TO_TIMESTAMP('2018-04-12 20:20:22', 'YYYY-MM-DD HH24:MI:SS'), null, 418, 'sebasgut1');
insert into registro values(TO_TIMESTAMP('2017-05-23 01:21:23', 'YYYY-MM-DD HH24:MI:SS'), null, 419, 'samt210300');
insert into registro values(TO_TIMESTAMP('2018-06-30 05:02:24', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-06-30 05:03:24', 'YYYY-MM-DD HH24:MI:SS'), 409, 'la.jimenez');
insert into registro values(TO_TIMESTAMP('2017-08-20 09:08:25', 'YYYY-MM-DD HH24:MI:SS'), null, 421, 'la.jimenez');
insert into registro values(TO_TIMESTAMP('2018-02-19 11:12:26', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-02-19 11:13:26', 'YYYY-MM-DD HH24:MI:SS'), 422, 'ortegon');
insert into registro values(TO_TIMESTAMP('2018-03-01 14:23:27', 'YYYY-MM-DD HH24:MI:SS'), null, 423, 'balayiyo');
insert into registro values(TO_TIMESTAMP('2018-11-18 16:34:28', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-11-18 16:35:28', 'YYYY-MM-DD HH24:MI:SS'), 424, 'samt210300');
insert into registro values(TO_TIMESTAMP('2018-10-19 19:25:29', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-10-19 19:26:26', 'YYYY-MM-DD HH24:MI:SS'), 425, 'sebasgut1');
insert into registro values(TO_TIMESTAMP('2018-10-19 20:05:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-10-19 20:06:00', 'YYYY-MM-DD HH24:MI:SS'), 426, 'sebasgut1');
insert into registro values(TO_TIMESTAMP('2018-09-16 20:59:01', 'YYYY-MM-DD HH24:MI:SS'), null, 409, 'la.jimenez');
insert into registro values(TO_TIMESTAMP('2017-08-14 19:31:02', 'YYYY-MM-DD HH24:MI:SS'), null, 409, 'pala1007');
insert into registro values(TO_TIMESTAMP('2017-04-18 23:25:03', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2017-04-18 23:26:03', 'YYYY-MM-DD HH24:MI:SS'), 427, 'samt210300');
insert into registro values(TO_TIMESTAMP('2018-12-12 00:04:04', 'YYYY-MM-DD HH24:MI:SS'), null, 402, 'samt210300');
insert into registro values(TO_TIMESTAMP('2018-10-13 13:00:59', 'YYYY-MM-DD HH24:MI:SS'), null, 404, 'sebasgut1');
insert into registro values(TO_TIMESTAMP('2017-10-13 13:00:59', 'YYYY-MM-DD HH24:MI:SS'), null, 405, 'sebasgut1');
insert into registro values(TO_TIMESTAMP('2017-11-12 13:00:59', 'YYYY-MM-DD HH24:MI:SS'), null, 407, 'sebasgut1');
insert into registro values(TO_TIMESTAMP('2018-11-13 13:00:59', 'YYYY-MM-DD HH24:MI:SS'), null, 409, 'sebasgut1');
insert into registro values(TO_TIMESTAMP('2018-11-13 13:00:59', 'YYYY-MM-DD HH24:MI:SS'), null, 410, 'sebasgut1');
insert into registro values(TO_TIMESTAMP('2017-03-21 13:00:59', 'YYYY-MM-DD HH24:MI:SS'), null, 412, 'sebasgut1');
insert into registro values(TO_TIMESTAMP('2018-10-19 19:30:28', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-10-19 19:32:28', 'YYYY-MM-DD HH24:MI:SS'), 414, 'sebasgut1');
insert into registro values(TO_TIMESTAMP('2018-10-19 19:13:45', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-10-19 19:14:45', 'YYYY-MM-DD HH24:MI:SS'), 416, 'sebasgut1');
insert into registro values(TO_TIMESTAMP('2018-10-19 20:00:59', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-10-19 20:02:59', 'YYYY-MM-DD HH24:MI:SS'), 431, 'sebasgut1');
insert into registro values(TO_TIMESTAMP('2017-11-25 13:00:59', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2017-11-25 13:01:59', 'YYYY-MM-DD HH24:MI:SS'), 432, 'samt210300');
insert into registro values(TO_TIMESTAMP('2018-06-13 13:00:59', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2018-06-13 13:02:29', 'YYYY-MM-DD HH24:MI:SS'), 433, 'la.jimenez');
insert into registro values(TO_TIMESTAMP('2018-07-26 13:00:59', 'YYYY-MM-DD HH24:MI:SS'), null, 434, 'la.jimenez');
insert into registro values(TO_TIMESTAMP('2018-10-19 19:45:56', 'YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-10-19 19:46:56', 'YYYY-MM-DD HH24:MI:SS') , 432, 'sebasgut1');

insert into cancionXplaylist values(400,200,1);
insert into cancionXplaylist values(402,200,2);
insert into cancionXplaylist values(403,200,3);
insert into cancionXplaylist values(404,201,1);
insert into cancionXplaylist values(405,201,3);
insert into cancionXplaylist values(406,201,2);
insert into cancionXplaylist values(407,202,1);
insert into cancionXplaylist values(408,202,3);
insert into cancionXplaylist values(409,202,2);
insert into cancionXplaylist values(410,203,3);
insert into cancionXplaylist values(411,203,2);
insert into cancionXplaylist values(412,203,1);
insert into cancionXplaylist values(413,204,1);
insert into cancionXplaylist values(414,204,2);
insert into cancionXplaylist values(415,204,3);
insert into cancionXplaylist values(416,205,3);
insert into cancionXplaylist values(417,205,1);
insert into cancionXplaylist values(400,205,2);
insert into cancionXplaylist values(401,206,1);
insert into cancionXplaylist values(402,206,2);
insert into cancionXplaylist values(403,206,3);
insert into cancionXplaylist values(404,207,2);
insert into cancionXplaylist values(405,207,3);
insert into cancionXplaylist values(406,207,1);
insert into cancionXplaylist values(407,208,1);
insert into cancionXplaylist values(408,208,2);
insert into cancionXplaylist values(409,208,3);
insert into cancionXplaylist values(410,209,2);
insert into cancionXplaylist values(411,209,3);
insert into cancionXplaylist values(412,209,1);
insert into cancionXplaylist values(413,210,2);
insert into cancionXplaylist values(414,210,1);
insert into cancionXplaylist values(415,211,1);

insert into cancionXinterprete values(100,414,'Principal');
insert into cancionXinterprete values(101,412,'Principal');
insert into cancionXinterprete values(102,413,'Principal');
insert into cancionXinterprete values(103,410,'Principal');
insert into cancionXinterprete values(104,401,'Principal');
insert into cancionXinterprete values(105,417,'Principal');
insert into cancionXinterprete values(106,400,'Principal');
insert into cancionXinterprete values(107,400,'Invitado');
insert into cancionXinterprete values(108,402,'Principal');
insert into cancionXinterprete values(109,403,'Principal');
insert into cancionXinterprete values(110,404,'Principal');
insert into cancionXinterprete values(101,405,'Principal');
insert into cancionXinterprete values(102,406,'Principal');
insert into cancionXinterprete values(103,407,'Principal');
insert into cancionXinterprete values(104,408,'Principal');
insert into cancionXinterprete values(105,409,'Principal');
insert into cancionXinterprete values(106,410,'Invitado');
insert into cancionXinterprete values(107,411,'Principal');
insert into cancionXinterprete values(108,415,'Principal');
insert into cancionXinterprete values(109,416,'Principal');
insert into cancionXinterprete values(110,417,'Invitado');
insert into cancionXinterprete values(101,400,'Invitado');
insert into cancionXinterprete values(102,400,'Invitado');

insert into cancionXinterprete values(102,420,'Principal');
insert into cancionXinterprete values(102,421,'Principal');
insert into cancionXinterprete values(102,422,'Principal');
insert into cancionXinterprete values(102,423,'Principal');
insert into cancionXinterprete values(102,424,'Principal');
insert into cancionXinterprete values(102,425,'Principal');
insert into cancionXinterprete values(102,426,'Principal');
insert into cancionXinterprete values(102,427,'Principal');
insert into cancionXinterprete values(102,428,'Principal');

insert into Seguidores values('samt210300','sebasgut1');
insert into Seguidores values('sebasgut1','samt210300');
insert into Seguidores values('sebasgut1','la.jimenez');
insert into Seguidores values('julian1010','samt210300');
insert into Seguidores values('julian1010','sebasgut1');
insert into Seguidores values('hector.old','sebasgut1');
insert into Seguidores values('hector.old','balayiyo');
insert into Seguidores values('hector.old','moni99');
insert into Seguidores values('hector.old','samt210300');
insert into Seguidores values('moni99','pala1007');
insert into Seguidores values('moni99','balayiyo');
insert into Seguidores values('moni99','jajalaby');
insert into Seguidores values('balayiyo','pala1007');
insert into Seguidores values('balayiyo','moni99');
insert into Seguidores values('aldedios','hector.old');
insert into Seguidores values('aldedios','samt210300');
insert into Seguidores values('la.jimenez','sebasgut1');
insert into Seguidores values('la.jimenez','samt210300');
insert into Seguidores values('la.jimenez','julian1010');
insert into Seguidores values('samt210300','moni99');
insert into Seguidores values('moni99','sebasgut1');
insert into Seguidores values('aldedios','sebasgut1');
insert into Seguidores values('balayiyo','sebasgut1');
insert into Seguidores values('pala1007','sebasgut1');
insert into Seguidores values('jajalaby','sebasgut1');
insert into Seguidores values('ortegon','sebasgut1');


create or replace TRIGGER CHANGEALBUM 
AFTER DELETE OR INSERT OR UPDATE OF cod_album, titulo_album, fecha_lanzamiento, isEP ON ALBUM 
for each row
declare
    tipo auditoria.proceso%type;
    tipoA NVARCHAR2(40);
    id auditoria.idEntidad%type;

BEGIN
if INSERTING then tipo:='I'; id:= :new.cod_album; end if;
if DELETING then tipo:='D'; id:= :old.cod_album; end if;
if UPDATING then tipo:='U'; id:= :old.cod_album; end if;

if INSERTING then
    if :new.isEp like 'S' then tipoA:='Ep'; end if;
    if :new.isEp like 'N' then tipoA:='Album'; end if;
else
    if :old.isEp like 'S' then tipoA:='Ep'; end if;
    if :old.isEp like 'N' then tipoA:='Album'; end if;
end if;


insert into auditoria values(tipoA,id,tipo,LOCALTIMESTAMP);
  
END;
/

create or replace TRIGGER CHANGECANCION 
AFTER DELETE OR INSERT OR UPDATE OF cod_cancion, titulo_cancion, tiempo_reprM, tiempo_reprS, cod_album, cod_genero, cod_idioma ON CANCION 
for each row
declare
    tipo auditoria.proceso%type;

    id auditoria.idEntidad%type;

BEGIN
if INSERTING then tipo:='I'; id:= :new.cod_cancion; end if;
if DELETING then tipo:='D'; id:= :old.cod_cancion; end if;
if UPDATING then tipo:='U'; id:= :old.cod_cancion; end if;

insert into auditoria values('Cancion',id,tipo,LOCALTIMESTAMP);
  
END;
/

create or replace TRIGGER CHANGEINTERPRETE 
AFTER DELETE OR INSERT OR UPDATE OF COD_INTERPRETE,COD_PAIS,NOMBRE_ARTISTA,NOMBRE_REAL ON INTERPRETE 
for each row
declare
    tipo auditoria.proceso%type;

    id auditoria.idEntidad%type;

BEGIN
if INSERTING then tipo:='I'; id:= :new.cod_interprete; end if;
if DELETING then tipo:='D'; id:= :old.cod_interprete; end if;
if UPDATING then tipo:='U'; id:= :old.cod_interprete; end if;

insert into auditoria values('Interprete',id,tipo,LOCALTIMESTAMP);
  
END;
/

commit;

-- Informe de Resumen de Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                            17
-- CREATE INDEX                             0
-- ALTER TABLE                             37
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   3
-- WARNINGS                                 1