package Entities;

import Entities.Cancionxinterprete;
import Entities.Pais;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-12T15:19:19")
@StaticMetamodel(Interprete.class)
public class Interprete_ { 

    public static volatile SingularAttribute<Interprete, String> nombreArtista;
    public static volatile SingularAttribute<Interprete, BigInteger> codInterprete;
    public static volatile SingularAttribute<Interprete, Pais> codPais;
    public static volatile SingularAttribute<Interprete, String> nombreReal;
    public static volatile ListAttribute<Interprete, Cancionxinterprete> cancionxinterpreteList;

}