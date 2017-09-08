/**
 * 
 */
package mx.ine.observadoresINE.dto;

import java.io.Serializable;

/**
 * @author Gerardo López
 * @since 09/08/2017
 */
public class DTOReglas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3910850333545153700L;

	/**
	 * Identificador de la regla de evaluacion
	 */
	private Integer idRegla;

	/**
	 * Descripcion de la regla
	 */
	private String descripcionRegla;

	// ****** GET & SET ********

	public Integer getIdRegla() {
		return idRegla;
	}

	public void setIdRegla(Integer idRegla) {
		this.idRegla = idRegla;
	}

	public String getDescripcionRegla() {
		return descripcionRegla;
	}

	public void setDescripcionRegla(String descripcionRegla) {
		this.descripcionRegla = descripcionRegla;
	}

}
