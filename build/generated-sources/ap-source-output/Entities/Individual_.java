package Entities;

import Entities.Suscripcion;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-12T15:19:19")
@StaticMetamodel(Individual.class)
public class Individual_ { 

    public static volatile SingularAttribute<Individual, Date> fechaInicio;
    public static volatile SingularAttribute<Individual, Suscripcion> suscripcion;
    public static volatile SingularAttribute<Individual, Date> fechaFinal;
    public static volatile SingularAttribute<Individual, BigInteger> codSuscripcion;
    public static volatile SingularAttribute<Individual, String> nombre;

}