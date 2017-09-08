package mx.ine.observadoresINE.bsd.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.observadoresINE.as.ASReportesInterface;
import mx.ine.observadoresINE.bsd.BSDReporteAgrupacionesInterface;

@Component("bsdReporteAgrupaciones")
@Scope("prototype")
public class BSDReporteAgrupacionesImpl implements
		BSDReporteAgrupacionesInterface {

	@Autowired
	@Qualifier("asReportes")
	private ASReportesInterface asReportes;

	@Override
	public List<Object[]> consultaDatosReporte(Integer idFiltroAgrupacion)
			throws Exception {
		return asReportes.consultaDatosReporteAgrupaciones(idFiltroAgrupacion);
	}
}
