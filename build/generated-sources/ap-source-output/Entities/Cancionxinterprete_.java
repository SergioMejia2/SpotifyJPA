package Entities;

import Entities.Cancion;
import Entities.CancionxinterpretePK;
import Entities.Interprete;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-12T15:19:19")
@StaticMetamodel(Cancionxinterprete.class)
public class Cancionxinterprete_ { 

    public static volatile SingularAttribute<Cancionxinterprete, Interprete> interprete;
    public static volatile SingularAttribute<Cancionxinterprete, CancionxinterpretePK> cancionxinterpretePK;
    public static volatile SingularAttribute<Cancionxinterprete, String> rol;
    public static volatile SingularAttribute<Cancionxinterprete, Cancion> cancion;

}