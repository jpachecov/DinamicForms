/**
 * @(#)HLPAcreditacionGafete.java 20/09/2016
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.helper;

import java.io.Serializable;
import java.util.Calendar;

/**
 * <code>HLPAcreditacionGafete.java</code> 
 *
 * @author Pablo Zuñiga Mata
 * @version 0.0.1
 * @since 20/09/2016
 */
public class HLPAcreditacionGafete implements Serializable{

	/**
	 * Elemento necesario para la serialización de los objetos generados de esta clase.
	 */
	private static final long serialVersionUID = 2873647535056248286L;
	
	/**
	 * Representa el apellido paterno del solicitante acreditado
	 */
	private String apellidoPaterno;
	
	/**
	 * Representa el apellido materno del solicitante acreditado
	 */
	private String apellidoMaterno;
	
	/**
	 * Representa el nombre del solicitante acreditado
	 */
	private String nombre;
	
	/**
	 * Representa el folio del solicitante acreditado
	 */
	private String  folio;
	
	/**
	 * Representa la ruta del solicitante acreditado.
	 */
	private String foto;
	
	/**
	 * Representa el vocal presidente para el gafete
	 */
	private String  vocalPresidenteD;
	
	/**
	 * Representa el vocal secretario para el gafete
	 */
	private String  vocalSecretarioD;
	
	/**
	 * Propiedad que contiene los valores de los vocalesEstatales
	 */
	private String vocalPresidenteE;
	
	/**
	 * Propiedad que contiene los valores de los vocalesEstatales
	 */
	private String vocalSecretarioE;
	
	/**
	 * Representa la clave del vocal presidente para el gafete
	 */
	private String  fotoVocalPresidenteD;
	
	/**
	 * Representa la clave del vocal secretario para el gafete
	 */
	private String  fotoVocalSecretarioD;
	
	/**
	 * Representa la clave del vocales presidente Estatales
	 */
	private String fotoVocalPresidenteE;
	
	/**
	 * Representa la clave del vocales secretario Estatales
	 */
	private String fotoVocalSecretarioE;
	
	/**
	 * Representa la fecha de acreditación
	 */
	private Calendar fechaacreditacion;
	
	/**
	 * Representa la fecha de sesión
	 */
	private Calendar fechasesion;
	
	/**
	 * Representa la clave de elector del observador
	 */
	private String claveelector;
	
	/**
	 * Representa el genero del elector
	 */
	private String sexo;
	
	/**
	 * Nombre del estado al que pertenece la recidencia del observador 
	 */
	private String nombreestado;
	
	/**
	 * Estado para saber si fue acreditado anteriormente o no 
	 */
	private String acreditado;

	/**
	 * Propiedad que indica el valor dela acreditación
	 */
	private Integer idJustificacion;

	/**
	 * Identificador del observador
	 */
	private Integer idOservador;
	
	/**
	 * Porpiedad que indica de donde se obtiene la información
	 */
	private Integer estado;
	
	/**
	 * Porpiedad que indica de donde se obtiene la información
	 */
	private Integer distrito;
	
	/**
	 * Propiedad que iondica el genero del observador par generar el gafete
	 */
	private String sexoObs;
	
	/**
	 * Propiedad que indica si el gafete o acreditacion va sin firma
	 */
	private Boolean sinFirma;
	
	/**
	 * Propiedad que indica si el gafete o acreditacion va con firma
	 */
	private Boolean conFirma;
	
	public HLPAcreditacionGafete(){
		this.sinFirma = false;
		this.conFirma = false;
	}
	
	/**
	 * Método que obtiene el valor del atributo apellidoPaterno
	 * 
	 * @return apellidoPaterno: valor del atributo apellidoPaterno
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * Método que ingresa el valor del atributo apellidoPaterno
	 * 
	 * @param apellidoPaterno: valor del atributo apellidoPaterno
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * Método que obtiene el valor del atributo apellidoMaterno
	 * 
	 * @return apellidoMaterno: valor del atributo apellidoMaterno
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * Método que ingresa el valor del atributo apellidoMaterno
	 * 
	 * @param apellidoMaterno: valor del atributo apellidoMaterno
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	/**
	 * Método que obtiene el valor del atributo nombre
	 * 
	 * @return nombre: valor del atributo nombre
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método que ingresa el valor del atributo nombre
	 * 
	 * @param nombre: valor del atributo nombre
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Método que obtiene el valor del atributo folio
	 * 
	 * @return folio: valor del atributo folio
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * Método que ingresa el valor del atributo folio
	 * 
	 * @param folio: valor del atributo folio
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * Método que obtiene el valor del atributo foto
	 * 
	 * @return foto: valor del atributo foto
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public String getFoto() {
		return foto;
	}

	/**
	 * Método que ingresa el valor del atributo foto
	 * 
	 * @param foto: valor del atributo foto
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setFoto(String foto) {
		this.foto = foto;
	}

	/**
	 * Método que obtiene el valor del atributo vocalPresidenteD
	 * 
	 * @return vocalPresidenteD: valor del atributo vocalPresidenteD
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public String getVocalPresidenteD() {
		return vocalPresidenteD;
	}

	/**
	 * Método que ingresa el valor del atributo vocalPresidenteD
	 * 
	 * @param vocalPresidenteD: valor del atributo vocalPresidenteD
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setVocalPresidenteD(String vocalPresidenteD) {
		this.vocalPresidenteD = vocalPresidenteD;
	}

	/**
	 * Método que obtiene el valor del atributo vocalSecretarioD
	 * 
	 * @return vocalSecretarioD: valor del atributo vocalSecretarioD
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public String getVocalSecretarioD() {
		return vocalSecretarioD;
	}

	/**
	 * Método que ingresa el valor del atributo vocalSecretarioD
	 * 
	 * @param vocalSecretarioD: valor del atributo vocalSecretarioD
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setVocalSecretarioD(String vocalSecretarioD) {
		this.vocalSecretarioD = vocalSecretarioD;
	}

	/**
	 * Método que obtiene el valor del atributo vocalPresidenteE
	 * 
	 * @return vocalPresidenteE: valor del atributo vocalPresidenteE
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public String getVocalPresidenteE() {
		return vocalPresidenteE;
	}

	/**
	 * Método que ingresa el valor del atributo vocalPresidenteE
	 * 
	 * @param vocalPresidenteE: valor del atributo vocalPresidenteE
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setVocalPresidenteE(String vocalPresidenteE) {
		this.vocalPresidenteE = vocalPresidenteE;
	}

	/**
	 * Método que obtiene el valor del atributo vocalSecretarioE
	 * 
	 * @return vocalSecretarioE: valor del atributo vocalSecretarioE
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public String getVocalSecretarioE() {
		return vocalSecretarioE;
	}

	/**
	 * Método que ingresa el valor del atributo vocalSecretarioE
	 * 
	 * @param vocalSecretarioE: valor del atributo vocalSecretarioE
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setVocalSecretarioE(String vocalSecretarioE) {
		this.vocalSecretarioE = vocalSecretarioE;
	}	

	/**
	 * Método que obtiene el valor del atributo fechaacreditacion
	 * 
	 * @return fechaacreditacion: valor del atributo fechaacreditacion
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public Calendar getFechaacreditacion() {
		return fechaacreditacion;
	}

	/**
	 * Método que ingresa el valor del atributo fechaacreditacion
	 * 
	 * @param fechaacreditacion: valor del atributo fechaacreditacion
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setFechaacreditacion(Calendar fechaacreditacion) {
		this.fechaacreditacion = fechaacreditacion;
	}

	/**
	 * Método que obtiene el valor del atributo fechasesion
	 * 
	 * @return fechasesion: valor del atributo fechasesion
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public Calendar getFechasesion() {
		return fechasesion;
	}

	/**
	 * Método que ingresa el valor del atributo fechasesion
	 * 
	 * @param fechasesion: valor del atributo fechasesion
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setFechasesion(Calendar fechasesion) {
		this.fechasesion = fechasesion;
	}

	/**
	 * Método que obtiene el valor del atributo claveelector
	 * 
	 * @return claveelector: valor del atributo claveelector
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public String getClaveelector() {
		return claveelector;
	}

	/**
	 * Método que ingresa el valor del atributo claveelector
	 * 
	 * @param claveelector: valor del atributo claveelector
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setClaveelector(String claveelector) {
		this.claveelector = claveelector;
	}

	/**
	 * Método que obtiene el valor del atributo sexo
	 * 
	 * @return sexo: valor del atributo sexo
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * Método que ingresa el valor del atributo sexo
	 * 
	 * @param sexo: valor del atributo sexo
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	/**
	 * Método que obtiene el valor del atributo nombreestado
	 * 
	 * @return nombreestado: valor del atributo nombreestado
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public String getNombreestado() {
		return nombreestado;
	}

	/**
	 * Método que ingresa el valor del atributo nombreestado
	 * 
	 * @param nombreestado: valor del atributo nombreestado
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setNombreestado(String nombreestado) {
		this.nombreestado = nombreestado;
	}

	/**
	 * Método que obtiene el valor del atributo acreditado
	 * 
	 * @return acreditado: valor del atributo acreditado
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public String getAcreditado() {
		return acreditado;
	}

	/**
	 * Método que ingresa el valor del atributo acreditado
	 * 
	 * @param acreditado: valor del atributo acreditado
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setAcreditado(String acreditado) {
		this.acreditado = acreditado;
	}

	/**
	 * Método que obtiene el valor del atributo idJustificacion
	 * 
	 * @return idJustificacion: valor del atributo idJustificacion
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public Integer getIdJustificacion() {
		return idJustificacion;
	}

	/**
	 * Método que ingresa el valor del atributo idJustificacion
	 * 
	 * @param idJustificacion: valor del atributo idJustificacion
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setIdJustificacion(Integer idJustificacion) {
		this.idJustificacion = idJustificacion;
	}

	/**
	 * Método que obtiene el valor del atributo estado
	 * 
	 * @return estado: valor del atributo estado
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public Integer getEstado() {
		return estado;
	}

	/**
	 * Método que ingresa el valor del atributo estado
	 * 
	 * @param estado: valor del atributo estado
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	/**
	 * Método que obtiene el valor del atributo distrito
	 * 
	 * @return distrito: valor del atributo distrito
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public Integer getDistrito() {
		return distrito;
	}

	/**
	 * Método que ingresa el valor del atributo distrito
	 * 
	 * @param distrito: valor del atributo distrito
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setDistrito(Integer distrito) {
		this.distrito = distrito;
	}

	/**
	 * Método que obtiene el valor del atributo sexoObs
	 * 
	 * @return sexoObs: valor del atributo sexoObs
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public String getSexoObs() {
		return sexoObs;
	}

	/**
	 * Método que ingresa el valor del atributo sexoObs
	 * 
	 * @param sexoObs: valor del atributo sexoObs
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setSexoObs(String sexoObs) {
		this.sexoObs = sexoObs;
	}
	
	

	/**
	 * Método que obtiene el valor del atributo sinFirma
	 * 
	 * @return sinFirma: valor del atributo sinFirma
	 * @author Pablo Zuñiga Mata.
	 * @since  10/07/2017
	 */
	public Boolean getSinFirma() {
		return sinFirma;
	}

	/**
	 * Método que ingresa el valor del atributo sinFirma
	 * 
	 * @param sinFirma: valor del atributo sinFirma
	 * @author Pablo Zuñiga Mata.
	 * @since  10/07/2017
	 */
	public void setSinFirma(Boolean sinFirma) {
		this.sinFirma = sinFirma;
	}

	/**
	 * Método que obtiene el valor del atributo conFirma
	 * 
	 * @return conFirma: valor del atributo conFirma
	 * @author Pablo Zuñiga Mata.
	 * @since  10/07/2017
	 */
	public Boolean getConFirma() {
		return conFirma;
	}

	/**
	 * Método que ingresa el valor del atributo conFirma
	 * 
	 * @param conFirma: valor del atributo conFirma
	 * @author Pablo Zuñiga Mata.
	 * @since  10/07/2017
	 */
	public void setConFirma(Boolean conFirma) {
		this.conFirma = conFirma;
	}

	

	/**
	 * Método que obtiene el valor del atributo fotoVocalPresidenteD
	 * 
	 * @return fotoVocalPresidenteD: valor del atributo fotoVocalPresidenteD
	 * @author Pablo Zuñiga Mata.
	 * @since  19/07/2017
	 */
	public String getFotoVocalPresidenteD() {
		return fotoVocalPresidenteD;
	}

	/**
	 * Método que ingresa el valor del atributo fotoVocalPresidenteD
	 * 
	 * @param fotoVocalPresidenteD: valor del atributo fotoVocalPresidenteD
	 * @author Pablo Zuñiga Mata.
	 * @since  19/07/2017
	 */
	public void setFotoVocalPresidenteD(String fotoVocalPresidenteD) {
		this.fotoVocalPresidenteD = fotoVocalPresidenteD;
	}

	/**
	 * Método que obtiene el valor del atributo fotoVocalSecretarioD
	 * 
	 * @return fotoVocalSecretarioD: valor del atributo fotoVocalSecretarioD
	 * @author Pablo Zuñiga Mata.
	 * @since  19/07/2017
	 */
	public String getFotoVocalSecretarioD() {
		return fotoVocalSecretarioD;
	}

	/**
	 * Método que ingresa el valor del atributo fotoVocalSecretarioD
	 * 
	 * @param fotoVocalSecretarioD: valor del atributo fotoVocalSecretarioD
	 * @author Pablo Zuñiga Mata.
	 * @since  19/07/2017
	 */
	public void setFotoVocalSecretarioD(String fotoVocalSecretarioD) {
		this.fotoVocalSecretarioD = fotoVocalSecretarioD;
	}

	/**
	 * Método que obtiene el valor del atributo fotoVocalPresidenteE
	 * 
	 * @return fotoVocalPresidenteE: valor del atributo fotoVocalPresidenteE
	 * @author Pablo Zuñiga Mata.
	 * @since  19/07/2017
	 */
	public String getFotoVocalPresidenteE() {
		return fotoVocalPresidenteE;
	}

	/**
	 * Método que ingresa el valor del atributo fotoVocalPresidenteE
	 * 
	 * @param fotoVocalPresidenteE: valor del atributo fotoVocalPresidenteE
	 * @author Pablo Zuñiga Mata.
	 * @since  19/07/2017
	 */
	public void setFotoVocalPresidenteE(String fotoVocalPresidenteE) {
		this.fotoVocalPresidenteE = fotoVocalPresidenteE;
	}

	/**
	 * Método que obtiene el valor del atributo fotoVocalSecretarioE
	 * 
	 * @return fotoVocalSecretarioE: valor del atributo fotoVocalSecretarioE
	 * @author Pablo Zuñiga Mata.
	 * @since  19/07/2017
	 */
	public String getFotoVocalSecretarioE() {
		return fotoVocalSecretarioE;
	}

	/**
	 * Método que ingresa el valor del atributo fotoVocalSecretarioE
	 * 
	 * @param fotoVocalSecretarioE: valor del atributo fotoVocalSecretarioE
	 * @author Pablo Zuñiga Mata.
	 * @since  19/07/2017
	 */
	public void setFotoVocalSecretarioE(String fotoVocalSecretarioE) {
		this.fotoVocalSecretarioE = fotoVocalSecretarioE;
	}


	/**
	 * Método que obtiene el valor del atributo idOservador
	 * 
	 * @return idOservador: valor del atributo idOservador
	 * @author Pablo Zuñiga Mata.
	 * @since  20/07/2017
	 */
	public Integer getIdOservador() {
		return idOservador;
	}

	/**
	 * Método que ingresa el valor del atributo idOservador
	 * 
	 * @param idOservador: valor del atributo idOservador
	 * @author Pablo Zuñiga Mata.
	 * @since  20/07/2017
	 */
	public void setIdOservador(Integer idOservador) {
		this.idOservador = idOservador;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((claveelector == null) ? 0 : claveelector.hashCode());
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
		HLPAcreditacionGafete other = (HLPAcreditacionGafete) obj;
		if (acreditado == null) {
			if (other.acreditado != null)
				return false;
		} else if (!acreditado.equals(other.acreditado))
			return false;
		if (apellidoMaterno == null) {
			if (other.apellidoMaterno != null)
				return false;
		} else if (!apellidoMaterno.equals(other.apellidoMaterno))
			return false;
		if (apellidoPaterno == null) {
			if (other.apellidoPaterno != null)
				return false;
		} else if (!apellidoPaterno.equals(other.apellidoPaterno))
			return false;
		if (claveelector == null) {
			if (other.claveelector != null)
				return false;
		} else if (!claveelector.equals(other.claveelector))
			return false;
		if (conFirma == null) {
			if (other.conFirma != null)
				return false;
		} else if (!conFirma.equals(other.conFirma))
			return false;
		if (distrito == null) {
			if (other.distrito != null)
				return false;
		} else if (!distrito.equals(other.distrito))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fechaacreditacion == null) {
			if (other.fechaacreditacion != null)
				return false;
		} else if (!fechaacreditacion.equals(other.fechaacreditacion))
			return false;
		if (fechasesion == null) {
			if (other.fechasesion != null)
				return false;
		} else if (!fechasesion.equals(other.fechasesion))
			return false;
		if (folio == null) {
			if (other.folio != null)
				return false;
		} else if (!folio.equals(other.folio))
			return false;
		if (foto == null) {
			if (other.foto != null)
				return false;
		} else if (!foto.equals(other.foto))
			return false;
		if (fotoVocalPresidenteD == null) {
			if (other.fotoVocalPresidenteD != null)
				return false;
		} else if (!fotoVocalPresidenteD.equals(other.fotoVocalPresidenteD))
			return false;
		if (fotoVocalPresidenteE == null) {
			if (other.fotoVocalPresidenteE != null)
				return false;
		} else if (!fotoVocalPresidenteE.equals(other.fotoVocalPresidenteE))
			return false;
		if (fotoVocalSecretarioD == null) {
			if (other.fotoVocalSecretarioD != null)
				return false;
		} else if (!fotoVocalSecretarioD.equals(other.fotoVocalSecretarioD))
			return false;
		if (fotoVocalSecretarioE == null) {
			if (other.fotoVocalSecretarioE != null)
				return false;
		} else if (!fotoVocalSecretarioE.equals(other.fotoVocalSecretarioE))
			return false;
		if (idJustificacion == null) {
			if (other.idJustificacion != null)
				return false;
		} else if (!idJustificacion.equals(other.idJustificacion))
			return false;
		if (idOservador == null) {
			if (other.idOservador != null)
				return false;
		} else if (!idOservador.equals(other.idOservador))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (nombreestado == null) {
			if (other.nombreestado != null)
				return false;
		} else if (!nombreestado.equals(other.nombreestado))
			return false;
		if (sexo == null) {
			if (other.sexo != null)
				return false;
		} else if (!sexo.equals(other.sexo))
			return false;
		if (sexoObs == null) {
			if (other.sexoObs != null)
				return false;
		} else if (!sexoObs.equals(other.sexoObs))
			return false;
		if (sinFirma == null) {
			if (other.sinFirma != null)
				return false;
		} else if (!sinFirma.equals(other.sinFirma))
			return false;
		if (vocalPresidenteD == null) {
			if (other.vocalPresidenteD != null)
				return false;
		} else if (!vocalPresidenteD.equals(other.vocalPresidenteD))
			return false;
		if (vocalPresidenteE == null) {
			if (other.vocalPresidenteE != null)
				return false;
		} else if (!vocalPresidenteE.equals(other.vocalPresidenteE))
			return false;
		if (vocalSecretarioD == null) {
			if (other.vocalSecretarioD != null)
				return false;
		} else if (!vocalSecretarioD.equals(other.vocalSecretarioD))
			return false;
		if (vocalSecretarioE == null) {
			if (other.vocalSecretarioE != null)
				return false;
		} else if (!vocalSecretarioE.equals(other.vocalSecretarioE))
			return false;
		return true;
	}	
	
}
