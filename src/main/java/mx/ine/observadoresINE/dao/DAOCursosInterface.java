 /**
 * @(#)DAOCursosInterface.java 29/06/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.dao;

import java.util.List;

import mx.ine.observadoresINE.dto.db.DTOAgrupaciones;
import mx.ine.observadoresINE.dto.db.DTOCCargoResponsable;
import mx.ine.observadoresINE.dto.db.DTOCursos;
import mx.ine.observadoresINE.dto.db.DTOObservadores;
import mx.ine.observadoresINE.dto.form.FormCursos;

 /**
 * Objeto de acceso a los datos para el módulo de Cursos
 * 
 * @author Emmanuel García Ysamit
 * @since 29/06/2017
 * @copyright Direccion de sistemas - INE
 */
public interface DAOCursosInterface {
	
	/**
	 * Método encargado de guardar/actualizar un curso 
	 * 
	 * @param dto
	 * @throws Exception
	 */
	public void guardarOActualizar(DTOCursos dto) throws Exception;
	
	/**
	 * Método encargado de eliminar un curso
	 * 
	 * @param dto
	 * @throws Exception
	 */
	public void eliminaCurso(DTOCursos dto) throws Exception;
	
	/**
	 * Método que verifica si ya existe un observador asignado a un curso
	 * 
	 * @author Emmanuel García Ysamit
	 * @param dto
	 * @return
	 * @throws Exception
	 * @since 26/07/2017
	 */
	public List<DTOObservadores> verificaObservador(Integer idCurso) throws Exception;
	
	/**
	 * Método encargado de obtener los cursos
	 * 
	 * @throws Exception
	 */
	public List<DTOCursos> obtenCursos(Integer idProceso, Integer idDetalleProceso) throws Exception;
	
	/**
	 * Método que regresa los cargos del catálogo de cargos
	 * 
	 * @throws Exception
	 */
	public List<DTOCCargoResponsable> obtenCargos(Integer idProceso, Integer idDetalle) throws Exception;
	
	/**Método que regresa una lista con las agrupaciones encontradas
	 * 
	 * @trhows Exception
	 */
	public List<DTOAgrupaciones> getAgrupacionesByNombre(String cadena, Integer idProceso, Integer idDetalle) throws Exception;
	
	/**
	 * Método que busca los cursos por fecha e impartición
	 * 
	 * @author Emmanuel García Ysamit
	 * @param formCursos
	 * @return cursos encontrados
	 * @throws Exception
	 * @since 07/07/2017
	 */
	public List<DTOCursos> buscaPorFecha(FormCursos formCursos, Integer nivelOficinas) throws Exception;
	
	/**
	 * Método que busca los cursos por impartición
	 * 
	 * @author Emmanuel García Ysamit
	 * @param formCursos
	 * @return cursos encontrados
	 * @throws Exception
	 * @since 07/07/2017
	 */
	public List<DTOCursos> buscaImparte(FormCursos formCursos, Integer nivelOficinas) throws Exception;

	 /**
	 * Método que obtiene el domicilio de la junta a partir del estado y domicilio del usuario logeado
	 * 
	 * @author Emmanuel García Ysamit
	 * @param idEstado
	 * @param idDistrito
	 * @return
	 * @since 24/07/2017
	 */
	public DTOCursos obtenDomicilioDeLaJunta(Integer idEstado, Integer idDistrito) throws Exception;
}
