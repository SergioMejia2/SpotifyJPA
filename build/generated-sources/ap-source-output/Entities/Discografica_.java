package Entities;

import Entities.Album;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-12T15:19:19")
@StaticMetamodel(Discografica.class)
public class Discografica_ { 

    public static volatile ListAttribute<Discografica, Album> albumList;
    public static volatile SingularAttribute<Discografica, BigDecimal> codDiscografica;
    public static volatile SingularAttribute<Discografica, String> nombreDiscografica;

}