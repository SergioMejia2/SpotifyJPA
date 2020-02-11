/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sala BD
 */
@Entity
@Table(name = "ALBUM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Album.findAll", query = "SELECT a FROM Album a")
    , @NamedQuery(name = "Album.findByCodAlbum", query = "SELECT a FROM Album a WHERE a.codAlbum = :codAlbum")
    , @NamedQuery(name = "Album.findByTituloAlbum", query = "SELECT a FROM Album a WHERE a.tituloAlbum = :tituloAlbum")
    , @NamedQuery(name = "Album.findByFechaLanzamiento", query = "SELECT a FROM Album a WHERE a.fechaLanzamiento = :fechaLanzamiento")
    , @NamedQuery(name = "Album.findByIsep", query = "SELECT a FROM Album a WHERE a.isep = :isep")
    , @NamedQuery(name = "Album.findLastSong", query = "SELECT max (a.codAlbum) FROM Album a")})
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "COD_ALBUM")
    private BigInteger codAlbum;
    @Column(name = "TITULO_ALBUM")
    private String tituloAlbum;
    @Column(name = "FECHA_LANZAMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLanzamiento;
    @Column(name = "ISEP")
    private Character isep;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codAlbum")
    private List<Cancion> cancionList;
    @JoinColumn(name = "COD_DISCOGRAFICA", referencedColumnName = "COD_DISCOGRAFICA")
    @ManyToOne(optional = false)
    private Discografica codDiscografica;

    public Album() {
    }

    public Album(BigInteger codAlbum) {
        this.codAlbum = codAlbum;
    }

    public BigInteger getCodAlbum() {
        return codAlbum;
    }

    public void setCodAlbum(BigInteger codAlbum) {
        this.codAlbum = codAlbum;
    }

    public String getTituloAlbum() {
        return tituloAlbum;
    }

    public void setTituloAlbum(String tituloAlbum) {
        this.tituloAlbum = tituloAlbum;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public Character getIsep() {
        return isep;
    }

    public void setIsep(Character isep) {
        this.isep = isep;
    }

    @XmlTransient
    public List<Cancion> getCancionList() {
        return cancionList;
    }

    public void setCancionList(List<Cancion> cancionList) {
        this.cancionList = cancionList;
    }

    public Discografica getCodDiscografica() {
        return codDiscografica;
    }

    public void setCodDiscografica(Discografica codDiscografica) {
        this.codDiscografica = codDiscografica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codAlbum != null ? codAlbum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Album)) {
            return false;
        }
        Album other = (Album) object;
        if ((this.codAlbum == null && other.codAlbum != null) || (this.codAlbum != null && !this.codAlbum.equals(other.codAlbum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Album[ codAlbum=" + codAlbum + " ]";
    }
    
}
