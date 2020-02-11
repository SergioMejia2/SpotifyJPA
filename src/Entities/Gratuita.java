/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sala BD
 */
@Entity
@Table(name = "GRATUITA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gratuita.findAll", query = "SELECT g FROM Gratuita g")
    , @NamedQuery(name = "Gratuita.findByCodSuscripcion", query = "SELECT g FROM Gratuita g WHERE g.codSuscripcion = :codSuscripcion")
    , @NamedQuery(name = "Gratuita.findByNombre", query = "SELECT g FROM Gratuita g WHERE g.nombre = :nombre")})
public class Gratuita implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "COD_SUSCRIPCION")
    private BigInteger codSuscripcion;
    @Column(name = "NOMBRE")
    private String nombre;
    @JoinColumn(name = "COD_SUSCRIPCION", referencedColumnName = "COD_SUSCRIPCION", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Suscripcion suscripcion;

    public Gratuita() {
    }

    public Gratuita(BigInteger codSuscripcion) {
        this.codSuscripcion = codSuscripcion;
    }

    public BigInteger getCodSuscripcion() {
        return codSuscripcion;
    }

    public void setCodSuscripcion(BigInteger codSuscripcion) {
        this.codSuscripcion = codSuscripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Suscripcion getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(Suscripcion suscripcion) {
        this.suscripcion = suscripcion;
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
        if (!(object instanceof Gratuita)) {
            return false;
        }
        Gratuita other = (Gratuita) object;
        if ((this.codSuscripcion == null && other.codSuscripcion != null) || (this.codSuscripcion != null && !this.codSuscripcion.equals(other.codSuscripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Gratuita[ codSuscripcion=" + codSuscripcion + " ]";
    }
    
}
