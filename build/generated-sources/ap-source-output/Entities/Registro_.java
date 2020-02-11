package Entities;

import Entities.Cancion;
import Entities.RegistroPK;
import Entities.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-12T15:19:19")
@StaticMetamodel(Registro.class)
public class Registro_ { 

    public static volatile SingularAttribute<Registro, Date> fuesaltada;
    public static volatile SingularAttribute<Registro, Usuario> usuario;
    public static volatile SingularAttribute<Registro, RegistroPK> registroPK;
    public static volatile SingularAttribute<Registro, Cancion> cancion;

}