/**
 * @(#)DTOCTextos.java 10/07/2017
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
@Table(name = "C_TEXTOS", schema="OBSERVADORESINE")
public class DTOCTextos extends DTOBase implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DTOCTextosPK dTOCTextosPK;
    @Size(max = 250)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 50)
    @Column(name = "USUARIO")
    private String usuario;
    @Column(name = "FECHA_HORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;

    public DTOCTextos() {
    }

    public DTOCTextos(DTOCTextosPK dTOCTextosPK) {
        this.dTOCTextosPK = dTOCTextosPK;
    }

    public DTOCTextos(int idProcesoElectoral, int idDetalleProceso, int idParrafo, int idTexto) {
        this.dTOCTextosPK = new DTOCTextosPK(idProcesoElectoral, idDetalleProceso, idParrafo, idTexto);
    }

    public DTOCTextosPK getDTOCTextosPK() {
        return dTOCTextosPK;
    }

    public void setDTOCTextosPK(DTOCTextosPK dTOCTextosPK) {
        this.dTOCTextosPK = dTOCTextosPK;
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
        hash += (dTOCTextosPK != null ? dTOCTextosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DTOCTextos)) {
            return false;
        }
        DTOCTextos other = (DTOCTextos) object;
        if ((this.dTOCTextosPK == null && other.dTOCTextosPK != null) || (this.dTOCTextosPK != null && !this.dTOCTextosPK.equals(other.dTOCTextosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.ine.observadoresINE.dto.db.DTOCTextos[ dTOCTextosPK=" + dTOCTextosPK + " ]";
    }

}
