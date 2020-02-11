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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sala BD
 */
@Entity
@Table(name = "FAMILIAR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Familiar.findAll", query = "SELECT f FROM Familiar f")
    , @NamedQuery(name = "Familiar.findByCodSuscripcion", query = "SELECT f FROM Familiar f WHERE f.codSuscripcion = :codSuscripcion")
    , @NamedQuery(name = "Familiar.findByFechaInicio", query = "SELECT f FROM Familiar f WHERE f.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Familiar.findByFechaFinal", query = "SELECT f FROM Familiar f WHERE f.fechaFinal = :fechaFinal")
    , @NamedQuery(name = "Familiar.findByNombre", query = "SELECT f FROM Familiar f WHERE f.nombre = :nombre")})
public class Familiar implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "COD_SUSCRIPCION")
    private BigInteger codSuscripcion;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "FECHA_FINAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinal;
    @Column(name = "NOMBRE")
    private String nombre;
    @JoinColumn(name = "COD_SUSCRIPCION", referencedColumnName = "COD_SUSCRIPCION", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Suscripcion suscripcion;

    public Familiar() {
    }

    public Familiar(BigInteger codSuscripcion) {
        this.codSuscripcion = codSuscripcion;
    }

    public BigInteger getCodSuscripcion() {
        return codSuscripcion;
    }

    public void setCodSuscripcion(BigInteger codSuscripcion) {
        this.codSuscripcion = codSuscripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
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
        if (!(object instanceof Familiar)) {
            return false;
        }
        Familiar other = (Familiar) object;
        if ((this.codSuscripcion == null && other.codSuscripcion != null) || (this.codSuscripcion != null && !this.codSuscripcion.equals(other.codSuscripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Familiar[ codSuscripcion=" + codSuscripcion + " ]";
    }
    
}
