/**
 * @(#)BSDAcreditacionGafeteImpl.java 30/09/2016
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.bsd.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.observadoresINE.as.ASAcreditacionGafeteInterface;
import mx.ine.observadoresINE.bsd.BSDAcreditacionGafeteInterface;
import mx.ine.observadoresINE.dto.db.DTOAcreditacionGafetes;
import mx.ine.observadoresINE.dto.form.FormAcreditacionGafete;
import mx.ine.observadoresINE.helper.HLPAcreditacionGafete;
import mx.ine.observadoresINE.helper.HLPAcreditacionesGafeteAutocomplete;

/**
 * <code>BSDAcreditacionGafeteImpl.java</code> 
 *
 * @author Pablo Zuñiga Mata
 * @version 0.0.1
 * @since 30/09/2016
 */
@Component("bsdAcreditacionGafeteImpl")
@Scope("prototype")
public class BSDAcreditacionGafeteImpl implements BSDAcreditacionGafeteInterface{

	@Autowired
	@Qualifier("asAcreditacionGafeteImpl")
	private ASAcreditacionGafeteInterface asAcreditacionGafeteInterface;
	
	/*
	 * (non-Javadoc)
	 * @see mx.ine.observadoresINE.bsd.BSDAcreditacionGafeteInterface#getObservadoresByNombreApellidosLike(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public List<HLPAcreditacionesGafeteAutocomplete> getObservadoresByNombreApellidosLike(
			Integer proceso, Integer detalle, Integer estado, Integer distrito , String nombre) throws Exception {
		return asAcreditacionGafeteInterface.getObservadoresByNombreApellidosLike(proceso, detalle, estado, distrito, nombre);
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.ine.observadoresINE.bsd.BSDAcreditacionGafeteInterface#getObservadoresByNombreAgrupacionLike(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public List<HLPAcreditacionesGafeteAutocomplete> getObservadoresByNombreAgrupacionLike(
			Integer proceso, Integer detalle, Integer estado, Integer distrito , String nombre)  throws Exception{
		return asAcreditacionGafeteInterface.getObservadoresByNombreAgrupacionLike(proceso, detalle, estado, distrito, nombre);
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.ine.observadoresINE.bsd.BSDAcreditacionGafeteInterface#getConfirmaExisteVocal(mx.ine.observadoresINE.dto.DTOFiltroAcreditacionGafete)
	 */
	@Override
	public String getConfirmaExisteVocal(FormAcreditacionGafete dtoFiltro) throws Exception {
		return asAcreditacionGafeteInterface.getConfirmaExisteVocal(dtoFiltro);
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.ine.observadoresINE.bsd.BSDAcreditacionGafeteInterface#getInfoAcreditacionGafete(mx.ine.observadoresINE.dto.DTOFiltroAcreditacionGafete)
	 */
	@Override
	public List<HLPAcreditacionGafete> getInfoAcreditacionGafete(FormAcreditacionGafete dtoFiltro) throws Exception {
		return asAcreditacionGafeteInterface.getInfoAcreditacionGafete(dtoFiltro);
	}

	/* (non-Javadoc)
	 * @see mx.ine.observadoresINE.bsd.BSDAcreditacionGafeteInterface#guarda(mx.ine.observadoresINE.dto.db.DTOAcreditacionGafetes)
	 */
	@Override
	public void guarda(DTOAcreditacionGafetes acreGafete) throws Exception {
		asAcreditacionGafeteInterface.guarda(acreGafete);
	}

	/* (non-Javadoc)
	 * @see mx.ine.observadoresINE.bsd.BSDAcreditacionGafeteInterface#modifica(mx.ine.observadoresINE.dto.db.DTOAcreditacionGafetes)
	 */
	@Override
	public void modifica(DTOAcreditacionGafetes acreGafete) throws Exception {
		asAcreditacionGafeteInterface.modifica(acreGafete);
	}

	/* (non-Javadoc)
	 * @see mx.ine.observadoresINE.bsd.BSDAcreditacionGafeteInterface#elimina(mx.ine.observadoresINE.dto.db.DTOAcreditacionGafetes)
	 */
	@Override
	public void elimina(DTOAcreditacionGafetes acreGafete) throws Exception {
		asAcreditacionGafeteInterface.elimina(acreGafete);
	}
}
