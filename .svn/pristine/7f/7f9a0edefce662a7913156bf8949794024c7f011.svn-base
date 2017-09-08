package mx.ine.observadoresINE.bsd.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.observadoresINE.as.ASReportesInterface;
import mx.ine.observadoresINE.bsd.BSDReporteAccionesInterface;
import mx.ine.observadoresINE.dto.DTOFiltroReporteAcciones;
import mx.ine.observadoresINE.dto.DTOReportesParametros;

@Component("bsdReporteAcciones")
@Scope("prototype")
public class BSDReporteAccionesImpl implements BSDReporteAccionesInterface{

	@Autowired
	@Qualifier("asReportes")
	private ASReportesInterface asReportes;
	
	@Override
	public DTOReportesParametros obtenReporte(DTOFiltroReporteAcciones datos) throws Exception {
		return asReportes.consultaDatosReporteAcciones(datos);
	}
}
