/**
 * @(#)DAOCTextos.java 18/07/2017
 * <p>
 * Copyright (C) 2017 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.dao;

import java.util.List;

import mx.ine.observadoresINE.dto.db.DTOCTextos;
import mx.ine.observadoresINE.dto.form.FormAcreditacionGafete;

/**
 * <code>DAOCTextos.java</code> 
 *
 * @author Pablo Zuñiga Mata
 * @since 18/07/2017
 * @copyright Direccion de sistemas - INE
 */
public interface DAOCTextosInterface {
	
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
			Integer idTexto) throws Exception;

}
