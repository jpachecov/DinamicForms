package mx.ine.observadoresINE.bsd.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import mx.ine.observadoresINE.as.ASReportesInterface;
import mx.ine.observadoresINE.bsd.BSDReportesCursosInterface;
import mx.ine.observadoresINE.dto.DTOFiltroReporteAcciones;
import mx.ine.observadoresINE.dto.DTOReportesParametros;
import mx.ine.observadoresINE.dto.db.DTOCursos;

@Component("bsdReportesCursos")
@Scope("prototype")
public class BSDReportesCursosImpl implements BSDReportesCursosInterface {

	@Autowired
	@Qualifier("asReportes")
	private transient ASReportesInterface asReportes;

	@Override
	public DTOReportesParametros obtenReporteCursos(DTOFiltroReporteAcciones datos) {
		return asReportes.obtenReporteCursos(datos);
	}

	@Override
	public List<DTOCursos> obtenListaCursos(DTOFiltroReporteAcciones filtros) {
		return asReportes.obtenListaCursos(filtros);
	}
	
}
