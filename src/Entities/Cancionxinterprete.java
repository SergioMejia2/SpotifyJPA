/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sala BD
 */
@Entity
@Table(name = "CANCIONXINTERPRETE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cancionxinterprete.findAll", query = "SELECT c FROM Cancionxinterprete c")
    , @NamedQuery(name = "Cancionxinterprete.findByCodInterprete", query = "SELECT c FROM Cancionxinterprete c WHERE c.cancionxinterpretePK.codInterprete = :codInterprete")
    , @NamedQuery(name = "Cancionxinterprete.findByCodCancion", query = "SELECT c FROM Cancionxinterprete c WHERE c.cancionxinterpretePK.codCancion = :codCancion")
    , @NamedQuery(name = "Cancionxinterprete.findByRol", query = "SELECT c FROM Cancionxinterprete c WHERE c.rol = :rol")})
public class Cancionxinterprete implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CancionxinterpretePK cancionxinterpretePK;
    @Basic(optional = false)
    @Column(name = "ROL")
    private String rol;
    @JoinColumn(name = "COD_CANCION", referencedColumnName = "COD_CANCION", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cancion cancion;
    @JoinColumn(name = "COD_INTERPRETE", referencedColumnName = "COD_INTERPRETE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Interprete interprete;

    public Cancionxinterprete() {
    }

    public Cancionxinterprete(CancionxinterpretePK cancionxinterpretePK) {
        this.cancionxinterpretePK = cancionxinterpretePK;
    }

    public Cancionxinterprete(CancionxinterpretePK cancionxinterpretePK, String rol) {
        this.cancionxinterpretePK = cancionxinterpretePK;
        this.rol = rol;
    }

    public Cancionxinterprete(BigInteger codInterprete, BigInteger codCancion) {
        this.cancionxinterpretePK = new CancionxinterpretePK(codInterprete, codCancion);
    }

    public CancionxinterpretePK getCancionxinterpretePK() {
        return cancionxinterpretePK;
    }

    public void setCancionxinterpretePK(CancionxinterpretePK cancionxinterpretePK) {
        this.cancionxinterpretePK = cancionxinterpretePK;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }

    public Interprete getInterprete() {
        return interprete;
    }

    public void setInterprete(Interprete interprete) {
        this.interprete = interprete;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cancionxinterpretePK != null ? cancionxinterpretePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cancionxinterprete)) {
            return false;
        }
        Cancionxinterprete other = (Cancionxinterprete) object;
        if ((this.cancionxinterpretePK == null && other.cancionxinterpretePK != null) || (this.cancionxinterpretePK != null && !this.cancionxinterpretePK.equals(other.cancionxinterpretePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Cancionxinterprete[ cancionxinterpretePK=" + cancionxinterpretePK + " ]";
    }
    
}
