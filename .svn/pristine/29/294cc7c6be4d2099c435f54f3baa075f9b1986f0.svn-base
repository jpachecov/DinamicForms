/**
 * @(#)DTOCAccionesPK.java 28/06/2017
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
public class DTOCAccionesPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4689042784845296655L;

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
	@Column(name = "ID_ACCION")
	private Integer idAccion;

	public DTOCAccionesPK() {
	}

	public DTOCAccionesPK(Integer idProcesoElectoral, Integer idDetalleProceso,
			Integer idAccion) {
		this.idProcesoElectoral = idProcesoElectoral;
		this.idDetalleProceso = idDetalleProceso;
		this.idAccion = idAccion;
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

	public Integer getIdAccion() {
		return idAccion;
	}

	public void setIdAccion(Integer idAccion) {
		this.idAccion = idAccion;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (int) idProcesoElectoral;
		hash += (int) idDetalleProceso;
		hash += (int) idAccion;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof DTOCAccionesPK)) {
			return false;
		}
		DTOCAccionesPK other = (DTOCAccionesPK) object;
		if (this.idProcesoElectoral != other.idProcesoElectoral) {
			return false;
		}
		if (this.idDetalleProceso != other.idDetalleProceso) {
			return false;
		}
		if (this.idAccion != other.idAccion) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "mx.ine.observadoresINE.dto.db.DTOCAccionesPK[ idProcesoElectoral="
				+ idProcesoElectoral
				+ ", idDetalleProceso="
				+ idDetalleProceso
				+ ", idAccion=" + idAccion + " ]";
	}

}
