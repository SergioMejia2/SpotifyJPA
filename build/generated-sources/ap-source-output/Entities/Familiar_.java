package Entities;

import Entities.Suscripcion;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-12T15:19:19")
@StaticMetamodel(Familiar.class)
public class Familiar_ { 

    public static volatile SingularAttribute<Familiar, Date> fechaInicio;
    public static volatile SingularAttribute<Familiar, Suscripcion> suscripcion;
    public static volatile SingularAttribute<Familiar, Date> fechaFinal;
    public static volatile SingularAttribute<Familiar, BigInteger> codSuscripcion;
    public static volatile SingularAttribute<Familiar, String> nombre;

}