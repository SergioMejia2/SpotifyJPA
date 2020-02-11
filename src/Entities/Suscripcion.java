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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sala BD
 */
@Entity
@Table(name = "SUSCRIPCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Suscripcion.findAll", query = "SELECT s FROM Suscripcion s")
    , @NamedQuery(name = "Suscripcion.findByCodSuscripcion", query = "SELECT s FROM Suscripcion s WHERE s.codSuscripcion = :codSuscripcion")})
public class Suscripcion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "COD_SUSCRIPCION")
    private BigInteger codSuscripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codSuscripcion")
    private List<Usuario> usuarioList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "suscripcion")
    private Familiar familiar;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "suscripcion")
    private Gratuita gratuita;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "suscripcion")
    private Individual individual;

    public Suscripcion() {
    }

    public Suscripcion(BigInteger codSuscripcion) {
        this.codSuscripcion = codSuscripcion;
    }

    public BigInteger getCodSuscripcion() {
        return codSuscripcion;
    }

    public void setCodSuscripcion(BigInteger codSuscripcion) {
        this.codSuscripcion = codSuscripcion;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public Familiar getFamiliar() {
        return familiar;
    }

    public void setFamiliar(Familiar familiar) {
        this.familiar = familiar;
    }

    public Gratuita getGratuita() {
        return gratuita;
    }

    public void setGratuita(Gratuita gratuita) {
        this.gratuita = gratuita;
    }

    public Individual getIndividual() {
        return individual;
    }

    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codSuscripcion != null ? codSuscripcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Suscripcion)) {
            return false;
        }
        Suscripcion other = (Suscripcion) object;
        if ((this.codSuscripcion == null && other.codSuscripcion != null) || (this.codSuscripcion != null && !this.codSuscripcion.equals(other.codSuscripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Suscripcion[ codSuscripcion=" + codSuscripcion + " ]";
    }
    
}
