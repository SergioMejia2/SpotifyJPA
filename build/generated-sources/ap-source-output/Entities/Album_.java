package Entities;

import Entities.Cancion;
import Entities.Discografica;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-12T15:19:19")
@StaticMetamodel(Album.class)
public class Album_ { 

    public static volatile SingularAttribute<Album, Date> fechaLanzamiento;
    public static volatile SingularAttribute<Album, BigInteger> codAlbum;
    public static volatile SingularAttribute<Album, Character> isep;
    public static volatile ListAttribute<Album, Cancion> cancionList;
    public static volatile SingularAttribute<Album, String> tituloAlbum;
    public static volatile SingularAttribute<Album, Discografica> codDiscografica;

}