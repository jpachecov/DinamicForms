/**
 * @(#)DTOAgrupacionesPK.java 28/06/2017
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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Helaine Flores Cervantes
 * @since 28/06/2017
 * @copyright Direccion de sistemas - INE
 */
@Embeddable
public class DTOAgrupacionesPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 815561303583759060L;
	
	

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
    @Column(name = "ID_AGRUPACION")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="course_seq")
	@SequenceGenerator(
		name="course_seq",
		sequenceName="course_sequence",
		allocationSize=20
	)
    private Integer idAgrupacion;

    public DTOAgrupacionesPK() {
    }

    public DTOAgrupacionesPK(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idAgrupacion) {
        this.idProcesoElectoral = idProcesoElectoral;
        this.idDetalleProceso = idDetalleProceso;
        this.idAgrupacion = idAgrupacion;
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

    public Integer getIdAgrupacion() {
        return idAgrupacion;
    }

    public void setIdAgrupacion(Integer idAgrupacion) {
        this.idAgrupacion = idAgrupacion;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += idProcesoElectoral;
        hash += idDetalleProceso;
        hash += idAgrupacion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DTOAgrupacionesPK)) {
            return false;
        }
        DTOAgrupacionesPK other = (DTOAgrupacionesPK) object;
        if (this.idProcesoElectoral != other.idProcesoElectoral) {
            return false;
        }
        if (this.idDetalleProceso != other.idDetalleProceso) {
            return false;
        }
        if (this.idAgrupacion != other.idAgrupacion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.ine.observadoresINE.dto.db.DTOAgrupacionesPK[ idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso=" + idDetalleProceso + ", idAgrupacion=" + idAgrupacion + " ]";
    }

}
