package mx.ine.observadoresINE.bsd;


import mx.ine.observadoresINE.dto.DTOFiltroReporteAcciones;
import mx.ine.observadoresINE.dto.DTOReportesParametros;

public interface BSDReporteAccionesInterface {

	public DTOReportesParametros obtenReporte(DTOFiltroReporteAcciones datos) throws Exception;
}
