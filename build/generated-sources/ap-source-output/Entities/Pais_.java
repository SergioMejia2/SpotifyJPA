package Entities;

import Entities.Interprete;
import Entities.Usuario;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-12T15:19:19")
@StaticMetamodel(Pais.class)
public class Pais_ { 

    public static volatile ListAttribute<Pais, Usuario> usuarioList;
    public static volatile ListAttribute<Pais, Interprete> interpreteList;
    public static volatile SingularAttribute<Pais, BigDecimal> codPais;
    public static volatile SingularAttribute<Pais, String> nombrePais;

}