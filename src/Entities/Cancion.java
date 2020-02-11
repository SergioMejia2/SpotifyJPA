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
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
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
@Table(name = "CANCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cancion.findAll", query = "SELECT c FROM Cancion c")
    , @NamedQuery(name = "Cancion.findByCodCancion", query = "SELECT c FROM Cancion c WHERE c.codCancion = :codCancion")
    , @NamedQuery(name = "Cancion.findByTituloCancion", query = "SELECT c FROM Cancion c WHERE c.tituloCancion = :tituloCancion")})
public class Cancion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "COD_CANCION")
    private BigInteger codCancion;
    @Basic(optional = false)
    @Column(name = "TITULO_CANCION")
    private String tituloCancion;
    @Basic(optional = false)
    @Column(name = "TIEMPO_REPRM")
    private BigInteger tiempoReprM;
    @Basic(optional = false)
    @Column(name = "TIEMPO_REPRS")
    private BigInteger tiempoReprS;
    @Lob
    @Column(name = "FILE_CANCION")
    private Serializable fileCancion;
    @JoinColumn(name = "COD_ALBUM", referencedColumnName = "COD_ALBUM")
    @ManyToOne(optional = false)
    private Album codAlbum;
    @JoinColumns({
        @JoinColumn(name = "COD_GENERO", referencedColumnName = "COD_GENERO")
        , @JoinColumn(name = "COD_IDIOMA", referencedColumnName = "COD_IDIOMA")})
    @ManyToOne(optional = false)
    private Genero genero;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cancion")
    private List<Cancionxinterprete> cancionxinterpreteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cancion")
    private List<Registro> registroList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cancion")
    private List<Reaccion> reaccionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cancion")
    private List<Cancionxplaylist> cancionxplaylistList;

    public Cancion() {
    }

    public Cancion(BigInteger codCancion) {
        this.codCancion = codCancion;
    }

    public Cancion(BigInteger codCancion, String tituloCancion, BigInteger tiempoReprM, BigInteger tiempoReprS) {
        this.codCancion = codCancion;
        this.tituloCancion = tituloCancion;
        this.tiempoReprM = tiempoReprM;
        this.tiempoReprS = tiempoReprS;
    }

    public BigInteger getCodCancion() {
        return codCancion;
    }

    public void setCodCancion(BigInteger codCancion) {
        this.codCancion = codCancion;
    }

    public String getTituloCancion() {
        return tituloCancion;
    }

    public void setTituloCancion(String tituloCancion) {
        this.tituloCancion = tituloCancion;
    }

    public BigInteger getTiempoReprM()
    {
        return tiempoReprM;
    }

    public BigInteger getTiempoReprS()
    {
        return tiempoReprS;
    }

    public Serializable getFileCancion() {
        return fileCancion;
    }

    public void setFileCancion(Serializable fileCancion) {
        this.fileCancion = fileCancion;
    }

    public Album getCodAlbum() {
        return codAlbum;
    }

    public void setCodAlbum(Album codAlbum) {
        this.codAlbum = codAlbum;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    @XmlTransient
    public List<Cancionxinterprete> getCancionxinterpreteList() {
        return cancionxinterpreteList;
    }

    public void setCancionxinterpreteList(List<Cancionxinterprete> cancionxinterpreteList) {
        this.cancionxinterpreteList = cancionxinterpreteList;
    }

    @XmlTransient
    public List<Registro> getRegistroList() {
        return registroList;
    }

    public void setRegistroList(List<Registro> registroList) {
        this.registroList = registroList;
    }

    @XmlTransient
    public List<Reaccion> getReaccionList() {
        return reaccionList;
    }

    public void setReaccionList(List<Reaccion> reaccionList) {
        this.reaccionList = reaccionList;
    }

    @XmlTransient
    public List<Cancionxplaylist> getCancionxplaylistList() {
        return cancionxplaylistList;
    }

    public void setCancionxplaylistList(List<Cancionxplaylist> cancionxplaylistList) {
        this.cancionxplaylistList = cancionxplaylistList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCancion != null ? codCancion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cancion)) {
            return false;
        }
        Cancion other = (Cancion) object;
        if ((this.codCancion == null && other.codCancion != null) || (this.codCancion != null && !this.codCancion.equals(other.codCancion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tituloCancion;
    }
    
}
