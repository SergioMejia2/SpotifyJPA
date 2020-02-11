package Entities;

import Entities.Cancion;
import Entities.CancionxplaylistPK;
import Entities.Playlist;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-12T15:19:19")
@StaticMetamodel(Cancionxplaylist.class)
public class Cancionxplaylist_ { 

    public static volatile SingularAttribute<Cancionxplaylist, Playlist> playlist;
    public static volatile SingularAttribute<Cancionxplaylist, BigInteger> orden;
    public static volatile SingularAttribute<Cancionxplaylist, CancionxplaylistPK> cancionxplaylistPK;
    public static volatile SingularAttribute<Cancionxplaylist, Cancion> cancion;

}