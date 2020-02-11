/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sala BD
 */
@Entity
@Table(name = "IDIOMA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Idioma.findAll", query = "SELECT i FROM Idioma i")
    , @NamedQuery(name = "Idioma.findByCodIdioma", query = "SELECT i FROM Idioma i WHERE i.codIdioma = :codIdioma")
    , @NamedQuery(name = "Idioma.findByIdioma", query = "SELECT i FROM Idioma i WHERE i.idioma = :idioma")})
public class Idioma implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "COD_IDIOMA")
    private BigDecimal codIdioma;
    @Basic(optional = false)
    @Column(name = "IDIOMA")
    private String idioma;

    public Idioma() {
    }

    public Idioma(BigDecimal codIdioma) {
        this.codIdioma = codIdioma;
    }

    public Idioma(BigDecimal codIdioma, String idioma) {
        this.codIdioma = codIdioma;
        this.idioma = idioma;
    }

    public BigDecimal getCodIdioma() {
        return codIdioma;
    }

    public void setCodIdioma(BigDecimal codIdioma) {
        this.codIdioma = codIdioma;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codIdioma != null ? codIdioma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Idioma)) {
            return false;
        }
        Idioma other = (Idioma) object;
        if ((this.codIdioma == null && other.codIdioma != null) || (this.codIdioma != null && !this.codIdioma.equals(other.codIdioma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Idioma[ codIdioma=" + codIdioma + " ]";
    }
    
}
