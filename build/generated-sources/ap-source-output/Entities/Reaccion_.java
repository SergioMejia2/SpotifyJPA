package Entities;

import Entities.Cancion;
import Entities.ReaccionPK;
import Entities.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-12T15:19:19")
@StaticMetamodel(Reaccion.class)
public class Reaccion_ { 

    public static volatile SingularAttribute<Reaccion, Date> fechaLike;
    public static volatile SingularAttribute<Reaccion, Usuario> usuario;
    public static volatile SingularAttribute<Reaccion, Cancion> cancion;
    public static volatile SingularAttribute<Reaccion, ReaccionPK> reaccionPK;

}