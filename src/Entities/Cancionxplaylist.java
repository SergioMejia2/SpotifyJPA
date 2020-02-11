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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sala BD
 */
@Entity
@Table(name = "CANCIONXPLAYLIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cancionxplaylist.findAll", query = "SELECT c FROM Cancionxplaylist c")
    , @NamedQuery(name = "Cancionxplaylist.findByCodCancion", query = "SELECT c FROM Cancionxplaylist c WHERE c.cancionxplaylistPK.codCancion = :codCancion")
    , @NamedQuery(name = "Cancionxplaylist.findByCodPlaylist", query = "SELECT c FROM Cancionxplaylist c WHERE c.cancionxplaylistPK.codPlaylist = :codPlaylist")
    , @NamedQuery(name = "Cancionxplaylist.findByOrden", query = "SELECT c FROM Cancionxplaylist c WHERE c.orden = :orden")})
public class Cancionxplaylist implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CancionxplaylistPK cancionxplaylistPK;
    @Basic(optional = false)
    @Column(name = "ORDEN")
    private BigInteger orden;
    @JoinColumn(name = "COD_CANCION", referencedColumnName = "COD_CANCION", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cancion cancion;
    @JoinColumn(name = "COD_PLAYLIST", referencedColumnName = "COD_PLAYLIST", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Playlist playlist;

    public Cancionxplaylist() {
    }

    public Cancionxplaylist(CancionxplaylistPK cancionxplaylistPK) {
        this.cancionxplaylistPK = cancionxplaylistPK;
    }

    public Cancionxplaylist(CancionxplaylistPK cancionxplaylistPK, BigInteger orden) {
        this.cancionxplaylistPK = cancionxplaylistPK;
        this.orden = orden;
    }

    public Cancionxplaylist(BigInteger codCancion, BigInteger codPlaylist){
        this.cancionxplaylistPK = new CancionxplaylistPK(codCancion, codPlaylist);
    }
    
    public Cancionxplaylist(Cancion cancion, Playlist playlist)
    {
        this.cancion = cancion;
        this.playlist = playlist;
    }

    public CancionxplaylistPK getCancionxplaylistPK() {
        return cancionxplaylistPK;
    }

    public void setCancionxplaylistPK(CancionxplaylistPK cancionxplaylistPK) {
        this.cancionxplaylistPK = cancionxplaylistPK;
    }

    public BigInteger getOrden() {
        return orden;
    }

    public void setOrden(BigInteger orden) {
        this.orden = orden;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cancionxplaylistPK != null ? cancionxplaylistPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cancionxplaylist)) {
            return false;
        }
        Cancionxplaylist other = (Cancionxplaylist) object;
        if ((this.cancionxplaylistPK == null && other.cancionxplaylistPK != null) || (this.cancionxplaylistPK != null && !this.cancionxplaylistPK.equals(other.cancionxplaylistPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Cancionxplaylist[ cancionxplaylistPK=" + cancionxplaylistPK + " ]";
    }
    
}
