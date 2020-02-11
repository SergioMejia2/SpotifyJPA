/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sala BD
 */
@Entity
@Table(name = "REGISTRO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Registro.findAll", query = "SELECT r FROM Registro r")
    , @NamedQuery(name = "Registro.findByFechaRepr", query = "SELECT r FROM Registro r WHERE r.registroPK.fechaRepr = :fechaRepr")
    , @NamedQuery(name = "Registro.findByFuesaltada", query = "SELECT r FROM Registro r WHERE r.fuesaltada = :fuesaltada")
    , @NamedQuery(name = "Registro.findByCodCancion", query = "SELECT r FROM Registro r WHERE r.registroPK.codCancion = :codCancion")
    , @NamedQuery(name = "Registro.findByNickname", query = "SELECT r FROM Registro r WHERE r.registroPK.nickname = :nickname")})
public class Registro implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RegistroPK registroPK;
    @Column(name = "FUESALTADA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fuesaltada;
    @JoinColumn(name = "COD_CANCION", referencedColumnName = "COD_CANCION", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cancion cancion;
    @JoinColumn(name = "NICKNAME", referencedColumnName = "NICKNAME", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Registro() {
    }

    public Registro(RegistroPK registroPK) {
        this.registroPK = registroPK;
    }

    public Registro(Date fechaRepr, BigInteger codCancion, String nickname) {
        this.registroPK = new RegistroPK(fechaRepr, codCancion, nickname);
    }

    public RegistroPK getRegistroPK() {
        return registroPK;
    }

    public void setRegistroPK(RegistroPK registroPK) {
        this.registroPK = registroPK;
    }

    public Date getFuesaltada() {
        return fuesaltada;
    }

    public void setFuesaltada(Date fuesaltada) {
        this.fuesaltada = fuesaltada;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (registroPK != null ? registroPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registro)) {
            return false;
        }
        Registro other = (Registro) object;
        if ((this.registroPK == null && other.registroPK != null) || (this.registroPK != null && !this.registroPK.equals(other.registroPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Registro[ registroPK=" + registroPK + " ]";
    }
    
}
