/**
 * @(#)ASReportesInterface.java 5/07/2017
 * <p>
 * Copyright (C) 2016 Instituto Nacional Electoral (INE).
 * <p>
 * Todos los derechos reservados.
 */

package mx.ine.observadoresINE.as;

import java.util.LinkedHashMap;
import java.util.List;

import mx.ine.common.ws.administracion.dto.response.DTODetalleDistritoProcesoWS;
import mx.ine.common.ws.administracion.dto.response.DTODetalleEstadoProcesoWS;
import mx.ine.common.ws.api.exception.ClienteWebServiceException;
import mx.ine.observadoresINE.dto.DTOFiltroReporteAcciones;
import mx.ine.observadoresINE.dto.DTOFiltrosReporte;
import mx.ine.observadoresINE.dto.DTOList;
import mx.ine.observadoresINE.dto.DTOReportesParametros;
import mx.ine.observadoresINE.dto.db.DTOCursos;
import mx.ine.observadoresINE.dto.form.FormRepControlObs;
import mx.ine.observadoresINE.helper.HLPReporteDetalleObservadores;
import mx.org.ine.servicios.exception.ApplicationException;

/**
 * <code>ASReportesInterface.java</code> 
 * 
 * @author Helaine Flores Cervantes
 * @since 5/07/2017
 * @copyright Direccion de sistemas - INE
 */
public interface ASReportesInterface {
    
    /**
     * Método que obtiene una lista de distritos
     * @author Carlos Augusto Escalona Navarro
     * @since 13/03/2017
     * @param filtros
     * @return
     * @throws mx.ine.common.ws.api.exception.ClienteWebServiceException
     */
    public List<DTODetalleDistritoProcesoWS> getDistritos(DTOFiltrosReporte filtros) throws ClienteWebServiceException;
    
     /**
     * Método que obtiene una lista de estados
     * @author Carlos Augusto Escalona Navarro
     * @param idSistema
     * @param idProceso
     * @param idDetalle
     * @since 13/03/2017
     * @return
     * @throws mx.ine.common.ws.api.exception.ClienteWebServiceException
     */    
    public List<DTODetalleEstadoProcesoWS> obtenEstadosPorProcesoDetalle(Integer idSistema,
            Integer idProceso, Integer idDetalle) throws ClienteWebServiceException;
    
    /**
     * Método encargado de obtener los valores de una tabla de la BD
     * @param numeroTabla
     * @return registros de la tabla
     * 
     * @author Helaine Flores Cervantes
     * @throws mx.org.ine.servicios.exception.ApplicationException
     * @since 05/07/2017
     */
    List<String> obtenerTabla(int numeroTabla) throws ApplicationException;
    /**
     * Método encargado de obtener el reporte "Control de Observadores"
     * @param form
     * @return reporte
     * @author Helaine Flores Cervantes
     * @throws mx.org.ine.servicios.exception.ApplicationException
     * @since 05/07/2017
     */
    public List<Object[]> consultarReporteControlObs(FormRepControlObs form) throws ApplicationException;
    
    /**
	 * Método que consulta datos de agrupaciones para generar reporte.
	 * @param idFiltroAgrupacion 
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> consultaDatosReporteAgrupaciones(Integer idFiltroAgrupacion) throws Exception;
	
	/**
	 * Método que genera el reporte de Detalle de Observadores Registrados
	 * 
	 * @author Emmanuel García Ysamit
	 * @return
	 * @throws Exception
	 * @since 17/08/2017
	 */
	public HLPReporteDetalleObservadores generaReporteDetalleObs(LinkedHashMap<String, Integer> filtros) throws Exception;
	
	/**
	 * Método que consulta los datos del reporte de ACCIONES DE PROMOCION.
	 * @param datos
	 * @return
	 * @author Jean Pierre Pacheco Avila
	 */
	public DTOReportesParametros consultaDatosReporteAcciones(DTOFiltroReporteAcciones datos) throws Exception;

	public DTOReportesParametros obtenReporteCursos(DTOFiltroReporteAcciones datos);
    /**
     * Método que obtiene los identificadores de los estados y sus abreviaturas 
     * 
     * @author Helaine Flores Cervantes
     * @since 22/08/2017
     * @return 
     * @throws mx.org.ine.servicios.exception.ApplicationException 
     */	
    public List<DTOList> obtenerAbreviaturaEstados() throws ApplicationException;
    /**
    * Método que obtiene el reporte Control Observadores con encabezados dinámicos
    * 
    * @author Helaine Flores C.
     * @throws mx.org.ine.servicios.exception.ApplicationException
    * @since 22/08/2017
    * @param form
    * @return 
    */
    public List<Object[]> consultarReporteControlObsDinamico(FormRepControlObs form) throws ApplicationException;

	public List<DTOCursos> obtenListaCursos(DTOFiltroReporteAcciones filtros);
        
    /**
     * Método que onsulta el catálogo C_Justificaciones y obtiene los aprobados
     * 
     * @author Helaine Flores C.
     * @param form
     * @throws mx.org.ine.servicios.exception.ApplicationException
     * @since 24/08/2017
     * @return 
     */
    List<DTOList> obtenerJustificacionesAprobadas(FormRepControlObs form) throws ApplicationException;
    /**
     * Método que consulta el encabezado dinámico para el reporte Control de
     * Observadores. Acreditaciones No aprobadas. Por Entidad OC
     * 
     * @author Helaine Flores C.
     * @param form
     * @return
     * @throws mx.org.ine.servicios.exception.ApplicationException
     */
     List<Object[]> consultarEncabezadoCanDenDecl(FormRepControlObs form)throws ApplicationException;
     
     /**
     * Método que consulta el reporte "Acreditaciones No aprobadas Por Entidad OC"
     * @param form
     * @return 
     */
    List<Object[]> consultarRepAcreditacionesNoAprobadasPorEntidad(FormRepControlObs form)throws ApplicationException;
	
}
