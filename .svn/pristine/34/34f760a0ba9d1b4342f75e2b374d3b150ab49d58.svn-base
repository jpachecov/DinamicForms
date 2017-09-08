 /**
 * @(#)BSDReporteDetalleObservadoresImpl.java 17/08/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */
package mx.ine.observadoresINE.bsd.impl;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.observadoresINE.as.ASReportesInterface;
import mx.ine.observadoresINE.bsd.BSDReporteDetalleObservadoresInterface;
import mx.ine.observadoresINE.helper.HLPReporteDetalleObservadores;

 /**
 * 
 * @author Emmanuel García Ysamit
 * @since 17/08/2017
 * @copyright Direccion de sistemas - INE
 */
@Component("bsdReporteDetalleObservadores")
@Scope("prototype")
public class BSDReporteDetalleObservadoresImpl implements BSDReporteDetalleObservadoresInterface {

	@Autowired
	@Qualifier("asReportes")
	private ASReportesInterface asReportes;
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public HLPReporteDetalleObservadores generaReporte(LinkedHashMap<String, Integer> filtros) throws Exception {
		return asReportes.generaReporteDetalleObs(filtros);
	}

}
