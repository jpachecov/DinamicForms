/**
 * @(#)ASAcreditacionGafeteImpl.java 30/09/2016
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.as.impl;

import java.util.List;

import mx.ine.observadoresINE.as.ASAcreditacionGafeteInterface;
import mx.ine.observadoresINE.dao.DAOAcreditacionGafeteInterface;
import mx.ine.observadoresINE.dao.DAOCImagenesInterface;
import mx.ine.observadoresINE.dao.DAOCTextosInterface;
import mx.ine.observadoresINE.dto.db.DTOAcreditacionGafetes;
import mx.ine.observadoresINE.dto.db.DTOAcreditacionGafetesPK;
import mx.ine.observadoresINE.dto.db.DTOCImagenes;
import mx.ine.observadoresINE.dto.db.DTOCTextos;
import mx.ine.observadoresINE.dto.form.FormAcreditacionGafete;
import mx.ine.observadoresINE.helper.HLPAcreditacionGafete;
import mx.ine.observadoresINE.helper.HLPAcreditacionesGafeteAutocomplete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <code>ASAcreditacionGafeteImpl.java</code> 
 *
 * @author Pablo Zuñiga Mata
 * @version 0.0.1
 * @since 30/09/2016
 */
@Service("asAcreditacionGafeteImpl")
@Scope("prototype")
@Transactional(readOnly= false)
public class ASAcreditacionGafeteImpl implements ASAcreditacionGafeteInterface{

	@Autowired
	@Qualifier("daoAcreditacionGafeteImpl")
	private DAOAcreditacionGafeteInterface daoAcreditacionGafete;
	
	@Autowired
	@Qualifier("daoCTextos")
	private DAOCTextosInterface daoCTextos;
	
	@Autowired
	@Qualifier("daoCImagenes")
	private DAOCImagenesInterface daoCImagenes;
	
	/*
	 * (non-Javadoc)
	 * @see mx.ine.observadoresINE.as.ASAcreditacionGafeteInterface#getObservadoresByNombreApellidosLike(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = {Exception.class})
	public List<HLPAcreditacionesGafeteAutocomplete> getObservadoresByNombreApellidosLike(
			Integer proceso, Integer detalle, Integer estado, Integer distrito , String nombre)  throws Exception{
		return daoAcreditacionGafete.getObservadoresByNombreApellidosLike(proceso, detalle, estado, distrito, nombre);
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.ine.observadoresINE.as.ASAcreditacionGafeteInterface#getObservadoresByNombreAgrupacionLike(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = {Exception.class})
	public List<HLPAcreditacionesGafeteAutocomplete> getObservadoresByNombreAgrupacionLike(
			Integer proceso, Integer detalle, Integer estado, Integer distrito , String nombre)  throws Exception{
		return daoAcreditacionGafete.getObservadoresByNombreAgrupacionLike(proceso, detalle, estado, distrito, nombre);
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.ine.observadoresINE.bsd.BSDAcreditacionGafeteInterface#getConfirmaExisteVocal(mx.ine.observadoresINE.dto.DTOFiltroAcreditacionGafete)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = {Exception.class})
	public String getConfirmaExisteVocal(FormAcreditacionGafete dtoFiltro)  throws Exception{
		return daoAcreditacionGafete.getConfirmaExisteVocal(dtoFiltro);
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.ine.observadoresINE.bsd.BSDAcreditacionGafeteInterface#getInfoAcreditacionGafete(mx.ine.observadoresINE.dto.DTOFiltroAcreditacionGafete)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = {Exception.class})
	public List<HLPAcreditacionGafete> getInfoAcreditacionGafete(FormAcreditacionGafete dtoFiltro) throws Exception{
		return daoAcreditacionGafete.getInfoAcreditacionGafete(dtoFiltro);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Exception.class})
	public void guarda(DTOAcreditacionGafetes acreGafete) throws Exception {
		daoAcreditacionGafete.guarda(acreGafete);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Exception.class})
	public void modifica(DTOAcreditacionGafetes acreGafete) throws Exception {
		daoAcreditacionGafete.modifica(acreGafete);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Exception.class})
	public void elimina(DTOAcreditacionGafetes acreGafete) throws Exception {
		daoAcreditacionGafete.elimina(acreGafete);
	}
}
