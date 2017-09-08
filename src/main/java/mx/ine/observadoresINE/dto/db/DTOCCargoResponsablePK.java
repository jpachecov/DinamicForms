/**
 * @(#)DTOCCargoResponsablePK.java 28/06/2017
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
public class DTOCCargoResponsablePK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5189518340806708260L;
	
	
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
    @Column(name = "ID_CARGO")
    private Integer idCargo;

    public DTOCCargoResponsablePK() {
    }

    public DTOCCargoResponsablePK(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idCargo) {
        this.idProcesoElectoral = idProcesoElectoral;
        this.idDetalleProceso = idDetalleProceso;
        this.idCargo = idCargo;
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

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    

    /**
	* {@inheritDoc}
	*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCargo == null) ? 0 : idCargo.hashCode());
		result = prime
				* result
				+ ((idDetalleProceso == null) ? 0 : idDetalleProceso.hashCode());
		result = prime
				* result
				+ ((idProcesoElectoral == null) ? 0 : idProcesoElectoral
						.hashCode());
		return result;
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOCCargoResponsablePK other = (DTOCCargoResponsablePK) obj;
		if (idCargo == null) {
			if (other.idCargo != null)
				return false;
		} else if (!idCargo.equals(other.idCargo))
			return false;
		if (idDetalleProceso == null) {
			if (other.idDetalleProceso != null)
				return false;
		} else if (!idDetalleProceso.equals(other.idDetalleProceso))
			return false;
		if (idProcesoElectoral == null) {
			if (other.idProcesoElectoral != null)
				return false;
		} else if (!idProcesoElectoral.equals(other.idProcesoElectoral))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "mx.ine.observadoresINE.dto.db.DTOCCargoResponsablePK[ idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso=" + idDetalleProceso + ", idCargo=" + idCargo + " ]";
    }

}
