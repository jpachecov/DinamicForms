 /**
 * @(#)BSDCursosImpl.java 29/06/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.bsd.impl;

import java.util.List;

import mx.ine.observadoresINE.as.ASCursosInterface;
import mx.ine.observadoresINE.bsd.BSDCursosInterface;
import mx.ine.observadoresINE.dto.db.DTOAgrupaciones;
import mx.ine.observadoresINE.dto.db.DTOCCargoResponsable;
import mx.ine.observadoresINE.dto.db.DTOCursos;
import mx.ine.observadoresINE.dto.form.FormCursos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

 /**
 * 
 * @author Emmanuel García Ysamit
 * @since 29/06/2017
 * @copyright Direccion de sistemas - INE
 */
@Component("bsdCursos")
@Scope("prototype")
public class BSDCursosImpl implements BSDCursosInterface {

	@Autowired
	@Qualifier("asCursos")
	ASCursosInterface asCursos;
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public void guardarOActualizar(DTOCursos dto) throws Exception {
		asCursos.guardarOActualizar(dto);
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public void eliminaCurso(DTOCursos dto) throws Exception {
		asCursos.eliminaCurso(dto);
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public List<DTOCursos> obtenCursos(Integer idProceso, Integer idDetalleProceso) throws Exception {
		return asCursos.obtenCursos(idProceso, idDetalleProceso);		
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public List<DTOCCargoResponsable> obtenCargos(Integer idProceso, Integer idDetalle) throws Exception {
		return asCursos.obtenCargos(idProceso, idDetalle);
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public List<String> getAgrupacionesByNombre(String cadena, Integer idProceso, Integer idDetalle)
			throws Exception {
		return asCursos.getAgrupacionesByNombre(cadena, idProceso, idDetalle);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean esNombreValido(DTOCursos dto) throws Exception {
		return asCursos.esNombreValido(dto);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean sonApellidosInvalidos(DTOCursos dto) throws Exception {
		return asCursos.sonApellidosInvalidos(dto);
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public List<DTOCursos> buscaPorFecha(FormCursos formCursos, Integer nivelOficinas)
			throws Exception {
		return asCursos.buscaPorFecha(formCursos, nivelOficinas);
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public List<DTOCursos> buscaImparte(FormCursos formCursos, Integer nivelOficinas) throws Exception {
		return asCursos.buscaImparte(formCursos, nivelOficinas);
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public boolean obtenDomicilioDeLaJunta(DTOCursos dto) {
		return asCursos.obtenDomicilioDeLaJunta(dto);
	}

	/**
	* {@inheritDoc}
	*/
	@Override
	public boolean verificaObservador(DTOCursos dto) throws Exception {
		return asCursos.verificaObservador(dto);
	}

}
