/**
 * @(#)HLPAcreditacionesGafeteAutocomplete.java 12/09/2016
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.helper;

import java.io.Serializable;

/**
 * <code>HLPAcreditacionesGafeteAutocomplete.java</code> 
 *
 * @author Pablo Zuñiga Mata
 * @version 0.0.1
 * @since 12/09/2016
 */
public class HLPAcreditacionesGafeteAutocomplete implements Serializable{

	/**
	 * Elemento necesario para la serialización de los objetos generados de esta clase.
	 */
	private static final long serialVersionUID = 7203715037805823126L;
	
	/**
	 * Variable que se encarga de recibir el valor de la llave primaria de la agrupación
	 */
	private Integer idAgrupacion;
	
	/**
	 * Variable que se encarga de recibir el valor del nombre de la agrupación
	 */
	private String nombreAgrupacion;
	
	/**
	 * Variable que se encarga de recibir la llave primaria de observador
	 */
	private Integer idObservador;
	
	/**
	 * Variable que se encarga de recibir el nombre del observador
	 */
	private String nombreObservador;
	
	/**
	 * Variable que se encarga de recibir la calve de Elector
	 */
	private String claveElector;
	
	/**
	 * Variable que se encarga de recibir la ruta de la foto
	 */
	private String rutaFoto;

	/**
	 * Método que obtiene el valor del atributo idAgrupacion
	 * 
	 * @return idAgrupacion: valor del atributo idAgrupacion
	 * @author Pablo Zuñiga Mata.
	 * @since  12/09/2016
	 */
	public Integer getIdAgrupacion() {
		return idAgrupacion;
	}

	/**
	 * Método que ingresa el valor del atributo idAgrupacion
	 * 
	 * @param idAgrupacion: valor del atributo idAgrupacion
	 * @author Pablo Zuñiga Mata.
	 * @since  12/09/2016
	 */
	public void setIdAgrupacion(Integer idAgrupacion) {
		this.idAgrupacion = idAgrupacion;
	}

	/**
	 * Método que obtiene el valor del atributo nombreAgrupacion
	 * 
	 * @return nombreAgrupacion: valor del atributo nombreAgrupacion
	 * @author Pablo Zuñiga Mata.
	 * @since  12/09/2016
	 */
	public String getNombreAgrupacion() {
		return nombreAgrupacion;
	}

	/**
	 * Método que ingresa el valor del atributo nombreAgrupacion
	 * 
	 * @param nombreAgrupacion: valor del atributo nombreAgrupacion
	 * @author Pablo Zuñiga Mata.
	 * @since  12/09/2016
	 */
	public void setNombreAgrupacion(String nombreAgrupacion) {
		this.nombreAgrupacion = nombreAgrupacion;
	}

	/**
	 * Método que obtiene el valor del atributo idObservador
	 * 
	 * @return idObservador: valor del atributo idObservador
	 * @author Pablo Zuñiga Mata.
	 * @since  12/09/2016
	 */
	public Integer getIdObservador() {
		return idObservador;
	}

	/**
	 * Método que ingresa el valor del atributo idObservador
	 * 
	 * @param idObservador: valor del atributo idObservador
	 * @author Pablo Zuñiga Mata.
	 * @since  12/09/2016
	 */
	public void setIdObservador(Integer idObservador) {
		this.idObservador = idObservador;
	}

	/**
	 * Método que obtiene el valor del atributo nombreObservador
	 * 
	 * @return nombreObservador: valor del atributo nombreObservador
	 * @author Pablo Zuñiga Mata.
	 * @since  12/09/2016
	 */
	public String getNombreObservador() {
		return nombreObservador;
	}

	/**
	 * Método que ingresa el valor del atributo nombreObservador
	 * 
	 * @param nombreObservador: valor del atributo nombreObservador
	 * @author Pablo Zuñiga Mata.
	 * @since  12/09/2016
	 */
	public void setNombreObservador(String nombreObservador) {
		this.nombreObservador = nombreObservador;
	}

	/**
	 * Método que obtiene el valor del atributo claveElector
	 * 
	 * @return claveElector: valor del atributo claveElector
	 * @author Pablo Zuñiga Mata.
	 * @since  12/09/2016
	 */
	public String getClaveElector() {
		return claveElector;
	}

	/**
	 * Método que ingresa el valor del atributo claveElector
	 * 
	 * @param claveElector: valor del atributo claveElector
	 * @author Pablo Zuñiga Mata.
	 * @since  12/09/2016
	 */
	public void setClaveElector(String claveElector) {
		this.claveElector = claveElector;
	}

	/**
	 * Método que obtiene el valor del atributo rutaFoto
	 * 
	 * @return rutaFoto: valor del atributo rutaFoto
	 * @author Pablo Zuñiga Mata.
	 * @since  12/09/2016
	 */
	public String getRutaFoto() {
		return rutaFoto;
	}

	/**
	 * Método que ingresa el valor del atributo rutaFoto
	 * 
	 * @param rutaFoto: valor del atributo rutaFoto
	 * @author Pablo Zuñiga Mata.
	 * @since  12/09/2016
	 */
	public void setRutaFoto(String rutaFoto) {
		this.rutaFoto = rutaFoto;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idObservador == null) ? 0 : idObservador.hashCode());
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
		HLPAcreditacionesGafeteAutocomplete other = (HLPAcreditacionesGafeteAutocomplete) obj;
		if (idObservador == null) {
			if (other.idObservador != null)
				return false;
		} else if (!idObservador.equals(other.idObservador))
			return false;
		return true;
	}
}
