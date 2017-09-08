/**
 * @(#)ASAcreditacionGafeteInterface.java 12/09/2016
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.as;

import java.util.List;

import mx.ine.observadoresINE.dto.db.DTOAcreditacionGafetes;
import mx.ine.observadoresINE.dto.db.DTOCImagenes;
import mx.ine.observadoresINE.dto.db.DTOCTextos;
import mx.ine.observadoresINE.dto.form.FormAcreditacionGafete;
import mx.ine.observadoresINE.helper.HLPAcreditacionGafete;
import mx.ine.observadoresINE.helper.HLPAcreditacionesGafeteAutocomplete;

/**
 * <code>ASAcreditacionGafeteInterface.java</code> 
 *
 * @author Pablo Zuñiga Mata
 * @version 0.0.1
 * @since 12/09/2016
 */
public interface ASAcreditacionGafeteInterface {

	/**
	 * Método que lleva a cabo la búsqueda de un observador ya sea por su nombre y/o apellidos 
	 * para un componente autocomplete
	 * 
	 * @param proceso : identificador del proceso
	 * @param detalle : identificador del detalle del proceso	 
	 * @param estado : estado especifico de la busqueda
	 * @param distrito : ditrito especifico de la busqueda
	 * @param nombre : nombre que buscará de acuerdo a la concatenación del nombre y apellidos
	 * @return List<HLPAcreditacionesGafeteAutocomplete> : lista de observadores encontrados
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 12/09/2016  
	 */
	public List<HLPAcreditacionesGafeteAutocomplete> getObservadoresByNombreApellidosLike(
			Integer proceso, Integer detalle, Integer estado, Integer distrito , String nombre) throws Exception;
	
	/**
	 * Método que lleva a cabo la búsqueda de las agrupaciones por su nombre 
	 * para un componente autocomplete
	 * 
	 * @param proceso : identificador del proceso
	 * @param detalle : identificador del detalle del proceso	 
	 * @param estado : estado especifico de la busqueda
	 * @param distrito : ditrito especifico de la busqueda
	 * @param nombre : nombre que buscará de acuerdo a la concatenación del nombre y apellidos
	 * @return List<HLPAcreditacionesGafeteAutocomplete> : lista de observadores encontrados
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 12/09/2016  
	 */
	public List<HLPAcreditacionesGafeteAutocomplete> getObservadoresByNombreAgrupacionLike(
			Integer proceso, Integer detalle, Integer estado, Integer distrito , String nombre) throws Exception;
	
	/**
	 * Método que valida si existen Consejeros presidente para generar las acreditaciones y/o gafetes
	 * para un componente autocomplete
	 * 	 
	 * @param dtoFiltro : filtro seleccionado por el usuario
	 * @return String : mensaje de error si es que no existe vocal o "" si existe vocal
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 12/09/2016  
	 */
	public String getConfirmaExisteVocal(FormAcreditacionGafete dtoFiltro) throws Exception;
	
	/**
	 * Método que obtiene la información de la(s) acreditación(es) o el/los gafete(s)
	 * 	 
	 * @param dtoFiltro : filtro seleccionado por el usuario
	 * @return List<HLPAcreditacionGafete> : lista de la informacion de los observadores encontrados
	 * 
	 * @author Pablo Zuñiga Mata
	 * @since 12/09/2016  
	 */
	public List<HLPAcreditacionGafete> getInfoAcreditacionGafete(FormAcreditacionGafete dtoFiltro) throws Exception;
	
	/**
	 * Agrega los datos de la tabla Acreditacion_Gafetes.
	 * @param dto
	 * @throws Exception
	 */
	public void guarda(DTOAcreditacionGafetes acreGafete) throws Exception ;

	/**
	 * Modifica los datos de la tabla Acreditacion_Gafetes.
	 * @param dto
	 * @throws Exception
	 */
	public void modifica(DTOAcreditacionGafetes acreGafete) throws Exception ;
	/**
	 * Elimina los datos de la tabla Acreditacion_Gafetes.
	 * @param dto
	 * @throws Exception
	 */
	public void elimina(DTOAcreditacionGafetes acreGafete) throws Exception;
	
}
