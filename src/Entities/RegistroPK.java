/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Sala BD
 */
@Embeddable
public class RegistroPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "FECHA_REPR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRepr;
    @Basic(optional = false)
    @Column(name = "COD_CANCION")
    private BigInteger codCancion;
    @Basic(optional = false)
    @Column(name = "NICKNAME")
    private String nickname;

    public RegistroPK() {
    }

    public RegistroPK(Date fechaRepr, BigInteger codCancion, String nickname) {
        this.fechaRepr = fechaRepr;
        this.codCancion = codCancion;
        this.nickname = nickname;
    }

    public Date getFechaRepr() {
        return fechaRepr;
    }

    public void setFechaRepr(Date fechaRepr) {
        this.fechaRepr = fechaRepr;
    }

    public BigInteger getCodCancion() {
        return codCancion;
    }

    public void setCodCancion(BigInteger codCancion) {
        this.codCancion = codCancion;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fechaRepr != null ? fechaRepr.hashCode() : 0);
        hash += (codCancion != null ? codCancion.hashCode() : 0);
        hash += (nickname != null ? nickname.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroPK)) {
            return false;
        }
        RegistroPK other = (RegistroPK) object;
        if ((this.fechaRepr == null && other.fechaRepr != null) || (this.fechaRepr != null && !this.fechaRepr.equals(other.fechaRepr))) {
            return false;
        }
        if ((this.codCancion == null && other.codCancion != null) || (this.codCancion != null && !this.codCancion.equals(other.codCancion))) {
            return false;
        }
        if ((this.nickname == null && other.nickname != null) || (this.nickname != null && !this.nickname.equals(other.nickname))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.RegistroPK[ fechaRepr=" + fechaRepr + ", codCancion=" + codCancion + ", nickname=" + nickname + " ]";
    }
    
}
