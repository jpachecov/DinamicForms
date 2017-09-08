/**
 * @(#)DTOCImagenes.java 10/07/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */

package mx.ine.observadoresINE.dto.db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import mx.org.ine.servicios.dto.DTOBase;


/**
 *
 * @author Helaine Flores Cervantes
 * @since 10/07/2017
 * @copyright Direccion de sistemas - INE
 */
@Entity
@Table(name = "C_IMAGENES", schema="OBSERVADORESINE")
public class DTOCImagenes extends DTOBase implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DTOCImagenesPK dTOCImagenesPK;
    @Size(max = 100)
    @Column(name = "RUTA")
    private String ruta;
    @Size(max = 250)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 50)
    @Column(name = "USUARIO")
    private String usuario;
    @Column(name = "FECHA_HORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;

    public DTOCImagenes() {
    }

    public DTOCImagenes(DTOCImagenesPK dTOCImagenesPK) {
        this.dTOCImagenesPK = dTOCImagenesPK;
    }

    public DTOCImagenes(int idProcesoElectoral, int idDetalleProceso, int idImagen) {
        this.dTOCImagenesPK = new DTOCImagenesPK(idProcesoElectoral, idDetalleProceso, idImagen);
    }

    public DTOCImagenesPK getDTOCImagenesPK() {
        return dTOCImagenesPK;
    }

    public void setDTOCImagenesPK(DTOCImagenesPK dTOCImagenesPK) {
        this.dTOCImagenesPK = dTOCImagenesPK;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dTOCImagenesPK != null ? dTOCImagenesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DTOCImagenes)) {
            return false;
        }
        DTOCImagenes other = (DTOCImagenes) object;
        if ((this.dTOCImagenesPK == null && other.dTOCImagenesPK != null) || (this.dTOCImagenesPK != null && !this.dTOCImagenesPK.equals(other.dTOCImagenesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.ine.observadoresINE.dto.db.DTOCImagenes[ dTOCImagenesPK=" + dTOCImagenesPK + " ]";
    }

}
