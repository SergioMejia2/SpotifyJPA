package Entities;

import Entities.Album;
import Entities.Cancionxinterprete;
import Entities.Cancionxplaylist;
import Entities.Genero;
import Entities.Reaccion;
import Entities.Registro;
import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-12T15:19:19")
@StaticMetamodel(Cancion.class)
public class Cancion_ { 

    public static volatile SingularAttribute<Cancion, BigInteger> codCancion;
    public static volatile SingularAttribute<Cancion, Serializable> fileCancion;
    public static volatile SingularAttribute<Cancion, Album> codAlbum;
    public static volatile SingularAttribute<Cancion, BigInteger> tiempoReprS;
    public static volatile ListAttribute<Cancion, Registro> registroList;
    public static volatile ListAttribute<Cancion, Cancionxplaylist> cancionxplaylistList;
    public static volatile SingularAttribute<Cancion, Genero> genero;
    public static volatile ListAttribute<Cancion, Reaccion> reaccionList;
    public static volatile SingularAttribute<Cancion, BigInteger> tiempoReprM;
    public static volatile ListAttribute<Cancion, Cancionxinterprete> cancionxinterpreteList;
    public static volatile SingularAttribute<Cancion, String> tituloCancion;

}