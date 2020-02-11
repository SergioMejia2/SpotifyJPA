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
public class CancionxplaylistPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "COD_CANCION")
    private BigInteger codCancion;
    @Basic(optional = false)
    @Column(name = "COD_PLAYLIST")
    private BigInteger codPlaylist;

    public CancionxplaylistPK() {
    }

    public CancionxplaylistPK(BigInteger codCancion, BigInteger codPlaylist) {
        this.codCancion = codCancion;
        this.codPlaylist = codPlaylist;
    }

    public BigInteger getCodCancion() {
        return codCancion;
    }

    public void setCodCancion(BigInteger codCancion) {
        this.codCancion = codCancion;
    }

    public BigInteger getCodPlaylist() {
        return codPlaylist;
    }

    public void setCodPlaylist(BigInteger codPlaylist) {
        this.codPlaylist = codPlaylist;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCancion != null ? codCancion.hashCode() : 0);
        hash += (codPlaylist != null ? codPlaylist.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CancionxplaylistPK)) {
            return false;
        }
        CancionxplaylistPK other = (CancionxplaylistPK) object;
        if ((this.codCancion == null && other.codCancion != null) || (this.codCancion != null && !this.codCancion.equals(other.codCancion))) {
            return false;
        }
        if ((this.codPlaylist == null && other.codPlaylist != null) || (this.codPlaylist != null && !this.codPlaylist.equals(other.codPlaylist))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.CancionxplaylistPK[ codCancion=" + codCancion + ", codPlaylist=" + codPlaylist + " ]";
    }
    
}
