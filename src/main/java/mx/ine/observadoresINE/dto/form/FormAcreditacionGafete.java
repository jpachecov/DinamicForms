/**
 * @(#)DTOFiltroAcreditacionGafete.java 20/09/2016
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.dto.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mx.ine.observadoresINE.dto.db.DTOCImagenes;
import mx.ine.observadoresINE.dto.db.DTOCTextos;

/**
 * <code>DTOFiltroAcreditacionGafete.java</code> 
 *
 * @author Pablo Zuñiga Mata
 * @version 0.0.1
 * @since 20/09/2016
 */
public class FormAcreditacionGafete implements Serializable{

	/**
	 * Elemento necesario para la serialización de los objetos generados de esta clase.
	 */
	private static final long serialVersionUID = -3184449353747392058L;
	
	private Integer idProceso;
	
	private Integer idDetalleProceso;
	
	private Integer idEstado;
	
	private Integer idDistrito;
	
	private Integer tipoBusqueda;
	
	private Integer idBusqueda;
	
	private Integer acreditacionGafete;
	
	private Calendar fechaExpedicion;
	
	private Boolean porFecha;
	
	private Calendar inicioFecha;
	
	private Calendar finFecha;
	
	private List<DTOCTextos> listaTextos;
	
	private List<DTOCImagenes> listaRutasIMG;
	
	private String fechaVigencia;
	
	private String nombreProceso;
	
	private String rutaFirma;
	
	private String ambito;
	

    /**
     * Ruta de las fotos
     */
    protected String rutaFoto;
    
    /**
     * Ruta de la foto en caso de que no exista foto del observador
     */
    protected String rutaFotoSuplente;
	
	public FormAcreditacionGafete(){
		this.idProceso = null;
		this.idDetalleProceso = null;
		this.idEstado = null;
		this.idDistrito = null;
		this.tipoBusqueda = null;
		this.idBusqueda = null;
		this.acreditacionGafete = null;
		this.fechaExpedicion = null;
		this.porFecha = false;
		this.inicioFecha = null;
		this.finFecha = null;
		this.listaTextos = new ArrayList<DTOCTextos>();
		this.fechaVigencia = null;
		this.nombreProceso = null;
		this.rutaFirma = null;
	}

	/**
	 * Método que obtiene el valor del atributo tipoBusqueda
	 * 
	 * @return tipoBusqueda: valor del atributo tipoBusqueda
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public Integer getTipoBusqueda() {
		return tipoBusqueda;
	}

	/**
	 * Método que ingresa el valor del atributo tipoBusqueda
	 * 
	 * @param tipoBusqueda: valor del atributo tipoBusqueda
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setTipoBusqueda(Integer tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	/**
	 * Método que obtiene el valor del atributo idBusqueda
	 * 
	 * @return idBusqueda: valor del atributo idBusqueda
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public Integer getIdBusqueda() {
		return idBusqueda;
	}

	/**
	 * Método que ingresa el valor del atributo idBusqueda
	 * 
	 * @param idBusqueda: valor del atributo idBusqueda
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setIdBusqueda(Integer idBusqueda) {
		this.idBusqueda = idBusqueda;
	}

	/**
	 * Método que obtiene el valor del atributo acreditacionGafete
	 * 
	 * @return acreditacionGafete: valor del atributo acreditacionGafete
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public Integer getAcreditacionGafete() {
		return acreditacionGafete;
	}

	/**
	 * Método que ingresa el valor del atributo acreditacionGafete
	 * 
	 * @param acreditacionGafete: valor del atributo acreditacionGafete
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setAcreditacionGafete(Integer acreditacionGafete) {
		this.acreditacionGafete = acreditacionGafete;
	}

	/**
	 * Método que obtiene el valor del atributo fechaExpedicion
	 * 
	 * @return fechaExpedicion: valor del atributo fechaExpedicion
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public Calendar getFechaExpedicion() {
		return fechaExpedicion;
	}

	/**
	 * Método que ingresa el valor del atributo fechaExpedicion
	 * 
	 * @param fechaExpedicion: valor del atributo fechaExpedicion
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setFechaExpedicion(Calendar fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}

	/**
	 * Método que obtiene el valor del atributo porFecha
	 * 
	 * @return porFecha: valor del atributo porFecha
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public Boolean getPorFecha() {
		return porFecha;
	}

	/**
	 * Método que ingresa el valor del atributo porFecha
	 * 
	 * @param porFecha: valor del atributo porFecha
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setPorFecha(Boolean porFecha) {
		this.porFecha = porFecha;
	}

	/**
	 * Método que obtiene el valor del atributo inicioFecha
	 * 
	 * @return inicioFecha: valor del atributo inicioFecha
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public Calendar getInicioFecha() {
		return inicioFecha;
	}

	/**
	 * Método que ingresa el valor del atributo inicioFecha
	 * 
	 * @param inicioFecha: valor del atributo inicioFecha
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setInicioFecha(Calendar inicioFecha) {
		this.inicioFecha = inicioFecha;
	}

	/**
	 * Método que obtiene el valor del atributo finFecha
	 * 
	 * @return finFecha: valor del atributo finFecha
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public Calendar getFinFecha() {
		return finFecha;
	}

	/**
	 * Método que ingresa el valor del atributo finFecha
	 * 
	 * @param finFecha: valor del atributo finFecha
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setFinFecha(Calendar finFecha) {
		this.finFecha = finFecha;
	}

	/**
	 * Método que obtiene el valor del atributo idEstado
	 * 
	 * @return idEstado: valor del atributo idEstado
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public Integer getIdEstado() {
		return idEstado;
	}

	/**
	 * Método que ingresa el valor del atributo idEstado
	 * 
	 * @param idEstado: valor del atributo idEstado
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	/**
	 * Método que obtiene el valor del atributo idDistrito
	 * 
	 * @return idDistrito: valor del atributo idDistrito
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public Integer getIdDistrito() {
		return idDistrito;
	}

	/**
	 * Método que ingresa el valor del atributo idDistrito
	 * 
	 * @param idDistrito: valor del atributo idDistrito
	 * @author Pablo Zuñiga Mata.
	 * @since  20/09/2016
	 */
	public void setIdDistrito(Integer idDistrito) {
		this.idDistrito = idDistrito;
	}

	/**
	 * Método que obtiene el valor de el atributo idProceso
	 *
	 * @return Date : valor que tiene el atributo idProceso
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 27/06/2017
	 */
	public Integer getIdProceso() {
		return idProceso;
	}

	/**
	 * Método que ingresa el valor de el atributo idProceso
	 *
	 * @param fechaAprobacionCasillaJunta : valor que ingresa a el atributo idProceso
	 *
	 * @author Pablo Zuñiga Mata
	 * @since 27/06/2017
	 */
	public void setIdProceso(Integer idProceso) {
		this.idProceso = idProceso;
	}

	/**
	 * Método que obtiene el valor de el atributo idDetalleProceso
	 *
	 * @return Date : valor que tiene el atributo idDetalleProceso
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 27/06/2017
	 */
	public Integer getIdDetalleProceso() {
		return idDetalleProceso;
	}

	/**
	 * Método que ingresa el valor de el atributo idDetalleProceso
	 *
	 * @param fechaAprobacionCasillaJunta : valor que ingresa a el atributo idDetalleProceso
	 *
	 * @author Pablo Zuñiga Mata
	 * @since 27/06/2017
	 */
	public void setIdDetalleProceso(Integer idDetalleProceso) {
		this.idDetalleProceso = idDetalleProceso;
	}

	/**
	 * Método que obtiene el valor del atributo listaTextos
	 * 
	 * @return listaTextos: valor del atributo listaTextos
	 * @author Pablo Zuñiga Mata.
	 * @since  05/07/2017
	 */
	public List<DTOCTextos> getListaTextos() {
		return listaTextos;
	}

	/**
	 * Método que ingresa el valor del atributo listaTextos
	 * 
	 * @param listaTextos: valor del atributo listaTextos
	 * @author Pablo Zuñiga Mata.
	 * @since  05/07/2017
	 */
	public void setListaTextos(List<DTOCTextos> listaTextos) {
		this.listaTextos = listaTextos;
	}

	/**
	 * Método que obtiene el valor del atributo fechaVigencia
	 * 
	 * @return fechaVigencia: valor del atributo fechaVigencia
	 * @author Pablo Zuñiga Mata.
	 * @since  10/07/2017
	 */
	public String getFechaVigencia() {
		return fechaVigencia;
	}

	/**
	 * Método que ingresa el valor del atributo fechaVigencia
	 * 
	 * @param fechaVigencia: valor del atributo fechaVigencia
	 * @author Pablo Zuñiga Mata.
	 * @since  10/07/2017
	 */
	public void setFechaVigencia(String fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	/**
	 * Método que obtiene el valor del atributo nombreProceso
	 * 
	 * @return nombreProceso: valor del atributo nombreProceso
	 * @author Pablo Zuñiga Mata.
	 * @since  10/07/2017
	 */
	public String getNombreProceso() {
		return nombreProceso;
	}

	/**
	 * Método que ingresa el valor del atributo nombreProceso
	 * 
	 * @param nombreProceso: valor del atributo nombreProceso
	 * @author Pablo Zuñiga Mata.
	 * @since  10/07/2017
	 */
	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}

	/**
	 * Método que obtiene el valor del atributo rutaFirma
	 * 
	 * @return rutaFirma: valor del atributo rutaFirma
	 * @author Pablo Zuñiga Mata.
	 * @since  10/07/2017
	 */
	public String getRutaFirma() {
		return rutaFirma;
	}

	/**
	 * Método que ingresa el valor del atributo rutaFirma
	 * 
	 * @param rutaFirma: valor del atributo rutaFirma
	 * @author Pablo Zuñiga Mata.
	 * @since  10/07/2017
	 */
	public void setRutaFirma(String rutaFirma) {
		this.rutaFirma = rutaFirma;
	}

	/**
	 * Método que obtiene el valor del atributo listaRutasIMG
	 * 
	 * @return listaRutasIMG: valor del atributo listaRutasIMG
	 * @author Pablo Zuñiga Mata.
	 * @since  18/07/2017
	 */
	public List<DTOCImagenes> getListaRutasIMG() {
		return listaRutasIMG;
	}

	/**
	 * Método que ingresa el valor del atributo listaRutasIMG
	 * 
	 * @param listaRutasIMG: valor del atributo listaRutasIMG
	 * @author Pablo Zuñiga Mata.
	 * @since  18/07/2017
	 */
	public void setListaRutasIMG(List<DTOCImagenes> listaRutasIMG) {
		this.listaRutasIMG = listaRutasIMG;
	}

	/**
	 * Método que obtiene el valor del atributo ambito
	 * 
	 * @return ambito: valor del atributo ambito
	 * @author Pablo Zuñiga Mata.
	 * @since  20/07/2017
	 */
	public String getAmbito() {
		return ambito;
	}

	/**
	 * Método que ingresa el valor del atributo ambito
	 * 
	 * @param ambito: valor del atributo ambito
	 * @author Pablo Zuñiga Mata.
	 * @since  20/07/2017
	 */
	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	/**
	 * Método que obtiene el valor del atributo rutaFoto
	 * 
	 * @return rutaFoto: valor del atributo rutaFoto
	 * @author Pablo Zuñiga Mata.
	 * @since  09/08/2017
	 */
	public String getRutaFoto() {
		return rutaFoto;
	}

	/**
	 * Método que ingresa el valor del atributo rutaFoto
	 * 
	 * @param rutaFoto: valor del atributo rutaFoto
	 * @author Pablo Zuñiga Mata.
	 * @since  09/08/2017
	 */
	public void setRutaFoto(String rutaFoto) {
		this.rutaFoto = rutaFoto;
	}

	/**
	 * Método que obtiene el valor del atributo rutaFotoSuplente
	 * 
	 * @return rutaFotoSuplente: valor del atributo rutaFotoSuplente
	 * @author Pablo Zuñiga Mata.
	 * @since  11/08/2017
	 */
	public String getRutaFotoSuplente() {
		return rutaFotoSuplente;
	}

	/**
	 * Método que ingresa el valor del atributo rutaFotoSuplente
	 * 
	 * @param rutaFotoSuplente: valor del atributo rutaFotoSuplente
	 * @author Pablo Zuñiga Mata.
	 * @since  11/08/2017
	 */
	public void setRutaFotoSuplente(String rutaFotoSuplente) {
		this.rutaFotoSuplente = rutaFotoSuplente;
	}
	
}
