/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "DISCOGRAFICA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Discografica.findAll", query = "SELECT d FROM Discografica d")
    , @NamedQuery(name = "Discografica.findByCodDiscografica", query = "SELECT d FROM Discografica d WHERE d.codDiscografica = :codDiscografica")
    , @NamedQuery(name = "Discografica.findByNombreDiscografica", query = "SELECT d FROM Discografica d WHERE d.nombreDiscografica = :nombreDiscografica")})
public class Discografica implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "COD_DISCOGRAFICA")
    private BigDecimal codDiscografica;
    @Column(name = "NOMBRE_DISCOGRAFICA")
    private String nombreDiscografica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codDiscografica")
    private List<Album> albumList;

    public Discografica() {
    }

    public Discografica(BigDecimal codDiscografica) {
        this.codDiscografica = codDiscografica;
    }

    public BigDecimal getCodDiscografica() {
        return codDiscografica;
    }

    public void setCodDiscografica(BigDecimal codDiscografica) {
        this.codDiscografica = codDiscografica;
    }

    public String getNombreDiscografica() {
        return nombreDiscografica;
    }

    public void setNombreDiscografica(String nombreDiscografica) {
        this.nombreDiscografica = nombreDiscografica;
    }

    @XmlTransient
    public List<Album> getAlbumList() {
        return albumList;
    }

    public void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codDiscografica != null ? codDiscografica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Discografica)) {
            return false;
        }
        Discografica other = (Discografica) object;
        if ((this.codDiscografica == null && other.codDiscografica != null) || (this.codDiscografica != null && !this.codDiscografica.equals(other.codDiscografica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreDiscografica;
    }
    
}
