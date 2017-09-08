/**
 * @(#)DTOCEscolaridadesPK.java 28/06/2017
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
public class DTOCEscolaridadesPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2950944134416328635L;
	
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
	@Column(name = "ID_ESCOLARIDAD")
	private Integer idEscolaridad;

	public DTOCEscolaridadesPK() {
	}

	public DTOCEscolaridadesPK(Integer idProcesoElectoral,
			Integer idDetalleProceso, Integer idEscolaridad) {
		this.idProcesoElectoral = idProcesoElectoral;
		this.idDetalleProceso = idDetalleProceso;
		this.idEscolaridad = idEscolaridad;
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

	public Integer getIdEscolaridad() {
		return idEscolaridad;
	}

	public void setIdEscolaridad(Integer idEscolaridad) {
		this.idEscolaridad = idEscolaridad;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (int) idProcesoElectoral;
		hash += (int) idDetalleProceso;
		hash += (int) idEscolaridad;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof DTOCEscolaridadesPK)) {
			return false;
		}
		DTOCEscolaridadesPK other = (DTOCEscolaridadesPK) object;
		if (this.idProcesoElectoral != other.idProcesoElectoral) {
			return false;
		}
		if (this.idDetalleProceso != other.idDetalleProceso) {
			return false;
		}
		if (this.idEscolaridad != other.idEscolaridad) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "mx.ine.observadoresINE.dto.db.DTOCEscolaridadesPK[ idProcesoElectoral="
				+ idProcesoElectoral
				+ ", idDetalleProceso="
				+ idDetalleProceso
				+ ", idEscolaridad=" + idEscolaridad + " ]";
	}

}
