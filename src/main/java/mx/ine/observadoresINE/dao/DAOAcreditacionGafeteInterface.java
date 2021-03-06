/**
 * @(#)DAOAcreditacionGafeteInterface.java 12/09/2016
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.dao;

import java.util.List;

import mx.ine.observadoresINE.dto.db.DTOAcreditacionGafetes;
import mx.ine.observadoresINE.dto.form.FormAcreditacionGafete;
import mx.ine.observadoresINE.helper.HLPAcreditacionGafete;
import mx.ine.observadoresINE.helper.HLPAcreditacionesGafeteAutocomplete;

/**
 * <code>DAOAcreditacionGafeteInterface.java</code> 
 *
 * @author Pablo Zuñiga Mata
 * @version 0.0.1
 * @since 12/09/2016
 */
public interface DAOAcreditacionGafeteInterface {

	/**
	 * Método que lleva a cabo la búsqueda de un observador ya sea por su nombre y/o apellidos 
	 * para un componente autocomplete
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 12/09/2016
	 * 
	 * @param proceso : identificador del proceso
	 * @param detalle : identificador del detalle del proceso
	 * @param idEstado : estado especifico de la busqueda
	 * @param idDistrito : ditrito especifico de la busqueda
	 * @param cadena : nombre que buscará de acuerdo a la concatenación del nombre y apellidos
	 * @return List<HLPAcreditacionesGafeteAutocomplete> : lista de observadores encontrados
	 */
	public List<HLPAcreditacionesGafeteAutocomplete> getObservadoresByNombreApellidosLike( 
			Integer proceso, Integer detalle, Integer idEstado, Integer idDistrito, String cadena) throws Exception;
	
	/**
	 * Método que lleva a cabo la búsqueda de las agrupaciones por su nombre 
	 * para un componente autocomplete
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 12/09/2016
	 * 
	 * @param proceso : identificador del proceso
	 * @param detalle : identificador del detalle del proceso
	 * @param idEstado : estado especifico de la busqueda
	 * @param idDistrito : ditrito especifico de la busqueda
	 * @param cadena : nombre que buscará de acuerdo a la concatenación del nombre y apellidos
	 * @return List<HLPAcreditacionesGafeteAutocomplete> : lista de observadores encontrados
	 */
	public List<HLPAcreditacionesGafeteAutocomplete> getObservadoresByNombreAgrupacionLike( 
			Integer proceso, Integer detalle, Integer idEstado, Integer idDistrito, String cadena) throws Exception;
	
	/**
	 * Método que valida si existen Consejeros presidente para generar las acreditaciones y/o gafetes
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 12/09/2016
	 * 
	 * @param dtoFiltro : DTO del filtro
	 * @return String : mensaje de acuerdo a la busqueda
	 */
	public String getConfirmaExisteVocal(FormAcreditacionGafete dtoFiltro) throws Exception;
	
	/**
	 * Método que obtiene la información de la(s) acreditación(es) o el/los gafete(s)
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 12/09/2016
	 * 
	 * @param dtoFiltro : DTO del filtro
	 * @return List<HLPAcreditacionGafete> : lista de observadores encontrados
	 */
	public List<HLPAcreditacionGafete> getInfoAcreditacionGafete(FormAcreditacionGafete dtoFiltro) throws Exception;
	

	/**
	 * Elimina los datos de la tabla Acreditacion_Gafetes.
	 * @param dto
	 * @throws Exception
	 */
	public void elimina(DTOAcreditacionGafetes acreGafete) throws Exception;
	
	/**
	 * Agrega los datos de la tabla Acreditacion_Gafetes.
	 * @param dto
	 * @throws Exception
	 */
	public void guarda(DTOAcreditacionGafetes acreGafete) throws Exception;

	/**
	 * Modifica los datos de la tabla Acreditacion_Gafetes.
	 * @param dto
	 * @throws Exception
	 */
	public void modifica(DTOAcreditacionGafetes acreGafete) throws Exception ;
	
}
