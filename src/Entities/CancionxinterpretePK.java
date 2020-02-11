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
public class CancionxinterpretePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_INTERPRETE")
    private BigInteger codInterprete;
    @Basic(optional = false)
    @Column(name = "COD_CANCION")
    private BigInteger codCancion;

    public CancionxinterpretePK() {
    }

    public CancionxinterpretePK(BigInteger codInterprete, BigInteger codCancion) {
        this.codInterprete = codInterprete;
        this.codCancion = codCancion;
    }

    public BigInteger getCodInterprete() {
        return codInterprete;
    }

    public void setCodInterprete(BigInteger codInterprete) {
        this.codInterprete = codInterprete;
    }

    public BigInteger getCodCancion() {
        return codCancion;
    }

    public void setCodCancion(BigInteger codCancion) {
        this.codCancion = codCancion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codInterprete != null ? codInterprete.hashCode() : 0);
        hash += (codCancion != null ? codCancion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CancionxinterpretePK)) {
            return false;
        }
        CancionxinterpretePK other = (CancionxinterpretePK) object;
        if ((this.codInterprete == null && other.codInterprete != null) || (this.codInterprete != null && !this.codInterprete.equals(other.codInterprete))) {
            return false;
        }
        if ((this.codCancion == null && other.codCancion != null) || (this.codCancion != null && !this.codCancion.equals(other.codCancion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.CancionxinterpretePK[ codInterprete=" + codInterprete + ", codCancion=" + codCancion + " ]";
    }
    
}
