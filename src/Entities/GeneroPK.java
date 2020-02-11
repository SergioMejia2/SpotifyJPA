/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Sala BD
 */
@Embeddable
public class GeneroPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_GENERO")
    private BigInteger codGenero;
    @Basic(optional = false)
    @Column(name = "COD_IDIOMA")
    private BigInteger codIdioma;

    public GeneroPK() {
    }

    public GeneroPK(BigInteger codGenero, BigInteger codIdioma) {
        this.codGenero = codGenero;
        this.codIdioma = codIdioma;
    }

    public BigInteger getCodGenero() {
        return codGenero;
    }

    public void setCodGenero(BigInteger codGenero) {
        this.codGenero = codGenero;
    }

    public BigInteger getCodIdioma() {
        return codIdioma;
    }

    public void setCodIdioma(BigInteger codIdioma) {
        this.codIdioma = codIdioma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codGenero != null ? codGenero.hashCode() : 0);
        hash += (codIdioma != null ? codIdioma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeneroPK)) {
            return false;
        }
        GeneroPK other = (GeneroPK) object;
        if ((this.codGenero == null && other.codGenero != null) || (this.codGenero != null && !this.codGenero.equals(other.codGenero))) {
            return false;
        }
        if ((this.codIdioma == null && other.codIdioma != null) || (this.codIdioma != null && !this.codIdioma.equals(other.codIdioma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.GeneroPK[ codGenero=" + codGenero + ", codIdioma=" + codIdioma + " ]";
    }
    
}
