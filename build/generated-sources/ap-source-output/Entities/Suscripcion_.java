package Entities;

import Entities.Familiar;
import Entities.Gratuita;
import Entities.Individual;
import Entities.Usuario;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-12T15:19:19")
@StaticMetamodel(Suscripcion.class)
public class Suscripcion_ { 

    public static volatile SingularAttribute<Suscripcion, Gratuita> gratuita;
    public static volatile ListAttribute<Suscripcion, Usuario> usuarioList;
    public static volatile SingularAttribute<Suscripcion, Individual> individual;
    public static volatile SingularAttribute<Suscripcion, BigInteger> codSuscripcion;
    public static volatile SingularAttribute<Suscripcion, Familiar> familiar;

}