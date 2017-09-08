package mx.ine.observadoresINE.bsd;

import java.util.List;

public interface BSDReporteAgrupacionesInterface {

	/**
	 * Método que consulta datos de agrupaciones para generar reporte.
	 * 
	 * @param idFiltroAgrupacion
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> consultaDatosReporte(Integer idFiltroAgrupacion)
			throws Exception;

}
