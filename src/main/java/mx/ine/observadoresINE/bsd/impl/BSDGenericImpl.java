/**
 * @(#)BSDGenericImpl.java 07/08/2017
 * <p>
 * Copyright (C) 2017 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.bsd.impl;

import java.util.List;

import mx.ine.observadoresINE.as.ASGenericInterface;
import mx.ine.observadoresINE.bsd.BSDGenericInterface;
import mx.ine.observadoresINE.dto.db.DTOCImagenes;
import mx.ine.observadoresINE.dto.db.DTOCTextos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * <code>BSDGenericImpl.java</code> 
 *
 * @author Pablo Zuñiga Mata
 * @since 07/08/2017
 * @copyright Direccion de sistemas - INE
 */
@Component("bsdGeneric")
@Scope("prototype")
public class BSDGenericImpl implements BSDGenericInterface{
	
	@Autowired
	@Qualifier("asGeneric")
	private ASGenericInterface asGeneric;

	/* (non-Javadoc)
	 * @see mx.ine.observadoresINE.bsd.BSDGenericInterface#obtenTextos(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<DTOCTextos> obtenTextos(Integer idProceso, Integer idDetalle,
			Integer idParrafo, Integer idTexto) throws Exception {
		return asGeneric.obtenTextos(idProceso, idDetalle, idParrafo, idTexto);
	}

	/* (non-Javadoc)
	 * @see mx.ine.observadoresINE.bsd.BSDGenericInterface#obtenRutasIMG(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<DTOCImagenes> obtenRutasIMG(Integer idProceso,
			Integer idDetalle, List<Integer> listIdImagen) throws Exception {
		return asGeneric.obtenRutasIMG(idProceso, idDetalle, listIdImagen);
	}

}
