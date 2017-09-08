/**
 * @(#)DTOReportesBitacora.java 28/06/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */

package mx.ine.observadoresINE.dto.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Helaine Flores Cervantes
 * @since 28/06/2017
 * @copyright Direccion de sistemas - INE
 */
@Entity
@Table(name = "REPORTES_BITACORA", schema="OBSERVADORESINE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DTOReportesBitacora.findAll", query = "SELECT d FROM DTOReportesBitacora d")})
public class DTOReportesBitacora implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DTOReportesBitacoraPK dTOReportesBitacoraPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_HORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;

    public DTOReportesBitacora() {
    }

    public DTOReportesBitacora(DTOReportesBitacoraPK dTOReportesBitacoraPK) {
        this.dTOReportesBitacoraPK = dTOReportesBitacoraPK;
    }

    public DTOReportesBitacora(DTOReportesBitacoraPK dTOReportesBitacoraPK, Date fechaHora) {
        this.dTOReportesBitacoraPK = dTOReportesBitacoraPK;
        this.fechaHora = fechaHora;
    }

    public DTOReportesBitacora(short idEstado, short idDistrito, short idReporte, int idBitacora, String usuario) {
        this.dTOReportesBitacoraPK = new DTOReportesBitacoraPK(idEstado, idDistrito, idReporte, idBitacora, usuario);
    }

    public DTOReportesBitacoraPK getDTOReportesBitacoraPK() {
        return dTOReportesBitacoraPK;
    }

    public void setDTOReportesBitacoraPK(DTOReportesBitacoraPK dTOReportesBitacoraPK) {
        this.dTOReportesBitacoraPK = dTOReportesBitacoraPK;
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
        hash += (dTOReportesBitacoraPK != null ? dTOReportesBitacoraPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DTOReportesBitacora)) {
            return false;
        }
        DTOReportesBitacora other = (DTOReportesBitacora) object;
        if ((this.dTOReportesBitacoraPK == null && other.dTOReportesBitacoraPK != null) || (this.dTOReportesBitacoraPK != null && !this.dTOReportesBitacoraPK.equals(other.dTOReportesBitacoraPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.ine.observadoresINE.dto.db.DTOReportesBitacora[ dTOReportesBitacoraPK=" + dTOReportesBitacoraPK + " ]";
    }

}
