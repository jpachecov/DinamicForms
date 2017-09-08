/**
 * @(#)DTOCursosPK.java 28/06/2017
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
public class DTOCursosPK implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2950645806634362241L;
	
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
    @Column(name = "ID_CURSO")
    private Integer idCurso;

    public DTOCursosPK() {
    }

    public DTOCursosPK(Integer idProcesoElectoral, Integer idDetalleProceso, Integer idCurso) {
        this.idProcesoElectoral = idProcesoElectoral;
        this.idDetalleProceso = idDetalleProceso;
        this.idCurso = idCurso;
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

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

	/**
	* {@inheritDoc}
	*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCurso == null) ? 0 : idCurso.hashCode());
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
		DTOCursosPK other = (DTOCursosPK) obj;
		if (idCurso == null) {
			if (other.idCurso != null)
				return false;
		} else if (!idCurso.equals(other.idCurso))
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

       
}
