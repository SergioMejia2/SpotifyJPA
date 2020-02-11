package Entities;

import Entities.Cancion;
import Entities.GeneroPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-12T15:19:19")
@StaticMetamodel(Genero.class)
public class Genero_ { 

    public static volatile SingularAttribute<Genero, GeneroPK> generoPK;
    public static volatile SingularAttribute<Genero, String> nombreGenero;
    public static volatile ListAttribute<Genero, Cancion> cancionList;

}