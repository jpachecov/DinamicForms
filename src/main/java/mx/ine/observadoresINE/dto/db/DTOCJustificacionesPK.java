/**
 * @(#)DTOCJustificacionesPK.java 28/06/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */

package mx.ine.observadoresINE.dto.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Helaine Flores Cervantes
 * @since 28/06/2017
 * @copyright Direccion de sistemas - INE
 */
@Embeddable
public class DTOCJustificacionesPK implements Serializable {

    private static final long serialVersionUID = 7860850465346594783L;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROCESO_ELECTORAL")
    private Integer idProcesoElectoral;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DETALLE_PROCESO")
    private Integer idDetalleProceso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_JUSTIFICACION")
    private Integer idJustificacion;

    public DTOCJustificacionesPK() {
    }

    public DTOCJustificacionesPK(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idJustificacion) {
        this.idProcesoElectoral = idProcesoElectoral;
        this.idDetalleProceso = idDetalleProceso;
        this.idJustificacion = idJustificacion;
    }

    public Integer getIdProcesoElectoral() {
        return idProcesoElectoral;
    }

    public void setIdProcesoElectoral(int idProcesoElectoral) {
        this.idProcesoElectoral = idProcesoElectoral;
    }

    public Integer getIdDetalleProceso() {
        return idDetalleProceso;
    }

    public void setIdDetalleProceso(int idDetalleProceso) {
        this.idDetalleProceso = idDetalleProceso;
    }

    public Integer getIdJustificacion() {
        return idJustificacion;
    }

    public void setIdJustificacion(Integer idJustificacion) {
        this.idJustificacion = idJustificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idProcesoElectoral;
        hash += (int) idDetalleProceso;
        hash += (int) idJustificacion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DTOCJustificacionesPK)) {
            return false;
        }
        DTOCJustificacionesPK other = (DTOCJustificacionesPK) object;
        if (this.idProcesoElectoral != other.idProcesoElectoral) {
            return false;
        }
        if (this.idDetalleProceso != other.idDetalleProceso) {
            return false;
        }
        if (this.idJustificacion != other.idJustificacion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.ine.observadoresINE.dto.db.DTOCJustificacionesPK[ idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso=" + idDetalleProceso + ", idJustificacion=" + idJustificacion + " ]";
    }

}
