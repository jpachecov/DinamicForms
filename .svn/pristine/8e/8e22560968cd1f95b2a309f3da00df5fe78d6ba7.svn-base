/**
 * @(#)DTOObservadoresPK.java 28/06/2017
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
import javax.validation.constraints.Size;

/**
 *
 * @author Helaine Flores Cervantes
 * @since 28/06/2017
 * @copyright Direccion de sistemas - INE
 */
@Embeddable
public class DTOObservadoresPK implements Serializable {
 
	private static final long serialVersionUID = 573051217251044714L;
	@Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROCESO_ELECTORAL")
    private short idProcesoElectoral;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DETALLE_PROCESO")
    private short idDetalleProceso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_OBSERVADOR")
    private short idObservador;
    
    public short getIdObservador() {
		return idObservador;
	}

	public void setIdObservador(short idObservador) {
		this.idObservador = idObservador;
	}

	public DTOObservadoresPK() {
    }

    public DTOObservadoresPK(short idProcesoElectoral, short idDetalleProceso , short idObservador) {
        this.idProcesoElectoral = idProcesoElectoral;
        this.idDetalleProceso = idDetalleProceso;
        this.idObservador = idObservador;
       
    }

    public short getIdProcesoElectoral() {
        return idProcesoElectoral;
    }

    public void setIdProcesoElectoral(short idProcesoElectoral) {
        this.idProcesoElectoral = idProcesoElectoral;
    }

    public short getIdDetalleProceso() {
        return idDetalleProceso;
    }

    public void setIdDetalleProceso(short idDetalleProceso) {
        this.idDetalleProceso = idDetalleProceso;
    }
 

     
   
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idDetalleProceso;
		result = prime * result + idProcesoElectoral;
		result = prime * result + idObservador;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOObservadoresPK other = (DTOObservadoresPK) obj;
		if (idDetalleProceso != other.idDetalleProceso)
			return false;
		if (idProcesoElectoral != other.idProcesoElectoral)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DTOObservadoresPK [idProcesoElectoral=" + idProcesoElectoral + ", idDetalleProceso=" + idDetalleProceso + ", idObservador= " + idObservador
				+ "]";
	}
 

}
