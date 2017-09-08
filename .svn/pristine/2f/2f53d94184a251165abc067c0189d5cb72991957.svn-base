/**
 * @(#)ASGenericInterface.java 07/08/2017
 * <p>
 * Copyright (C) 2017 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.as;

import java.util.List;

import mx.ine.observadoresINE.dto.db.DTOCImagenes;
import mx.ine.observadoresINE.dto.db.DTOCTextos;

/**
 * <code>ASGenericInterface.java</code> 
 *
 * @author Pablo Zuñiga Mata
 * @since 07/08/2017
 * @copyright Direccion de sistemas - INE
 */
public interface ASGenericInterface {
	
	/**
	 * Método que obtiene la información de los textos
	 * 
	 * @author Pablo Zuñiga Mata
	 * @param idProceso
	 * @param idDetalle
	 * @param idParrafo
	 * @param idTexto
	 * @return
	 * @throws Exception
	 * @since 07/08/2017
	 */
	public List<DTOCTextos> obtenTextos(Integer idProceso, Integer idDetalle, Integer idParrafo,
			Integer idTexto)  throws Exception;
	
	/**
	 * Método que obtiene la ruta de las imagenes que se utilizaran
	 * 
	 * @author Pablo Zuñiga Mata
	 * @param idProceso
	 * @param idDetalle
	 * @param idImagen
	 * @return
	 * @throws Exception
	 * @since 07/08/2017
	 */
	public List<DTOCImagenes> obtenRutasIMG(Integer idProceso, Integer idDetalle, List<Integer> listIdImagen)  throws Exception;

}
