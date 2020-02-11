/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "PLAYLIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Playlist.findAll", query = "SELECT p FROM Playlist p")
    , @NamedQuery(name = "Playlist.findByCodPlaylist", query = "SELECT p FROM Playlist p WHERE p.codPlaylist = :codPlaylist")
    , @NamedQuery(name = "Playlist.findByNombrePlaylist", query = "SELECT p FROM Playlist p WHERE p.nombrePlaylist = :nombrePlaylist")
    , @NamedQuery(name = "Playlist.findByPrivacidad", query = "SELECT p FROM Playlist p WHERE p.privacidad = :privacidad")})
public class Playlist implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "COD_PLAYLIST")
    private BigInteger codPlaylist;
    @Column(name = "NOMBRE_PLAYLIST")
    private String nombrePlaylist;
    @Column(name = "PRIVACIDAD")
    private String privacidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playlist")
    private List<Cancionxplaylist> cancionxplaylistList;
    @JoinColumn(name = "NICKNAME", referencedColumnName = "NICKNAME")
    @ManyToOne(optional = false)
    private Usuario nickname;

    public Playlist() {
    }

    public Playlist(BigInteger codPlaylist) {
        this.codPlaylist = codPlaylist;
    }

    public BigInteger getCodPlaylist() {
        return codPlaylist;
    }

    public void setCodPlaylist(BigInteger codPlaylist) {
        this.codPlaylist = codPlaylist;
    }

    public String getNombrePlaylist() {
        return nombrePlaylist;
    }

    public void setNombrePlaylist(String nombrePlaylist) {
        this.nombrePlaylist = nombrePlaylist;
    }

    public String getPrivacidad() {
        return privacidad;
    }

    public void setPrivacidad(String privacidad) {
        this.privacidad = privacidad;
    }

    @XmlTransient
    public List<Cancionxplaylist> getCancionxplaylistList() {
        return cancionxplaylistList;
    }

    public void setCancionxplaylistList(List<Cancionxplaylist> cancionxplaylistList) {
        this.cancionxplaylistList = cancionxplaylistList;
    }

    public Usuario getNickname() {
        return nickname;
    }

    public void setNickname(Usuario nickname) {
        this.nickname = nickname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codPlaylist != null ? codPlaylist.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Playlist)) {
            return false;
        }
        Playlist other = (Playlist) object;
        if ((this.codPlaylist == null && other.codPlaylist != null) || (this.codPlaylist != null && !this.codPlaylist.equals(other.codPlaylist))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombrePlaylist;
    }
    
}
