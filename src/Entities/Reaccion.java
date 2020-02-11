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
@Table(name = "REACCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reaccion.findAll", query = "SELECT r FROM Reaccion r")
    , @NamedQuery(name = "Reaccion.findByCodCancion", query = "SELECT r FROM Reaccion r WHERE r.reaccionPK.codCancion = :codCancion")
    , @NamedQuery(name = "Reaccion.findByNickname", query = "SELECT r FROM Reaccion r WHERE r.reaccionPK.nickname = :nickname")
    , @NamedQuery(name = "Reaccion.findByFechaLike", query = "SELECT r FROM Reaccion r WHERE r.fechaLike = :fechaLike")})
public class Reaccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReaccionPK reaccionPK;
    @Column(name = "FECHA_LIKE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLike;
    @JoinColumn(name = "COD_CANCION", referencedColumnName = "COD_CANCION", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cancion cancion;
    @JoinColumn(name = "NICKNAME", referencedColumnName = "NICKNAME", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Reaccion() {
    }

    public Reaccion(ReaccionPK reaccionPK) {
        this.reaccionPK = reaccionPK;
    }

    public Reaccion(BigInteger codCancion, String nickname) {
        this.reaccionPK = new ReaccionPK(codCancion, nickname);
    }

    public ReaccionPK getReaccionPK() {
        return reaccionPK;
    }

    public void setReaccionPK(ReaccionPK reaccionPK) {
        this.reaccionPK = reaccionPK;
    }

    public Date getFechaLike() {
        return fechaLike;
    }

    public void setFechaLike(Date fechaLike) {
        this.fechaLike = fechaLike;
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
        hash += (reaccionPK != null ? reaccionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reaccion)) {
            return false;
        }
        Reaccion other = (Reaccion) object;
        if ((this.reaccionPK == null && other.reaccionPK != null) || (this.reaccionPK != null && !this.reaccionPK.equals(other.reaccionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Reaccion[ reaccionPK=" + reaccionPK + " ]";
    }
    
}
