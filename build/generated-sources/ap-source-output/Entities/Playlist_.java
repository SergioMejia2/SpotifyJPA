package Entities;

import Entities.Cancionxplaylist;
import Entities.Usuario;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-12T15:19:19")
@StaticMetamodel(Playlist.class)
public class Playlist_ { 

    public static volatile ListAttribute<Playlist, Cancionxplaylist> cancionxplaylistList;
    public static volatile SingularAttribute<Playlist, Usuario> nickname;
    public static volatile SingularAttribute<Playlist, String> nombrePlaylist;
    public static volatile SingularAttribute<Playlist, BigInteger> codPlaylist;
    public static volatile SingularAttribute<Playlist, String> privacidad;

}