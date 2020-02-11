/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sala BD
 */
@Entity
@Table(name = "GENERO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Genero.findAll", query = "SELECT g FROM Genero g")
    , @NamedQuery(name = "Genero.findByCodGenero", query = "SELECT g FROM Genero g WHERE g.generoPK.codGenero = :codGenero")
    , @NamedQuery(name = "Genero.findByCodIdioma", query = "SELECT g FROM Genero g WHERE g.generoPK.codIdioma = :codIdioma")
    , @NamedQuery(name = "Genero.findByNombreGenero", query = "SELECT g FROM Genero g WHERE g.nombreGenero = :nombreGenero")})
public class Genero implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GeneroPK generoPK;
    @Column(name = "NOMBRE_GENERO")
    private String nombreGenero;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "genero")
    private List<Cancion> cancionList;

    public Genero() {
    }

    public Genero(GeneroPK generoPK) {
        this.generoPK = generoPK;
    }

    public Genero(BigInteger codGenero, BigInteger codIdioma) {
        this.generoPK = new GeneroPK(codGenero, codIdioma);
    }

    public GeneroPK getGeneroPK() {
        return generoPK;
    }

    public void setGeneroPK(GeneroPK generoPK) {
        this.generoPK = generoPK;
    }

    public String getNombreGenero() {
        return nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }

    @XmlTransient
    public List<Cancion> getCancionList() {
        return cancionList;
    }

    public void setCancionList(List<Cancion> cancionList) {
        this.cancionList = cancionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (generoPK != null ? generoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Genero)) {
            return false;
        }
        Genero other = (Genero) object;
        if ((this.generoPK == null && other.generoPK != null) || (this.generoPK != null && !this.generoPK.equals(other.generoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreGenero;
    }
    
}
