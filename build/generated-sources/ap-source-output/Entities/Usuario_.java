package Entities;

import Entities.Pais;
import Entities.Playlist;
import Entities.Reaccion;
import Entities.Registro;
import Entities.Suscripcion;
import Entities.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-12T15:19:19")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile ListAttribute<Usuario, Playlist> playlistList;
    public static volatile SingularAttribute<Usuario, String> password;
    public static volatile ListAttribute<Usuario, Usuario> usuarioList;
    public static volatile ListAttribute<Usuario, Registro> registroList;
    public static volatile ListAttribute<Usuario, Reaccion> reaccionList;
    public static volatile SingularAttribute<Usuario, String> apellido;
    public static volatile SingularAttribute<Usuario, String> nickname;
    public static volatile SingularAttribute<Usuario, Pais> codPais;
    public static volatile SingularAttribute<Usuario, Suscripcion> codSuscripcion;
    public static volatile SingularAttribute<Usuario, String> nombre;
    public static volatile SingularAttribute<Usuario, String> rol;
    public static volatile ListAttribute<Usuario, Usuario> usuarioList1;

}