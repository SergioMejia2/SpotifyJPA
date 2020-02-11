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
@Table(name = "INTERPRETE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Interprete.findAll", query = "SELECT i FROM Interprete i")
    , @NamedQuery(name = "Interprete.findByCodInterprete", query = "SELECT i FROM Interprete i WHERE i.codInterprete = :codInterprete")
    , @NamedQuery(name = "Interprete.findByNombreArtista", query = "SELECT i FROM Interprete i WHERE i.nombreArtista = :nombreArtista")
    , @NamedQuery(name = "Interprete.findByNombreReal", query = "SELECT i FROM Interprete i WHERE i.nombreReal = :nombreReal")})
public class Interprete implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "COD_INTERPRETE")
    private BigInteger codInterprete;
    @Column(name = "NOMBRE_ARTISTA")
    private String nombreArtista;
    @Column(name = "NOMBRE_REAL")
    private String nombreReal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "interprete")
    private List<Cancionxinterprete> cancionxinterpreteList;
    @JoinColumn(name = "COD_PAIS", referencedColumnName = "COD_PAIS")
    @ManyToOne(optional = false)
    private Pais codPais;

    public Interprete() {
    }

    public Interprete(BigInteger codInterprete, String nombre_real, String nombre_artista, Pais pais)
    {
        this.nombreArtista = nombre_artista;
        this.nombreReal = nombre_real;
        this.codInterprete = codInterprete;
        this.codPais = pais;
    }

    public BigInteger getCodInterprete() {
        return codInterprete;
    }

    public void setCodInterprete(BigInteger codInterprete) {
        this.codInterprete = codInterprete;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public String getNombreReal() {
        return nombreReal;
    }

    public void setNombreReal(String nombreReal) {
        this.nombreReal = nombreReal;
    }

    @XmlTransient
    public List<Cancionxinterprete> getCancionxinterpreteList() {
        return cancionxinterpreteList;
    }

    public void setCancionxinterpreteList(List<Cancionxinterprete> cancionxinterpreteList) {
        this.cancionxinterpreteList = cancionxinterpreteList;
    }

    public Pais getCodPais() {
        return codPais;
    }

    public void setCodPais(Pais codPais) {
        this.codPais = codPais;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codInterprete != null ? codInterprete.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Interprete)) {
            return false;
        }
        Interprete other = (Interprete) object;
        if ((this.codInterprete == null && other.codInterprete != null) || (this.codInterprete != null && !this.codInterprete.equals(other.codInterprete))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if(nombreArtista != null && !nombreArtista.equals(""))
            return nombreArtista;
        else return nombreReal;        
    }
    
}
