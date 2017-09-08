/**
 * @(#)DTOReglasEvalucaionPK.java 11/07/2017
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
 * @since 11/07/2017
 * @copyright Direccion de sistemas - INE
 */
@Embeddable
public class DTOReglasEvalucaionPK implements Serializable {

    private static final long serialVersionUID = -2322962407341860620L;

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
    @Column(name = "ID_EVALUACION")
    private Integer idEvaluacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_REGLA")
    private Integer idRegla;

    public DTOReglasEvalucaionPK() {
    }

    public DTOReglasEvalucaionPK(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idEvaluacion, Integer idRegla) {
        this.idProcesoElectoral = idProcesoElectoral;
        this.idDetalleProceso = idDetalleProceso;
        this.idEvaluacion = idEvaluacion;
        this.idRegla = idRegla;
    }

    public Integer getIdProcesoElectoral() {
        return idProcesoElectoral;
    }

    public void setIdProcesoElectoral(Integer idProcesoElectoral) {
        this.idProcesoElectoral = idProcesoElectoral;
    }

    public Integer getIdDetalleProceso() {
        return idDetalleProceso;
    }

    public void setIdDetalleProceso(Integer idDetalleProceso) {
        this.idDetalleProceso = idDetalleProceso;
    }

    public Integer getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(Integer idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public Integer getIdRegla() {
        return idRegla;
    }

    public void setIdRegla(Integer idRegla) {
        this.idRegla = idRegla;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += idProcesoElectoral;
        hash += idDetalleProceso;
        hash += idEvaluacion;
        hash += idRegla;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DTOReglasEvalucaionPK)) {
            return false;
        }
        DTOReglasEvalucaionPK other = (DTOReglasEvalucaionPK) object;
        if (this.idProcesoElectoral != other.idProcesoElectoral) {
            return false;
        }
        if (this.idDetalleProceso != other.idDetalleProceso) {
            return false;
        }
        if (this.idEvaluacion != other.idEvaluacion) {
            return false;
        }
        if (this.idRegla != other.idRegla) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.ine.observadoresINE.dto.db.DTOReglasEvalucaionPK[ idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso=" + idDetalleProceso + ", idEvaluacion=" + idEvaluacion + ", idRegla=" + idRegla + " ]";
    }

}
