/**
 * @(#)DTOAcreditacionGafetesPK.java 07/08/2017
 * <p>
 * Copyright (C) 2017 Instituto Nacional Electoral (INE).
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
 * <code>DTOAcreditacionGafetesPK.java</code> 
 *
 * @author Pablo Zuñiga Mata
 * @since 07/08/2017
 * @copyright Direccion de sistemas - INE
 */
@Embeddable
public class DTOAcreditacionGafetesPK implements Serializable{

	private static final long serialVersionUID = -5235648030868700551L;
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
    @Column(name = "ID_GENERACION")
    private short idGeneracion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_OBSERVADOR")
    private short idObservador;
    
    /**
     * Constructor
     */
    public DTOAcreditacionGafetesPK(){    	
    }
    
    /**
     * Constructor
     * 
     * @param idProcesoElectoral
     * @param idDetalleProceso
     * @param idGeneracion
     * @param idObservador
     */
    public DTOAcreditacionGafetesPK(short idProcesoElectoral, short idDetalleProceso, short idGeneracion , short idObservador){ 
        this.idProcesoElectoral = idProcesoElectoral;
        this.idDetalleProceso = idDetalleProceso;
        this.idGeneracion = idGeneracion; 
        this.idObservador = idObservador;   	
    }

	/**
	 * Método que obtiene el valor del atributo idProcesoElectoral
	 * 
	 * @return idProcesoElectoral: valor del atributo idProcesoElectoral
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public short getIdProcesoElectoral() {
		return idProcesoElectoral;
	}

	/**
	 * Método que ingresa el valor del atributo idProcesoElectoral
	 * 
	 * @param idProcesoElectoral: valor del atributo idProcesoElectoral
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public void setIdProcesoElectoral(short idProcesoElectoral) {
		this.idProcesoElectoral = idProcesoElectoral;
	}

	/**
	 * Método que obtiene el valor del atributo idDetalleProceso
	 * 
	 * @return idDetalleProceso: valor del atributo idDetalleProceso
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public short getIdDetalleProceso() {
		return idDetalleProceso;
	}

	/**
	 * Método que ingresa el valor del atributo idDetalleProceso
	 * 
	 * @param idDetalleProceso: valor del atributo idDetalleProceso
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public void setIdDetalleProceso(short idDetalleProceso) {
		this.idDetalleProceso = idDetalleProceso;
	}

	/**
	 * Método que obtiene el valor del atributo idGeneracion
	 * 
	 * @return idGeneracion: valor del atributo idGeneracion
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public short getIdGeneracion() {
		return idGeneracion;
	}

	/**
	 * Método que ingresa el valor del atributo idGeneracion
	 * 
	 * @param idGeneracion: valor del atributo idGeneracion
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public void setIdGeneracion(short idGeneracion) {
		this.idGeneracion = idGeneracion;
	}

	/**
	 * Método que obtiene el valor del atributo idObservador
	 * 
	 * @return idObservador: valor del atributo idObservador
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public short getIdObservador() {
		return idObservador;
	}

	/**
	 * Método que ingresa el valor del atributo idObservador
	 * 
	 * @param idObservador: valor del atributo idObservador
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public void setIdObservador(short idObservador) {
		this.idObservador = idObservador;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idDetalleProceso;
		result = prime * result + idGeneracion;
		result = prime * result + idObservador;
		result = prime * result + idProcesoElectoral;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DTOAcreditacionGafetesPK other = (DTOAcreditacionGafetesPK) obj;
		if (idDetalleProceso != other.idDetalleProceso)
			return false;
		if (idGeneracion != other.idGeneracion)
			return false;
		if (idObservador != other.idObservador)
			return false;
		if (idProcesoElectoral != other.idProcesoElectoral)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DTOAcreditacionGafetesPK [idProcesoElectoral="
				+ idProcesoElectoral + ", idDetalleProceso=" + idDetalleProceso
				+ ", idGeneracion=" + idGeneracion + ", idObservador="
				+ idObservador + "]";
	}
    
    

}
