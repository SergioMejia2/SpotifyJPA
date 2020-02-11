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
public class ReaccionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_CANCION")
    private BigInteger codCancion;
    @Basic(optional = false)
    @Column(name = "NICKNAME")
    private String nickname;

    public ReaccionPK() {
    }

    public ReaccionPK(BigInteger codCancion, String nickname) {
        this.codCancion = codCancion;
        this.nickname = nickname;
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
        hash += (codCancion != null ? codCancion.hashCode() : 0);
        hash += (nickname != null ? nickname.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReaccionPK)) {
            return false;
        }
        ReaccionPK other = (ReaccionPK) object;
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
        return "Entities.ReaccionPK[ codCancion=" + codCancion + ", nickname=" + nickname + " ]";
    }
    
}
