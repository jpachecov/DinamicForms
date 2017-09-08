/**
 * @(#)DTOAcreditacionGafetes.java 07/08/2017
 * <p>
 * Copyright (C) 2017 Instituto Nacional Electoral (INE).
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import mx.org.ine.servicios.dto.DTOBase;

/**
 * <code>DTOAcreditacionGafetes.java</code> 
 *
 * @author Pablo Zuñiga Mata
 * @since 07/08/2017
 * @copyright Direccion de sistemas - INE
 */
@Entity
@Table(name = "ACREDITACION_GAFETES", schema = "OBSERVADORESINE")
public class DTOAcreditacionGafetes extends DTOBase implements Serializable{

	private static final long serialVersionUID = 8221562419660775972L;
	
	@EmbeddedId
	protected DTOAcreditacionGafetesPK dTOAcreditacionGafetesPK;
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID_ESTADO")
	private short idEstado;
	@Basic(optional = false)
	@NotNull
	@Column(name = "ID_DISTRITO")
	private short idDistrito;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 18)
	@Column(name = "CLAVE_ELECTOR")
	private String claveElector;
	@Size(max = 1)
	@Column(name = "GENERACION")
	private String generacion;
	@Column(name = "FECHA_EXPEDICION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaExpedicion;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "USUARIO")
	private String usuario;
	@Basic(optional = false)
	@NotNull
	@Column(name = "FECHA_HORA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaHora;
	
	public DTOAcreditacionGafetes(){		
	}
	
	public DTOAcreditacionGafetes(DTOAcreditacionGafetesPK dTOAcreditacionGafetesPK, short idEstado, short idDistrito,
			String generacion, Date fechaExpedicion){
		this.dTOAcreditacionGafetesPK = dTOAcreditacionGafetesPK;
		this.idEstado = idEstado;
		this.idDistrito = idDistrito;
		this.generacion = generacion;
		this.fechaExpedicion = fechaExpedicion;
	}

	/**
	 * Método que obtiene el valor del atributo dTOAcreditacionGafetesPK
	 * 
	 * @return dTOAcreditacionGafetesPK: valor del atributo dTOAcreditacionGafetesPK
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public DTOAcreditacionGafetesPK getdTOAcreditacionGafetesPK() {
		return dTOAcreditacionGafetesPK;
	}

	/**
	 * Método que ingresa el valor del atributo dTOAcreditacionGafetesPK
	 * 
	 * @param dTOAcreditacionGafetesPK: valor del atributo dTOAcreditacionGafetesPK
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public void setdTOAcreditacionGafetesPK(
			DTOAcreditacionGafetesPK dTOAcreditacionGafetesPK) {
		this.dTOAcreditacionGafetesPK = dTOAcreditacionGafetesPK;
	}

	/**
	 * Método que obtiene el valor del atributo idEstado
	 * 
	 * @return idEstado: valor del atributo idEstado
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public short getIdEstado() {
		return idEstado;
	}

	/**
	 * Método que ingresa el valor del atributo idEstado
	 * 
	 * @param idEstado: valor del atributo idEstado
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public void setIdEstado(short idEstado) {
		this.idEstado = idEstado;
	}

	/**
	 * Método que obtiene el valor del atributo idDistrito
	 * 
	 * @return idDistrito: valor del atributo idDistrito
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public short getIdDistrito() {
		return idDistrito;
	}

	/**
	 * Método que ingresa el valor del atributo idDistrito
	 * 
	 * @param idDistrito: valor del atributo idDistrito
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public void setIdDistrito(short idDistrito) {
		this.idDistrito = idDistrito;
	}

	/**
	 * Método que obtiene el valor del atributo claveElector
	 * 
	 * @return claveElector: valor del atributo claveElector
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public String getClaveElector() {
		return claveElector;
	}

	/**
	 * Método que ingresa el valor del atributo claveElector
	 * 
	 * @param claveElector: valor del atributo claveElector
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public void setClaveElector(String claveElector) {
		this.claveElector = claveElector;
	}

	/**
	 * Método que obtiene el valor del atributo generacion
	 * 
	 * @return generacion: valor del atributo generacion
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public String getGeneracion() {
		return generacion;
	}

	/**
	 * Método que ingresa el valor del atributo generacion
	 * 
	 * @param generacion: valor del atributo generacion
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public void setGeneracion(String generacion) {
		this.generacion = generacion;
	}

	/**
	 * Método que obtiene el valor del atributo fechaExpedicion
	 * 
	 * @return fechaExpedicion: valor del atributo fechaExpedicion
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public Date getFechaExpedicion() {
		return fechaExpedicion;
	}

	/**
	 * Método que ingresa el valor del atributo fechaExpedicion
	 * 
	 * @param fechaExpedicion: valor del atributo fechaExpedicion
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public void setFechaExpedicion(Date fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}

	/**
	 * Método que obtiene el valor del atributo usuario
	 * 
	 * @return usuario: valor del atributo usuario
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Método que ingresa el valor del atributo usuario
	 * 
	 * @param usuario: valor del atributo usuario
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Método que obtiene el valor del atributo fechaHora
	 * 
	 * @return fechaHora: valor del atributo fechaHora
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public Date getFechaHora() {
		return fechaHora;
	}

	/**
	 * Método que ingresa el valor del atributo fechaHora
	 * 
	 * @param fechaHora: valor del atributo fechaHora
	 * @author Pablo Zuñiga Mata.
	 * @since  07/08/2017
	 */
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((claveElector == null) ? 0 : claveElector.hashCode());
		result = prime
				* result
				+ ((dTOAcreditacionGafetesPK == null) ? 0
						: dTOAcreditacionGafetesPK.hashCode());
		result = prime * result
				+ ((fechaExpedicion == null) ? 0 : fechaExpedicion.hashCode());
		result = prime * result
				+ ((fechaHora == null) ? 0 : fechaHora.hashCode());
		result = prime * result
				+ ((generacion == null) ? 0 : generacion.hashCode());
		result = prime * result + idDistrito;
		result = prime * result + idEstado;
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		DTOAcreditacionGafetes other = (DTOAcreditacionGafetes) obj;
		if (claveElector == null) {
			if (other.claveElector != null)
				return false;
		} else if (!claveElector.equals(other.claveElector))
			return false;
		if (dTOAcreditacionGafetesPK == null) {
			if (other.dTOAcreditacionGafetesPK != null)
				return false;
		} else if (!dTOAcreditacionGafetesPK
				.equals(other.dTOAcreditacionGafetesPK))
			return false;
		if (fechaExpedicion == null) {
			if (other.fechaExpedicion != null)
				return false;
		} else if (!fechaExpedicion.equals(other.fechaExpedicion))
			return false;
		if (fechaHora == null) {
			if (other.fechaHora != null)
				return false;
		} else if (!fechaHora.equals(other.fechaHora))
			return false;
		if (generacion == null) {
			if (other.generacion != null)
				return false;
		} else if (!generacion.equals(other.generacion))
			return false;
		if (idDistrito != other.idDistrito)
			return false;
		if (idEstado != other.idEstado)
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DTOAcreditacionGafetes [dTOAcreditacionGafetesPK="
				+ dTOAcreditacionGafetesPK + ", idEstado=" + idEstado
				+ ", idDistrito=" + idDistrito + ", claveElector="
				+ claveElector + ", generacion=" + generacion
				+ ", fechaExpedicion=" + fechaExpedicion + ", usuario="
				+ usuario + ", fechaHora=" + fechaHora + "]";
	}
	

}
